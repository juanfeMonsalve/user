package com.bci.user.service.impl;

import com.bci.user.UserRepository;
import com.bci.user.config.JwtService;
import com.bci.user.dto.*;
import com.bci.user.entity.UserEntity;
import com.bci.user.enums.Role;
import com.bci.user.exception.UserException;
import com.bci.user.mapper.UserMapper;
import com.bci.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final static String ROLE_DEFAULT = "USER";

    @Override
    public AuthResponse register( RegisterRequest request) {
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole() != null ?  request.getRole().toUpperCase() : ROLE_DEFAULT));
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setLastLogin(LocalDateTime.now());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException("El usuario ya existe: " + request.getEmail());
        }

        UserEntity userSaved =userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(userSaved.getId().toString(),token,userSaved.getCreatedAt(),userSaved.getLastLogin(),userSaved.getIsActive());
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity userSaved =userRepository.findByEmail(request.getEmail()).orElse(null);
        String token = jwtService.generateToken(request.getEmail());
        return new LoginResponse(userSaved.getId(),token,userSaved.getCreatedAt(),userSaved.getLastLogin(),userSaved.getIsActive(),userSaved.getPassword());
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userMapper.toModel(userRepository.findById(id).orElse(null));
    }

    @Override
    public User updateUser(
            String id,
            UserEntity userDetails) {

        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());

            if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            return userMapper.toModel(userRepository.save(user));
        }).orElse(null);
    }

    @Override
    public void deleteUser(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserException("El usuario no existe"));
        userRepository.delete(user);
    }
}
