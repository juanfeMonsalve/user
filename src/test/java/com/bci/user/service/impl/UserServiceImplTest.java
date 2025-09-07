package com.bci.user.service.impl;

import com.bci.user.UserRepository;
import com.bci.user.config.JwtService;
import com.bci.user.dto.*;
import com.bci.user.dto.User;
import com.bci.user.entity.UserEntity;
import com.bci.user.enums.Role;
import com.bci.user.exception.UserException;
import com.bci.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private AuthRequest authRequest;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setPassword("Password1");
        registerRequest.setEmail("juan@test.com");
        authRequest = new AuthRequest("juan@test.com", "Password1");

        userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUsername("juan@test.com");
        userEntity.setEmail("juan@test.com");
        userEntity.setPassword("encodedPass");
        userEntity.setRole(Role.USER);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setIsActive(true);
    }

    @Test
    void register_ShouldSaveUser_WhenNewUser() {
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Password1")).thenReturn("encodedPass");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(jwtService.generateToken("juan@test.com")).thenReturn("jwt-token");

        AuthResponse response = userService.register(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void register_ShouldThrow_WhenUserAlreadyExists() {
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> userService.register(registerRequest))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("El usuario ya existe");
    }

    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(userEntity));
        when(jwtService.generateToken("juan@test.com")).thenReturn("jwt-token");

        LoginResponse response = userService.login(authRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void getAllUsers_ShouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserEntity> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        User mappedUser = new User();
        mappedUser.setUsername("Juan");
        mappedUser.setPassword("123");
        when(userRepository.findById("123")).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(userEntity)).thenReturn(mappedUser);

        User result = userService.getUserById("123");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("Juan");
    }

    @Test
    void updateUser_ShouldUpdatePassword_WhenProvided() {
        UserEntity updateRequest = new UserEntity();
        updateRequest.setUsername("nuevo");
        updateRequest.setPassword("newPass");
        User user = new User();
        user.setUsername("Juan");
        user.setPassword("123");

        when(userRepository.findById("123")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNew");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toModel(any(UserEntity.class))).thenReturn(user);

        User result = userService.updateUser("123", updateRequest);

        assertThat(result).isNotNull();
        verify(passwordEncoder).encode("newPass");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void deleteUser_ShouldDelete_WhenExists() {
        when(userRepository.findById("123")).thenReturn(Optional.of(userEntity));

        userService.deleteUser("123");

        verify(userRepository).delete(userEntity);
    }

    @Test
    void deleteUser_ShouldThrow_WhenNotExists() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser("999"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("El usuario no existe");
    }
}

