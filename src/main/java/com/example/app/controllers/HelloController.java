package com.example.app.controllers;

import com.example.app.responses.SuccessResponse;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value="HelloController", tags="1. Hello")
@RestController
@RequestMapping("/v1/hello")
public class HelloController {
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> hello(){
        SuccessResponse<?> hello = SuccessResponse.response("hello");
        return new ResponseEntity<>(hello, HttpStatus.OK);
    }
}
