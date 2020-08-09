package com.example.app.exceptions;

public class NoEntityFoundException extends RuntimeException {
    private String[] msg = null;
    public NoEntityFoundException(String... args){
        super("Could not found Resource {0} by {1}");
        this.msg = args;
    }
}
