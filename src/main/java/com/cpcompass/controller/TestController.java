package com.cpcompass.controller;

import com.cpcompass.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/test")
    public String test(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token =
                authHeader.substring(7);

        return jwtService.extractEmail(token);
    }
}