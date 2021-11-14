package com.home.assignment.userapi.mapper;

import com.home.assignment.userapi.dto.UserDTO;
import com.home.assignment.userapi.entity.RoleEntity;
import com.home.assignment.userapi.entity.UnitEntity;
import com.home.assignment.userapi.entity.UserEntity;
import com.home.assignment.userapi.entity.UserRoleEntity;
import com.home.assignment.userapi.model.Role;
import com.home.assignment.userapi.model.Unit;
import com.home.assignment.userapi.model.User;
import com.home.assignment.userapi.model.UserRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(
         componentModel = "spring"
)
@Component
public interface ObjectMapper {

    @Mapping(target = "username", source = "userEntity.name")

    User userEnityToUser(UserEntity userEntity);

    @Mapping(target = "unitName", source = "unitEntity.name")
    Unit unitEnityToUser(UnitEntity unitEntity);

    @Mapping(target = "roleName", source = "roleEntity.name")
    Role roleEnityToUser(RoleEntity roleEntity);


    UserRole useRoleEnityToUserRole(UserRoleEntity userRoleEntity);

    @Mapping(target="username",source = "userDTO.userName")
    @Mapping(target="id",source = "userDTO.userId")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "unit", ignore = true)
    User customMappingToUser(UserDTO userDTO);



    @AfterMapping

    default void roleToRoleList(@MappingTarget User user, UserDTO userDTO) {
            Role role = new Role();
            role.setId(userDTO.getRoleId());
            role.setRoleName(userDTO.getRoleName());
            if (userDTO.getRoleId() != null)
            user.setRole(role);
            Unit unit = new Unit();
            unit.setId(userDTO.getUnitId());
            unit.setUnitName(userDTO.getUnitName());
            if (userDTO.getUnitId() != null)
            user.setUnit( unit);


    }




}
