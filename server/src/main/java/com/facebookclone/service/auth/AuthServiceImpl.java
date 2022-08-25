package com.facebookclone.service.auth;

import com.facebookclone.dto.auth.*;
import com.facebookclone.dto.user.UserMapper;
import com.facebookclone.entity.*;
import com.facebookclone.exception.*;
import com.facebookclone.repository.*;
import com.facebookclone.security.jwt.JwtUtils;
import com.facebookclone.service.email.EmailService;
import com.facebookclone.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    private final TemplateEngine templateEngine;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private String randomToken;


    @Override
    public ResponseEntity<String> register(RegisterDto dto) throws MessagingException {
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
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> activate(String urlWithToken) {
        User user = userRepository.findByEmail(randomTokenGenerator.getEmail()).orElseThrow();
        if (randomTokenGenerator.verifyTokenTime(urlWithToken, getTokenFormUrl())) {
            user.setIsVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok().body("Account is activated!");
        }
        user.getRoles().removeAll(user.getRoles());
        userRepository.delete(user);
        return ResponseEntity.badRequest().body("Limited time (30 min) is expired! Register again and activate your account!");
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
        Context context = new Context();
        context.setVariable("url", url);
        String html = templateEngine.process("email/emailActivation", context);

        return new EmailDetails(email, "Account activation", html, "");
    }

}
