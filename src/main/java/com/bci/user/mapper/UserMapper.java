package com.bci.user.mapper;

import com.bci.user.dto.User;
import com.bci.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserEntity userEntity);
    UserEntity toEntity(User user);
}
