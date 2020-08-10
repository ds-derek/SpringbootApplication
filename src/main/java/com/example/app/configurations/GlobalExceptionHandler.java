package com.example.app.configurations;

import com.example.app.enums.Errors;
import com.example.app.exceptions.DuplicateAccountException;
import com.example.app.exceptions.NoEntityFoundException;
import com.example.app.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountExceptionException(DuplicateAccountException e, HttpServletRequest request){
        String path = request.getRequestURL().toString();
        ErrorResponse response = ErrorResponse.of(Errors.EMAIL_DUPLICATION, path, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request){
        String path = request.getRequestURL().toString();
        ErrorResponse response = ErrorResponse.of(Errors.METHOD_NOT_ALLOWED, path, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error message", "Bad credentials");
        return new ResponseEntity<>(response, headers, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentNotValidException e, HttpServletRequest request){
        String path = request.getRequestURL().toString();
        ErrorResponse response = ErrorResponse.of(Errors.INVALID_INPUT_VALUE, path, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoEntityFoundException(NoEntityFoundException e, HttpServletRequest request){
        String path = request.getRequestURL().toString();
        ErrorResponse response = ErrorResponse.of(Errors.INVALID_INPUT_VALUE, path, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        String path = request.getRequestURL().toString();
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(Errors.INTERNAL_SERVER_ERROR, path, "server error.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
