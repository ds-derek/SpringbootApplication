package com.example.app.exceptions;

public class NoEntityFoundException extends RuntimeException {
    private static final long serialVersionUID = -8059812811968886319L;

    public NoEntityFoundException(String entity, String by){
        super("Could not found Resource "+entity+" by "+by);
    }
}
