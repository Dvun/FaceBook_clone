package com.facebookclone.dto.user;

import com.facebookclone.dto.auth.RegisterDto;
import com.facebookclone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RegisterDto dtoToEntity(User user);
    User entityToDto(RegisterDto dto);

    @Mapping(target = "user.roles", ignore = true)
    UserDto loginResponse(User user) throws ParseException;

}
