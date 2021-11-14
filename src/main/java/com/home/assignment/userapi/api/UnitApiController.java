package com.home.assignment.userapi.api;


import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.model.Unit;
import com.home.assignment.userapi.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UnitApiController implements UnitApi {


    private final UnitService unitService;
    private final ObjectMapper objectMapper;
    private final NativeWebRequest nativeWebRequest;

    public UnitApiController(UnitService unitService, ObjectMapper objectMapper, NativeWebRequest nativeWebRequest) {
        this.unitService = unitService;
        this.objectMapper = objectMapper;
        this.nativeWebRequest = nativeWebRequest;
    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(nativeWebRequest);
    }

    @Override
    public ResponseEntity<List<Unit>> getAllUnits(UUID xRequestID) throws Exception {
        log.info("Received Request : {} for listing all units",xRequestID);
        List<Unit> unitList = unitService.getAllUnits()
                .stream()
                .map(objectMapper::unitEnityToUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(unitList, HttpStatus.OK);
    }





}

