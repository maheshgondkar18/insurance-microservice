package com.user.user.controller;

import com.user.user.dto.LoginRequest;
import com.user.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.user.service.AuthService;


import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        authService.register(user);
        return "User registered successfully";
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}