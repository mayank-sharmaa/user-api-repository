package com.home.assignment.userapi.validator;

import com.home.assignment.userapi.error.ErrorCodes;
import com.home.assignment.userapi.exception.UserApiException;
import com.home.assignment.userapi.model.User;
import com.home.assignment.userapi.model.UserRole;
import com.home.assignment.userapi.util.Operation;
import com.sun.istack.NotNull;

import javax.validation.Valid;
import java.time.ZonedDateTime;

public class RequestValidator {

    public static void  validateCreateUpdateUserRequest(@Valid @NotNull User user, Operation operation){
        if(user.getUsername()==null||user.getUsername().isBlank()|| user.getUsername().isEmpty())
            throw new UserApiException(ErrorCodes.INVALID_REQUEST_USER_NAME.getErrorCode(),ErrorCodes.INVALID_REQUEST_USER_NAME.getErrorMessage());
        if(operation.equals(Operation.UPDATE)){
            if(user.getVersion()==null)throw new UserApiException(ErrorCodes.VERSION_MANDATORY.getErrorCode(), ErrorCodes.VERSION_MANDATORY.getErrorMessage());
        }
    }

    public static void  validateCreateUpdateUserRoleRequest(@Valid @NotNull UserRole userRole,Operation operation){
        if(userRole.getUserId()==null) throw new UserApiException(ErrorCodes.INVALID_USERID.getErrorCode(), ErrorCodes.INVALID_USERID.getErrorMessage());
        if(userRole.getRoleId()==null) throw new UserApiException(ErrorCodes.INVALID_ROLEID.getErrorCode(), ErrorCodes.INVALID_ROLEID.getErrorMessage());
        if(userRole.getUnitId()==null) throw new UserApiException(ErrorCodes.INVALID_UNITID.getErrorCode(), ErrorCodes.INVALID_UNITID.getErrorMessage());
        if(userRole.getValidTo()!=null){
          if(userRole.getValidFrom()!=null && !userRole.getValidTo().isAfter(userRole.getValidFrom()))
              throw new UserApiException(ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorCode(), ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorMessage());
          if(userRole.getValidFrom()==null && !userRole.getValidTo().isAfter(ZonedDateTime.now()))
              throw new UserApiException(ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorCode(), ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorMessage());
        }
        if(operation.equals(Operation.UPDATE)){
            if(userRole.getVersion()==null)throw new UserApiException(ErrorCodes.VERSION_MANDATORY.getErrorCode(), ErrorCodes.VERSION_MANDATORY.getErrorMessage());
        }
    }


}
