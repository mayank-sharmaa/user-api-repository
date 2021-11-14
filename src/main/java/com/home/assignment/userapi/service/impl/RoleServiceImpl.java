package com.home.assignment.userapi.service.impl;

import com.home.assignment.userapi.entity.RoleEntity;
import com.home.assignment.userapi.repository.RoleEntityRepository;
import com.home.assignment.userapi.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleEntityRepository roleEntityRepository;

    public RoleServiceImpl(RoleEntityRepository roleEntityRepository) {
        this.roleEntityRepository = roleEntityRepository;
    }

    @Override
    @Transactional
    public List<RoleEntity> getAllRoles() {
        return roleEntityRepository.findAll();
    }
}
