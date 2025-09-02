package com.bci.user.service.impl;

import com.bci.user.UserRepository;
import com.bci.user.config.JwtService;
import com.bci.user.dto.AuthRequest;
import com.bci.user.dto.AuthResponse;
import com.bci.user.dto.RegisterRequest;
import com.bci.user.entity.UserEntity;
import com.bci.user.enums.Role;
import com.bci.user.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    @InjectMocks
    private UserServiceImpl userService; // tu implementación

    private RegisterRequest registerRequest;
    private AuthRequest authRequest;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("john", "1234", "USER",
                "");
        authRequest = new AuthRequest("john", "1234");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("john");
        userEntity.setPassword("encodedPass");
        userEntity.setRole(Role.USER);
    }

    @Test
    void register_ShouldSaveUserAndReturnToken() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("encodedPass");
        when(jwtService.generateToken("john")).thenReturn("jwt-token");

        AuthResponse response = userService.register(registerRequest);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void register_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> userService.register(registerRequest))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("El usuario ya existe");

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_ShouldAuthenticateAndReturnToken() {
        when(jwtService.generateToken("john")).thenReturn("jwt-token");

        AuthResponse response = userService.login(authRequest);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("john", "1234"));
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity));

        List<UserEntity> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("john");
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john");
    }

    @Test
    void getUserById_ShouldReturnNull_WhenNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        UserEntity result = userService.getUserById(2L);

        assertThat(result).isNull();
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        UserEntity updates = new UserEntity();
        updates.setUsername("newJohn");
        updates.setPassword("newPass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        UserEntity result = userService.updateUser(1L, updates);

        assertThat(result.getUsername()).isEqualTo("newJohn");
        assertThat(result.getPassword()).isEqualTo("encodedNewPass");
    }

    @Test
    void updateUser_ShouldReturnNull_WhenUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserEntity result = userService.updateUser(1L, new UserEntity());

        assertThat(result).isNull();
    }

    @Test
    void deleteUser_ShouldDelete_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        userService.deleteUser(1L);

        verify(userRepository).delete(userEntity);
    }

    @Test
    void deleteUser_ShouldThrow_WhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("El usuario no existe");
    }
}
