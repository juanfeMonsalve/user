package com.smartjob.user.mapper;

import com.smartjob.user.dto.UserDto;
import com.smartjob.user.entity.User;

import java.util.List;

public interface UserMapper {
    UserDto userToUserDto(User user);

    List<UserDto> usersToUsersDto(List<User> users);

    User userDtoToUserCreate(UserDto userDto);

}
