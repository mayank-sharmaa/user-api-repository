package com.home.assignment.userapi.advice;

import com.home.assignment.userapi.error.ErrorCodes;
import com.home.assignment.userapi.error.ErrorResponse;
import com.home.assignment.userapi.exception.UserApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy - HH:mm:ss z");
    private static final String EXCEPTION_OCCURED = "Exception occured : {} request : {}";

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenericException(Exception exp, WebRequest request) {
        log.warn(EXCEPTION_OCCURED, exp.getStackTrace(), request);
        return handleExceptionInternal(exp, new ErrorResponse(ErrorCodes.INTERNAL_ERROR.getErrorCode(), exp.getMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(DateTimeParseException.class)
    ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException exp, WebRequest request) {
        log.warn(EXCEPTION_OCCURED, exp.getStackTrace(), request);
        return handleExceptionInternal(exp, new ErrorResponse(ErrorCodes.INVALID_TIMESTAMP.getErrorCode(), ErrorCodes.INVALID_TIMESTAMP.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<Object> handleInvalidRequestException(UserApiException exception, WebRequest request) {
        log.warn(EXCEPTION_OCCURED, exception.getStackTrace(), request);
        if (exception.getName().equals(ErrorCodes.INVALID_REQUEST_USER_NAME.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_REQUEST_USER_NAME.getErrorCode(), ErrorCodes.INVALID_REQUEST_USER_NAME.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorCode(), ErrorCodes.INVALID_VALID_TO_DATE_TIME.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.INVALID_USERID.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_USERID.getErrorCode(), ErrorCodes.INVALID_USERID.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.INVALID_UNITID.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_UNITID.getErrorCode(), ErrorCodes.INVALID_UNITID.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.INVALID_ROLEID.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_ROLEID.getErrorCode(), ErrorCodes.INVALID_ROLEID.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode(), ErrorCodes.VERSION_MISMATCH_ERROR.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.VERSION_MANDATORY.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.VERSION_MANDATORY.getErrorCode(), ErrorCodes.VERSION_MANDATORY.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (exception.getName().equals(ErrorCodes.INVALID_UPDATE.getErrorCode())) {
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_UPDATE.getErrorCode(), ErrorCodes.INVALID_UPDATE.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_REQUEST.getErrorCode(), ErrorCodes.INVALID_REQUEST.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        log.warn(EXCEPTION_OCCURED, exception.getStackTrace(), request);
        if (Objects.requireNonNull(exception.getLocalizedMessage()).contains("PRIMARY_KEY"))
            return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_REQUEST_NON_UNIQUE_USER_ROLE.getErrorCode(), ErrorCodes.INVALID_REQUEST_NON_UNIQUE_USER_ROLE.getErrorMessage(),
                    ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);
        else return handleExceptionInternal(exception, new ErrorResponse(ErrorCodes.INVALID_REQUEST_FOREIGN_KEY_CONSTRAINT_FAILED.getErrorCode(),
                ErrorCodes.INVALID_REQUEST_FOREIGN_KEY_CONSTRAINT_FAILED.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);

    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMediaTypeNotSupportedException while processing request : {}", e.getMethod());
        return handleExceptionInternal(e, new ErrorResponse(ErrorCodes.METHOD_NOT_ALLOWED.getErrorCode(), ErrorCodes.METHOD_NOT_ALLOWED.getErrorMessage(),
                ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMediaTypeNotAcceptableException while processing request : {}", e.getMessage());
        return handleExceptionInternal(e, new ErrorResponse(ErrorCodes.UNSUPPORTED_MEDIA_TYPE.getErrorCode(), ErrorCodes.UNSUPPORTED_MEDIA_TYPE.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("MethodArgumentNotValidException while processing request : {}", e.getMessage());
        return handleExceptionInternal(e, new ErrorResponse(ErrorCodes.INVALID_REQUEST.getErrorCode(), ErrorCodes.INVALID_REQUEST.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @Override

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("MethodArgumentNotValidException while processing request : {}", ex.getMessage());
        return handleExceptionInternal(ex, new ErrorResponse(ErrorCodes.INVALID_REQUEST.getErrorCode(), ErrorCodes.INVALID_REQUEST.getErrorMessage(), ZonedDateTime.now().format(formatter)), getHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    private HttpHeaders getHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }
}
