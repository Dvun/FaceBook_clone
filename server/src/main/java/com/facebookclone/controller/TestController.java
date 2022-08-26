package com.facebookclone.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
public class TestController {


    @GetMapping("/test")

    public String test() {
        return "Test working";
    }

}
