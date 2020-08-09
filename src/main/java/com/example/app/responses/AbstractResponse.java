package com.example.app.responses;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractResponse {
    protected String message;
    protected String timestamp = LocalDateTime.now().toString();
    protected int status;
}
