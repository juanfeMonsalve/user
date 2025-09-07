package com.bci.user.service;

import com.bci.user.dto.*;
import com.bci.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    AuthResponse register( RegisterRequest request);
    LoginResponse login(AuthRequest request);
    void deleteUser( String id);
    User updateUser( String id, UserEntity userDetails);
    User getUserById(String id);
    List<UserEntity> getAllUsers();
}
