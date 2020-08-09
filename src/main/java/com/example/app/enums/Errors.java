package com.example.app.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Errors {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,  " Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.UNAUTHORIZED, " Invalid Input Value"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,  " Invalid Parameter Value"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND,  " Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "Access is Denied"),


    // Member
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "Email is Duplication"),
    LOGIN_INPUT_INVALID(HttpStatus.BAD_REQUEST, "Login input is invalid")

    ;

    private final String message;
    private HttpStatus status;

    Errors(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus() {
        return status;
    }


}
