package com.example.app.responses;


import com.example.app.enums.Errors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse extends AbstractResponse{
    private List<Error> errors;
    protected String path;

    private ErrorResponse(final Errors error, final String path, final List<Error> errors) {
        this.message = error.getMessage();
        this.status = error.getStatus().value();
        this.path = path;
        this.errors = errors;
    }

    private ErrorResponse(final Errors error, final String path, final ParameterError parameterError) {
        this.message = error.getMessage();
        this.status = error.getStatus().value();
        this.errors = new LinkedList<>();
        this.path = path;
        errors.add(parameterError);
    }

    private ErrorResponse(final Errors error, final String path) {
        this.message = error.getMessage();
        this.status = error.getStatus().value();
        this.path = path;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(final Errors error, final String path, String message) {
        this.message = message;
        this.status = error.getStatus().value();
        this.path = path;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(Map<String, Object> errorAttributes) {
        this.message = (String) errorAttributes.get("message");
        this.status = (int) errorAttributes.get("status");
        this.path = (String) errorAttributes.get("path");
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final Errors error, String path, final BindingResult bindingResult) {
        return new ErrorResponse(error, path, FieldError.of(bindingResult));
    }
    public static ErrorResponse of(final Errors error,  String path, final MethodParameter methodParameter) {
        return new ErrorResponse(error, path, ParameterError.of(methodParameter));
    }

    public static ErrorResponse of(final Errors error, String path) {
        return new ErrorResponse(error, path);
    }
    public static ErrorResponse of(final Errors error,  String path, String message) {
        return new ErrorResponse(error, path, message);
    }

    public static ErrorResponse of(final Errors error, String path, final List<Error> errors) {
        return new ErrorResponse(error, path, errors);
    }

    public static ErrorResponse of(final Map<String, Object> errorAttributes) {
        return new ErrorResponse(errorAttributes);
    }

    public static ErrorResponse of(String path, MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<Error> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(Errors.INVALID_TYPE_VALUE, path, errors);
    }

    public interface Error{}

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError implements Error{
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<Error> of(final String field, final String value, final String reason) {
            List<Error> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<Error> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ParameterError implements Error {
        private String parameter;
        private String allowableType;

        private ParameterError(final String parameter, final String allowableType) {
            this.parameter = parameter;
            this.allowableType = allowableType;
        }

        private static ParameterError of(final MethodParameter methodParameter) {
            return new ParameterError(methodParameter.getParameterName(), methodParameter.getParameterType().toString());
        }
    }

}
