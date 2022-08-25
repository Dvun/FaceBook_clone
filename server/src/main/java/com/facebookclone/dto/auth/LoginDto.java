package com.facebookclone.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginDto {

    @NotEmpty(message = "Email is required!")
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Please write correct email!")
    private String email;

    @NotEmpty(message = "Password is required!")
    @Length(min = 6, message = "Minimum 6 characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$",
            message = "Minimum 6 characters, at least one upper case, one lower case, one number!")
    private String password;


    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
