package com.facebookclone.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.Objects;

@Data
@ResponseStatus(HttpStatus.OK)
public class ApiResponse {

    private Date timestamp;
    private final String message;


    public ApiResponse(String message, Objects content) {
        this.timestamp = getTimestamp();
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return new Date();
    }

}
