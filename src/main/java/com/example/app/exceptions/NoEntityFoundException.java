package com.example.app.exceptions;

public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException(String entity, String by){
        super("Could not found Resource "+entity+" by "+by);
    }
}
