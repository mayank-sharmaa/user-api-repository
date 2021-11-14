package com.home.assignment.userapi.repository;

import com.home.assignment.userapi.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByUserIdAndRoleIdAndUnitId(Long userId, Long roleId, Long unitId);

    List<UserRoleEntity> findByUserIdAndUnitId(Long userId, Long unitId);

    List<UserRoleEntity> findByUserIdAndUnitIdAndValidFromAfter(Long userId, Long unitId, ZonedDateTime validFrom);

    Long deleteAllByUserIdAndUnitIdAndRoleIdAndVersionIs(Long userId, Long unitId, Long roleId,Integer version);
}