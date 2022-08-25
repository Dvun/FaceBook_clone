package com.facebookclone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String resource, String fieldName, String value) {
        super(String.format("%s not found with %s: %s", resource, fieldName, value));
    }

}
