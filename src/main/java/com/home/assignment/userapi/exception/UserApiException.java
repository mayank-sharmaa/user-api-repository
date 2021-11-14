package com.home.assignment.userapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserApiException extends RuntimeException {
    public String name;
    public String message;
}
