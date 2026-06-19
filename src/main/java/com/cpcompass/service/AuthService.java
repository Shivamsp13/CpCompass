package com.cpcompass.service;

import com.cpcompass.dto.LoginRequest;
import com.cpcompass.dto.LoginResponse;
import com.cpcompass.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}