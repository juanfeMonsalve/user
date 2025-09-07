package com.bci.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean isActive;
    private String password;
}
