package com.tamanna.customerserver.dto.mapper;

import com.tamanna.customerserver.dto.roles.RoleDTO;
import com.tamanna.customerserver.dto.user.UserDTO;
import com.tamanna.customerserver.entity.roles.Role;
import com.tamanna.customerserver.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);

    UserDTO mapToUserDTO (User user);

    User mapToUser(UserDTO userDto);

    Role mapToRole (RoleDTO roleDTO);

    RoleDTO mapToRoleDTO (Role role);
}
