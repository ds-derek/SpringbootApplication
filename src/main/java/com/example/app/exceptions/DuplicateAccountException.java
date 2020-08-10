package com.example.app.exceptions;

public class DuplicateAccountException extends RuntimeException{
    public DuplicateAccountException(String account){
        super(account + " is duplicated.");
    }
}
