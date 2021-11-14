package com.home.assignment.userapi.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code = null;
    private String message = null;
    private String timestamp = null;


}
