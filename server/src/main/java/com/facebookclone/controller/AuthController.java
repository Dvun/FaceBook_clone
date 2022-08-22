package com.facebookclone.controller;


import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.entity.User;
import com.facebookclone.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto dto) throws ParseException {
        return authService.register(dto);
    }

}

