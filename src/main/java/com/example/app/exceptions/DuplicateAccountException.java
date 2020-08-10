package com.example.app.exceptions;

public class DuplicateAccountException extends RuntimeException{

    private static final long serialVersionUID = 1450011509252993373L;

    public DuplicateAccountException(String account){
        super(account + " is duplicated.");
    }
}
