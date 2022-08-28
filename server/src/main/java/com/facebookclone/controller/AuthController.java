package com.facebookclone.controller;


import com.facebookclone.dto.auth.LoginDto;
import com.facebookclone.dto.auth.LoginResponseDto;
import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.service.auth.AuthService;
import com.facebookclone.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.text.ParseException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ApiResponse register(@RequestBody @Valid RegisterDto dto) throws ParseException, MessagingException {
        return authService.register(dto);
    }

    @GetMapping("/activate/{token}")
    public ApiResponse activate(@PathVariable String token) {
        return authService.activate(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto dto) throws ParseException {
        return authService.login(dto);
    }

}

