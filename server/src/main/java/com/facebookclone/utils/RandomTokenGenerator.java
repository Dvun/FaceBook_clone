package com.facebookclone.utils;

import com.facebookclone.exception.BadRequestException;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Configuration
public class RandomTokenGenerator {

    private String token;
    private String email;

    public RandomTokenGenerator(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public RandomTokenGenerator() {
    }

    public String setToken(String email) {
        StringBuilder token = new StringBuilder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long currentTime = timestamp.getTime();
        String newToken = token.append(currentTime).append("-").append(UUID.randomUUID()).toString();
        this.token = newToken;
        this.email = email;
        return newToken;
    }

    public Boolean verifyTokenTime(String token, String tokenFormUrl) {
        String existToken = getToken(token);
        if (!token.isEmpty() && token.equals(existToken)) {
            Long timestamp = Long.parseLong(tokenFormUrl.split("-")[0]);
            Long nowTime = new Date().getTime();
            return (nowTime - timestamp) <= AppConstants.RANDOM_TOKEN_TIME;
        }
        return false;
    }


    public String getToken(String tokenFormUrl) {
        if (this.token.equals(tokenFormUrl)) {
            return this.token;
        }
        throw new BadRequestException("Wrong token!");
    }

    public String getEmail() {
        return this.email;
    }

}
