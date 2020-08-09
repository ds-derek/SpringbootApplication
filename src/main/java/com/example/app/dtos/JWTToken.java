package com.example.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @JsonProperty("account_token")
    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }
}
