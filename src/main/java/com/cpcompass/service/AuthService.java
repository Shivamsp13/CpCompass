package com.cpcompass.service;

import com.cpcompass.client.CodeforcesClient;
import com.cpcompass.dto.LoginRequest;
import com.cpcompass.dto.LoginResponse;
import com.cpcompass.dto.RegisterRequest;
import com.cpcompass.entity.User;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CodeforcesClient codeforcesClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByCodeforcesHandle(
                request.getCodeforcesHandle())) {
            throw new RuntimeException("Handle already exists");
        }

        if (!codeforcesClient.handleExists(
                request.getCodeforcesHandle())) {
            throw new RuntimeException("Invalid Codeforces Handle");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .codeforcesHandle(request.getCodeforcesHandle())
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}