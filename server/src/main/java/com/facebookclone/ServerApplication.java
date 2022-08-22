package com.facebookclone;

import com.facebookclone.entity.Role;
import com.facebookclone.service.auth.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }


    @Bean
    CommandLineRunner run(AuthService authService) {
        return args -> {
            if (authService.findByRole("ROLE_ADMIN").isEmpty()) {
                authService.createRole(new Role(null, "ROLE_ADMIN"));
            }
            if (authService.findByRole("ROLE_USER").isEmpty()) {
                authService.createRole(new Role(null, "ROLE_USER"));
            }
        };
    }

}
