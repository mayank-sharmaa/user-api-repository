package com.home.assignment.userapi.service.impl;

import com.home.assignment.userapi.entity.UnitEntity;
import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.repository.UnitEntityRepository;
import com.home.assignment.userapi.service.UnitService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitEntityRepository unitEntityRepository;

    private final ObjectMapper objectMapper;

    public UnitServiceImpl(UnitEntityRepository unitEntityRepository, ObjectMapper objectMapper) {
        this.unitEntityRepository = unitEntityRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    @Transactional
    public List<UnitEntity> getAllUnits() {
        return unitEntityRepository.findAll();
    }




}
