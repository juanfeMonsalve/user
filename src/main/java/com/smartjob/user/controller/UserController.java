package com.smartjob.user.controller;


import com.smartjob.user.dto.ErrorDto;
import com.smartjob.user.dto.UserDto;
import com.smartjob.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ResponseEntity createUser(@RequestBody UserDto userDto) {
        try {
            UserDto response = userService.createUser(userDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
        }
    }

    @GetMapping
    ResponseEntity getUsers() {
        try {
            return ResponseEntity.ok(userService.getUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
        }
    }
}
