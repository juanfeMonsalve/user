package com.smartjob.user.service.impl;

import com.smartjob.user.dto.UserDto;
import com.smartjob.user.entity.User;
import com.smartjob.user.exception.EmailExistsException;
import com.smartjob.user.exception.EmailPasswordIncorrectException;
import com.smartjob.user.mapper.UserMapper;
import com.smartjob.user.repository.IUserRepository;
import com.smartjob.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImp implements UserService {
    @Value("${passworExpresion}")
    private String passwordExpresion;
    private final String emailExpresion = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        try {
            if (validationEmail(userDto.getEmail()) && validationPassword(userDto.getPassword())) {
                if (iUserRepository.finByEmail(userDto.getEmail()).isEmpty()) {
                    User user = iUserRepository.save(userMapper.userDtoToUserCreate(userDto));
                    return userMapper.userToUserDto(user);
                } else {
                    throw new EmailExistsException("El correo ingresado ya existe");
                }
            }
            throw new EmailPasswordIncorrectException("No cumple formato de correo o contraseña");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<UserDto> getUsers() {
        return userMapper.usersToUsersDto((List<User>) iUserRepository.findAll());
    }

    private boolean validationPassword(String password) {
        Pattern pattern = Pattern.compile(passwordExpresion);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validationEmail(String email) {
        Pattern pattern = Pattern.compile(emailExpresion);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
