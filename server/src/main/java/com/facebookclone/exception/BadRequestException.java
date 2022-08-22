package com.facebookclone.exception;


import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {

    private final String message;

}
