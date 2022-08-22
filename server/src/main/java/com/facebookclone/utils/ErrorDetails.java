package com.facebookclone.utils;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {

    private final Date timestamp;
    private final String message;
    private final String path;

    public ErrorDetails(Date timestamp, String message, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

}
