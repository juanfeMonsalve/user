package com.smartjob.user.mapper.impl;

import com.smartjob.user.dto.PhoneDto;
import com.smartjob.user.dto.UserDto;
import com.smartjob.user.entity.Phone;
import com.smartjob.user.entity.User;
import com.smartjob.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapperImp implements UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setCreated(user.getCreated());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setModified(user.getModified());
        userDto.setId(user.getId());
        userDto.setToken(user.getToken());
        userDto.setIsActive(user.getIsActive());
        userDto.setLastLogin(user.getLastLogin());

        List<PhoneDto> phonesDto = new ArrayList<>();
        user.getPhones().forEach(phone -> {
            PhoneDto phoneDto = new PhoneDto();
            phoneDto.setCitycode(phone.getCitycode());
            phoneDto.setId(phone.getId());
            phoneDto.setContrycode(phone.getContrycode());
            phoneDto.setNumber(phone.getNumber());
            phonesDto.add(phoneDto);
        });
        userDto.setPhones(phonesDto);
        return userDto;
    }

    @Override
    public List<UserDto> usersToUsersDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        users.forEach(user -> {
            usersDto.add(userToUserDto(user));
        });
        return usersDto;
    }

    @Override
    public User userDtoToUserCreate(UserDto userDto) {
        User user = new User();
        user.setCreated(LocalDateTime.now());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setModified(LocalDateTime.now());
        user.setIsActive(true);
        user.setLastLogin(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setToken(String.valueOf(UUID.randomUUID()));

        List<Phone> phones = new ArrayList<>();
        userDto.getPhones().forEach(phoneDto -> {
            Phone phone = new Phone();
            phone.setUser(user);
            phone.setCitycode(phoneDto.getCitycode());
            phone.setContrycode(phoneDto.getContrycode());
            phone.setNumber(phoneDto.getNumber());
            phones.add(phone);
        });

        user.setPhones(phones);
        return user;
    }
}

