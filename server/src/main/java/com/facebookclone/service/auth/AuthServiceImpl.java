package com.facebookclone.service.auth;

import com.facebookclone.dto.auth.LoginDto;
import com.facebookclone.dto.auth.LoginResponseDto;
import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.dto.user.UserMapper;
import com.facebookclone.entity.Role;
import com.facebookclone.entity.User;
import com.facebookclone.exception.BadRequestException;
import com.facebookclone.exception.NotFoundException;
import com.facebookclone.repository.RoleRepository;
import com.facebookclone.repository.UserRepository;
import com.facebookclone.security.jwt.JwtUtils;
import com.facebookclone.service.email.EmailService;
import com.facebookclone.utils.ApiResponse;
import com.facebookclone.utils.EmailDetails;
import com.facebookclone.utils.GenerateUsername;
import com.facebookclone.utils.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${client.base_url}")
    private String clientUrl;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateUsername generateUsername;
    private final RandomTokenGenerator randomTokenGenerator;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private String randomToken;


    @Override
    public ApiResponse register(RegisterDto dto) throws MessagingException {
        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new BadRequestException(String.format("User with email %s already registered!", dto.getEmail()));
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setBirthday(LocalDate.parse(dto.getBirthday()));
        user.setIsVerified(false);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        addRoleToUser(user, "ROLE_USER");
        generateUsername.build(user, dto);
        String randomToken = randomTokenGenerator.setToken(dto.getEmail());
        setTokenFormUrl(randomToken);
        String emailResponse = emailService.sendEmailWithAttachment(setDetails(dto.getEmail(), randomToken));
        userRepository.save(user);
        return new ApiResponse(emailResponse);
    }

    @Override
    public ApiResponse activate(String urlWithToken) {
        User user = userRepository.findByEmail(randomTokenGenerator.getEmail()).orElseThrow();
        if (randomTokenGenerator.verifyTokenTime(urlWithToken, getTokenFormUrl())) {
            if (user.getIsVerified()) {
                throw new BadRequestException("Account already activated!");
            }
            user.setIsVerified(true);
            userRepository.save(user);
            return new ApiResponse("Account is activated!");
        }
        user.getRoles().removeAll(user.getRoles());
        userRepository.delete(user);
        throw new BadRequestException("Limited time (30 min) is expired! Register again and activate your account!");
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginDto dto) throws ParseException {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new NotFoundException("User", "email", dto.getEmail()));
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        if (!user.getIsVerified() && !getTokenFormUrl().isEmpty()) {
            if (!randomTokenGenerator.verifyTokenTime(randomToken, getTokenFormUrl())) {
                user.getRoles().removeAll(user.getRoles());
                userRepository.delete(user);
                throw new BadRequestException("Limited time (30 min) is expired! Register again and activate your account!");
            }
            throw new BadRequestException("Email address must verify first!");
        }
        String token = jwtUtils.generateToken(auth);
        return ResponseEntity.ok().body(new LoginResponseDto(UserMapper.INSTANCE.loginResponse(user), token));
    }


    @Override
    public void addRoleToUser(User user, String roleType) {
        Set<Role> roleArr = new HashSet<>();
        Role role = roleRepository.findByRole(roleType).orElse(null);
        roleArr.add(role);
        user.setRoles(roleArr);
    }

    @Override
    public void createRole(Role roleType) {
        roleRepository.save(roleType);
    }

    @Override
    public Optional<Role> findByRole(String roleType) {
        return roleRepository.findByRole(roleType);
    }

    public String getTokenFormUrl() {
        return randomToken;
    }

    public void setTokenFormUrl(String tokenFormUrl) {
        this.randomToken = tokenFormUrl;
    }

    private EmailDetails setDetails(String email, String token) {
        String url = String.format("%s/api/auth/activate/%s", clientUrl, token);
        String html = String.format("<!doctype html>\n" +
                "<html lang=en xmlns=http://www.w3.org/1999/xhtml xmlns:th=http://www.thymeleaf.org>\n" +
                "<head>\n" +
                "<meta charset=UTF-8>\n" +
                "<meta name=viewport content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "<meta http-equiv=X-UA-Compatible content=\"ie=edge\">\n" +
                "<title>Activation</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=margin-left:50px;margin-top:50px>\n" +
                "<div><b>Welcome!</b></div>\n" +
                "<br/>\n" +
                "<div>Please activate your account using the link within 30 minutes!</div>\n" +
                "<br/>\n" +
                "<br/>\n" +
                "<a href=%s style=\"width:200px;height:50px;background-color:blue;text-decoration:none;color:white;padding:13px 45px;border-radius:10px;fontSize:18\">\n" +
                "Activate\n" +
                "</a>\n" +
                "<br/>\n" +
                "<br/>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>", url);

        return new EmailDetails(email, "Account activation", html, "");
    }

}
