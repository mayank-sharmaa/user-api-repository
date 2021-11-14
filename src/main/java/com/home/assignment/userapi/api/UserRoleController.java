package com.home.assignment.userapi.api;

import com.home.assignment.userapi.entity.UserRoleEntity;
import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.model.UserRole;
import com.home.assignment.userapi.service.UserRoleService;
import com.home.assignment.userapi.util.Operation;
import com.home.assignment.userapi.validator.RequestValidator;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserRoleController implements UserRoleApi {

    private final UserRoleService userRoleService;
    private final ObjectMapper objectMapper;

    public UserRoleController(UserRoleService userRoleService, ObjectMapper objectMapper) {
        this.userRoleService = userRoleService;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Void> createUserRole(UUID xRequestID, @Valid @NotNull UserRole userRole) throws Exception {
        log.info("Received Request : {} create user role request for User Id : {}, Role Id :{} , Unit Id {}",xRequestID,userRole.getUserId(),userRole.getRoleId(),userRole.getUnitId());
        RequestValidator.validateCreateUpdateUserRoleRequest(userRole, Operation.CREATE);
        userRoleService.createUserRole(userRole);
        log.info("User Role: {} created successfully ", userRole.getRoleId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserRole> updateUserRole(UUID xRequestID, UserRole userRole) throws Exception {
        log.info("Received Request : {} update user role request for User Id : {}, Role Id :{} , Unit Id {}",xRequestID,userRole.getUserId(),userRole.getRoleId(),userRole.getUnitId());
        RequestValidator.validateCreateUpdateUserRoleRequest(userRole,Operation.UPDATE);
        UserRoleEntity userRoleEntity=userRoleService.updateUserRole(userRole);
        log.info("User Role: {} created successfully ", userRole.getRoleId());
        return new ResponseEntity<>(objectMapper.useRoleEnityToUserRole(userRoleEntity),HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Void> deleteUserRole(Long userId, Long unitId, Long roleId, UUID xRequestID, Integer version) throws Exception {
        log.info("Received Request : {} for delete user role request for User Id : {}, Role Id :{} , Unit Id {}",xRequestID,userId,roleId,unitId);
        Long deletedRecords =userRoleService.deleteUserRole(userId,unitId,roleId,version);
        log.info("User Role: {} deleted successfully ", roleId);
        if(deletedRecords.intValue()==0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserRole>> getAllUsersByUserIdAndUnitId(UUID xRequestID, Long userId, Long unitId) throws Exception {
        log.info("Received Request : {} getting user role by User Id : {} , Unit Id {}",xRequestID,userId,unitId);

      List<UserRole> userRoleList = userRoleService.getAllUsersByUserIdAndUnitId(userId,unitId)
              .stream()
              .map(objectMapper::useRoleEnityToUserRole)
              .collect(Collectors.toList());
        log.info("User Roles displayed correctly successfully ");
        return new ResponseEntity<>(userRoleList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserRole>> getAllUsersByUserIdAndUnitIdAtGivenTime(UUID xRequestID, Long userId, Long unitId, String givenTimeStamp) throws Exception {
        log.info("Received Request : {} getting user role by User Id : {} , Unit Id {}, Given Time : {}",xRequestID,userId,unitId,givenTimeStamp);

        ZonedDateTime givenTime = ZonedDateTime.parse(givenTimeStamp);
        List<UserRole> userRoleList = userRoleService.getAllValidUsersByUserIdAndUnitIdAtGivenTime(userId,unitId,givenTime)
                .stream()
                .map(objectMapper::useRoleEnityToUserRole)
                .collect(Collectors.toList());
        log.info("User Roles displayed correctly successfully ");
        return new ResponseEntity<>(userRoleList,HttpStatus.OK);
    }
}
