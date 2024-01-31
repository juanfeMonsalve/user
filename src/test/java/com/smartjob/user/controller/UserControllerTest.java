package com.smartjob.user.controller;

import com.smartjob.user.dto.UserDto;
import com.smartjob.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @Test
    void createUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("test");
        userDto.setEmail("test@test.com");
        userDto.setPassword("123");


        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        assertEquals(ResponseEntity.ok(userDto), userController.createUser(userDto));
    }


    @Test
    void getUsers() {
        List<UserDto> usersDto = new ArrayList<>();

        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("test");
        userDto.setEmail("test@test.com");
        userDto.setPassword("123");
        usersDto.add(userDto);

        Mockito.when(userService.getUsers()).thenReturn(usersDto);
        assertEquals(ResponseEntity.ok(usersDto), userController.getUsers());
    }
}