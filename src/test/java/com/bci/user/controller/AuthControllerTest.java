package com.bci.user.controller;

import com.bci.user.dto.AuthRequest;
import com.bci.user.dto.AuthResponse;
import com.bci.user.dto.LoginResponse;
import com.bci.user.dto.RegisterRequest;
import com.bci.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest validRegisterRequest;
    private AuthRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validRegisterRequest = new RegisterRequest("juan", "a2asfGfdfdf4", "ROLE_USER", "juan@test.com", new ArrayList<>());
        validLoginRequest = new AuthRequest("juan", "a2asfGfdfdf4");
    }

    @Test
    void register_ShouldReturnOk_WhenValidRequest() {
        AuthResponse response = new AuthResponse("asdasd","token", LocalDateTime.now(),LocalDateTime.now(),true);
        when(userService.register(any(RegisterRequest.class))).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.register(validRegisterRequest);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getToken()).isEqualTo("token");
    }

    @Test
    void register_ShouldThrowException_WhenServiceFails() {
        when(userService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Registration error"));

        assertThrows(RuntimeException.class,
                () -> authController.register(validRegisterRequest));
    }

    @Test
    void login_ShouldReturnOk_WhenValidRequest() {
        LoginResponse response = new LoginResponse("asdasd","token", LocalDateTime.now(),LocalDateTime.now(),true,"123");
        when(userService.login(any(AuthRequest.class))).thenReturn(response);

        ResponseEntity<LoginResponse> result = authController.login(validLoginRequest);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getToken()).isEqualTo("token");
    }

    @Test
    void login_ShouldThrowException_WhenInvalidCredentials() {
        when(userService.login(any(AuthRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class,
                () -> authController.login(validLoginRequest));
    }
}
