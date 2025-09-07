package com.bci.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    private String id;
    private String username;
    private String name;
    private String password;
    private String email;
    private List<Phone> phone;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}
