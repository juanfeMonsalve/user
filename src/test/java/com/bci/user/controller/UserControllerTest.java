package com.bci.user.controller;

import com.bci.user.entity.UserEntity;
import com.bci.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setUsername("juan");
        user.setPassword("secret");
        user.setEmail("juan@test.com");
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        List<UserEntity> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserEntity>> response = userController.getAllUsers();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getUsername()).isEqualTo("juan");

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.getUserById(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("juan@test.com");

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setUsername("juanUpdated");
        updatedUser.setEmail("updated@test.com");

        when(userService.updateUser(eq(1L), any(UserEntity.class))).thenReturn(updatedUser);

        ResponseEntity<UserEntity> response = userController.updateUser(1L, updatedUser);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getUsername()).isEqualTo("juanUpdated");
        assertThat(response.getBody().getEmail()).isEqualTo("updated@test.com");

        verify(userService, times(1)).updateUser(eq(1L), any(UserEntity.class));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();

        verify(userService, times(1)).deleteUser(1L);
    }
}
