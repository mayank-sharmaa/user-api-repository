package com.home.assignment.userapi.api;

import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.model.Role;
import com.home.assignment.userapi.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class RoleApiController implements RoleApi{

    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    public RoleApiController(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<List<Role>> getAllRoles(UUID xRequestID) throws Exception {
        log.info("Received Request : {} for listing all roles",xRequestID);
        List<Role> roleList = roleService.getAllRoles()
                .stream()
                .map(objectMapper::roleEnityToUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }
}
