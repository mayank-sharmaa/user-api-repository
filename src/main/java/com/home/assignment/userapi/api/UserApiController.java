package com.home.assignment.userapi.api;


import com.home.assignment.userapi.entity.UserEntity;
import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.model.User;
import com.home.assignment.userapi.service.UserService;
import com.home.assignment.userapi.util.Operation;
import com.home.assignment.userapi.validator.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserApiController implements UserApi {


    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserApiController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }


    @Override
    public ResponseEntity<Void> createUser(UUID xRequestID, User user) throws Exception {
        log.info("Received Request : {} create user request for User : {}",xRequestID,user.getUsername());
        RequestValidator.validateCreateUpdateUserRequest(user, Operation.CREATE);
        userService.createUser(xRequestID, user);
        log.info("User : {} created successfully ", user.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers(UUID xRequestID) throws Exception {
        log.info("Received Request : {} for listing all users",xRequestID);
        List<User> userList = userService.getAllUsers()
                .stream()
                .map(objectMapper::userEnityToUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String username, Integer version, UUID xRequestID) throws Exception {
        log.info("Received Request : {} for delete user  request for User Name : {}",xRequestID,username);
        Long deletedRecords =userService.deleteUser(username,version);
        log.info("User : {} deleted successfully ", username);
        if(deletedRecords.intValue()==0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(UUID xRequestID, User user) throws Exception {
        log.info("Received Request : {} update user  request for User Name : {}",xRequestID,user.getUsername());
        RequestValidator.validateCreateUpdateUserRequest(user,Operation.UPDATE);
        UserEntity userEntity=userService.updateUserRole(user);
        log.info("User : {} created successfully ", user.getUsername());
        return new ResponseEntity<>(objectMapper.userEnityToUser(userEntity),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<User>> getAllUserAndRoleInUnit(UUID xRequestID, Long unitId) throws Exception {
        log.info("Received Request getAllUserAndRoleInUnit : {} ",xRequestID);
        HttpHeaders responseHeaders = new HttpHeaders();
        getRequest().ifPresent(x->{
            if(Objects.equals(x.getHeader(HttpHeaders.ACCEPT), MediaType.APPLICATION_JSON.toString())){
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            }else if(Objects.equals(x.getHeader(HttpHeaders.ACCEPT), MediaType.APPLICATION_XML.toString())){
                responseHeaders.setContentType(MediaType.APPLICATION_XML);
            }
        });
        return new ResponseEntity<>(userService.getAllUserAndRoleInUnit(unitId),responseHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> getUserByName(String username, UUID xRequestID) throws Exception {
        return UserApi.super.getUserByName(username, xRequestID);
    }

    @Override
    public ResponseEntity<List<User>> getAllUserWithAtleastOneValidRoleAtGivenTime(UUID xRequestID, Long unitId, String timestamp) throws Exception {
        log.info("Received Request getAllUserWithAtleastOneValidRoleAtGivenTime : {} ",xRequestID);
        ZonedDateTime givenTime = ZonedDateTime.parse(timestamp);
        return new ResponseEntity<>(userService.getAllUserWithAtleastOneValidRoleAtGivenTime(unitId,givenTime), HttpStatus.OK);
    }
}

