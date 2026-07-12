package com.kartik.securescan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kartik.securescan.dto.LoginRequest;
import com.kartik.securescan.dto.LoginResponse;
import com.kartik.securescan.dto.RegisterRequest;
import com.kartik.securescan.entity.User;
import com.kartik.securescan.repository.UserRepository;
import com.kartik.securescan.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Register
    public String register(RegisterRequest request) {

        if (repository.existsByUsername(request.getUsername())) {

            return "Username already exists.";

        }

        if (repository.existsByEmail(request.getEmail())) {

            return "Email already exists.";

        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());

        user.setPassword(

                passwordEncoder.encode(

                        request.getPassword()));

        user.setRole("USER");

        repository.save(user);

        return "User Registered Successfully.";

    }

    // Login
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getUsername(),

                        request.getPassword()));

        String token =

                jwtUtil.generateToken(

                        request.getUsername());

        return new LoginResponse(token);

    }

}