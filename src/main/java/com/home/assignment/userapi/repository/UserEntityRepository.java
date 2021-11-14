package com.home.assignment.userapi.repository;

import com.home.assignment.userapi.dto.UserDTO;
import com.home.assignment.userapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNameIgnoreCase(String name);
    Long deleteAllByNameIgnoreCase(String name);

    @Query(value = "select user.name as userName , user.id as userId, unit.name as unitName,unit.id as unitId,role.name as roleName ,role.id as roleId from user user join user_role user_role on user.id=user_role.userId join role on role.id=user_role.roleId join unit on unit.id=user_role.unitId where unit.id=?",nativeQuery = true)
    List<UserDTO> findUserAndRoleByUnitId(Long unitId);


    @Query(value = "select user.name as userName , user.id as userId, unit.name as unitName,unit.id as unitId from user user join user_role user_role on user.id=user_role.userId join unit unit on unit.id=user_role.unitId where unit.id=? and user_role.validTo > ?",nativeQuery = true)
    List<UserDTO> findUserEntitiesByUserRolesAfter(Long unitId, ZonedDateTime givenTime);
}