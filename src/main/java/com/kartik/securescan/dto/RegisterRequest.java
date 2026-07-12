package com.kartik.securescan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    public RegisterRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}