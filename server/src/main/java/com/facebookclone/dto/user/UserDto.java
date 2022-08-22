package com.facebookclone.dto.user;

import com.facebookclone.entity.UserBio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String picture;
    private String cover;
    private String gender;
    private LocalDate birthday;
    private Boolean isVerified;
    private UserBio details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
