package com.facebookclone.service.auth;

import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.entity.Role;
import com.facebookclone.entity.User;
import com.facebookclone.exception.BadRequestException;
import com.facebookclone.repository.RoleRepository;
import com.facebookclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<User> register(RegisterDto dto) throws ParseException {
        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new BadRequestException(String.format("User with email %s already registered!", dto.getEmail()));
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setBirthday(new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).parse(dto.getBirthday()));
        addRoleToUser(user, "ROLE_USER");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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

}
