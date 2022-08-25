package com.facebookclone.dto.user;

import com.facebookclone.entity.Role;
import com.facebookclone.entity.UserBio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String picture;
    private String cover;
    private String gender;
    private Set<String> roles;
    private LocalDate birthday;
    private Boolean isVerified;
    private UserBio details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

}
