package com.emc.belustyle.dto.mapper;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.roleName", target = "role")
    UserDTO toDTO(User user);

    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO userDTO);
}