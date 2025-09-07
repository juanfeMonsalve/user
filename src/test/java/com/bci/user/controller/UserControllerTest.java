package com.bci.user.controller;

import com.bci.user.dto.User;
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
import java.util.UUID;

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
    private User userDto;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId("76992a50-7150-4764-ac0a-201a3addadc1");
        user.setUsername("juan");
        user.setPassword("secret");
        user.setEmail("juan@test.com");
        userDto = new User();
        userDto.setId(UUID.randomUUID().toString());
        userDto.setUsername("juan");
        userDto.setPassword("secret");
        userDto.setEmail("juan@test.com");
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
        when(userService.getUserById("76992a50-7150-4764-ac0a-201a3addadc1")).thenReturn(userDto);

        ResponseEntity<User> response = userController.getUserById("76992a50-7150-4764-ac0a-201a3addadc1");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("juan@test.com");

        verify(userService, times(1)).getUserById("76992a50-7150-4764-ac0a-201a3addadc1");
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        User updatedUser = new User();
        updatedUser.setId("76992a50-7150-4764-ac0a-201a3addadc1");
        updatedUser.setUsername("juanUpdated");
        updatedUser.setEmail("updated@test.com");

        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId("76992a50-7150-4764-ac0a-201a3addadc1");
        updatedUserEntity.setUsername("juanUpdated");
        updatedUserEntity.setEmail("updated@test.com");

        when(userService.updateUser(eq("76992a50-7150-4764-ac0a-201a3addadc1"), any(UserEntity.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser("76992a50-7150-4764-ac0a-201a3addadc1", updatedUserEntity);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getUsername()).isEqualTo("juanUpdated");
        assertThat(response.getBody().getEmail()).isEqualTo("updated@test.com");

        verify(userService, times(1)).updateUser(eq("76992a50-7150-4764-ac0a-201a3addadc1"), any(UserEntity.class));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        doNothing().when(userService).deleteUser("76992a50-7150-4764-ac0a-201a3addadc1");

        ResponseEntity<Void> response = userController.deleteUser("76992a50-7150-4764-ac0a-201a3addadc1");

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();

        verify(userService, times(1)).deleteUser("76992a50-7150-4764-ac0a-201a3addadc1");
    }
}
