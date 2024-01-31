package com.smartjob.user.service;

import com.smartjob.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto) throws Exception;

    List<UserDto> getUsers();
}
