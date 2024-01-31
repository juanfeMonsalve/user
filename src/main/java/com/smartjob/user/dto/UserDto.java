package com.smartjob.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    UUID id;
    String name;
    String email;
    String password;
    List<PhoneDto> phones;
    LocalDateTime created;
    LocalDateTime modified;
    LocalDateTime lastLogin;
    String token;
    Boolean isActive;
}
