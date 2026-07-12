package com.kartik.securescan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kartik.securescan.dto.LoginRequest;
import com.kartik.securescan.dto.LoginResponse;
import com.kartik.securescan.dto.RegisterRequest;
import com.kartik.securescan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public String register(
            @Valid
            @RequestBody RegisterRequest request) {

        return userService.register(request);

    }

    // Login
    @PostMapping("/login")
    public LoginResponse login(
            @Valid
            @RequestBody LoginRequest request) {

        return userService.login(request);

    }

}