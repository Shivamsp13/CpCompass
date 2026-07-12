package com.cpcompass.controller;

import com.cpcompass.dto.LoginRequest;
import com.cpcompass.dto.LoginResponse;
import com.cpcompass.dto.RegisterRequest;
import com.cpcompass.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        System.out.println("Recieved login request");
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}