package com.home.assignment.userapi.service;

import com.home.assignment.userapi.entity.UserRoleEntity;
import com.home.assignment.userapi.model.UserRole;

import java.time.ZonedDateTime;
import java.util.List;

public interface UserRoleService {

    void createUserRole(UserRole userRole);

    UserRoleEntity updateUserRole(UserRole userRole);

    Long deleteUserRole(Long userId, Long unitId, Long roleId,Integer version);

    List<UserRoleEntity> getAllUsersByUserIdAndUnitId(Long userId, Long unitId);
    List<UserRoleEntity> getAllValidUsersByUserIdAndUnitIdAtGivenTime(Long userId, Long unitId, ZonedDateTime givenTimeStamp);


}
