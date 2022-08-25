package com.facebookclone.dto.auth;

import com.facebookclone.dto.user.UserDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {

    private UserDto user;
    private String accessToken;

}
