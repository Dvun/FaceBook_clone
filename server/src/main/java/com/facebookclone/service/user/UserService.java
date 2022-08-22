package com.facebookclone.service.user;

import com.facebookclone.dto.user.UserDto;

public interface UserService {

    UserDto getUser(String email);

}
