package com.bci.user.service;

import com.bci.user.dto.AuthRequest;
import com.bci.user.dto.AuthResponse;
import com.bci.user.dto.RegisterRequest;
import com.bci.user.entity.UserEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {
    AuthResponse register( RegisterRequest request);
    AuthResponse login( AuthRequest request);
    void deleteUser( Long id);
    UserEntity updateUser( Long id, UserEntity userDetails);
    UserEntity getUserById(@PathVariable Long id);
    List<UserEntity> getAllUsers();
}
