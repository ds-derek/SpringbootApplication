package com.example.app.controllers;

import com.example.app.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class ErrorController extends AbstractErrorController {
    private final Logger log = LoggerFactory.getLogger(ErrorController.class);

    public ErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @GetMapping
    public ResponseEntity<?> error(final HttpServletRequest request) {
        Map<String, Object> errorAttributes = this.getErrorAttributes(request, false);
        ErrorResponse res = ErrorResponse.of(errorAttributes);
        final HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>(res, status);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
