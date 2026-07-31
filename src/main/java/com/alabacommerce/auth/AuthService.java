package com.alabacommerce.auth;

import com.alabacommerce.dto.LoginRequest;
import com.alabacommerce.dto.LoginResponse;
import com.alabacommerce.dto.RegisterRequest;
import com.alabacommerce.dto.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest loginRequest);
}