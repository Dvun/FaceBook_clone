package com.facebookclone.dto.auth;

import com.facebookclone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    User registerUserFromDtoToEntity(RegisterDto dto);

}
