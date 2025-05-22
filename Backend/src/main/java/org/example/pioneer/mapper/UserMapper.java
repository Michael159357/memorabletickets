package org.example.pioneer.mapper;

import org.example.pioneer.domain.User;
import org.example.pioneer.dto.UserDTO;
import org.example.pioneer.dto.UserProfileDTO;
import org.example.pioneer.dto.UserRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO  toUserDTO(User user);

    User toUser(UserDTO userDTO);

    UserRegisterDTO toUserRegisterDTO(User user);

    User toUser(UserRegisterDTO userRegisterDTO);

    UserProfileDTO toUserProfileDTO(User user);

    User toUser(UserProfileDTO userProfileDTO);
}

