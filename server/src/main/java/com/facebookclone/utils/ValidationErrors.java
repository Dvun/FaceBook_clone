package com.facebookclone.utils;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ValidationErrors {

    private final Date timestamp;
    private final String path;
    private final Map<String, String> errors;

    public ValidationErrors(Date timestamp, String path, Map<String, String> errors) {
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }

}
