package com.facebookclone.service.auth;

import com.facebookclone.dto.auth.LoginDto;
import com.facebookclone.dto.auth.LoginResponseDto;
import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.entity.Role;
import com.facebookclone.entity.User;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.Optional;

public interface AuthService {

    ResponseEntity<String> register(RegisterDto dto) throws ParseException, MessagingException;
    ResponseEntity<String> activate(String token);
    ResponseEntity<LoginResponseDto> login(LoginDto dto) throws ParseException;
    void addRoleToUser(User user, String roleType);
    void createRole(Role roleType);
    Optional<Role> findByRole(String roleType);
}
