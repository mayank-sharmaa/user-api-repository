package com.home.assignment.userapi.repository;

import com.home.assignment.userapi.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
}