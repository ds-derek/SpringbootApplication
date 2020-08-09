package com.example.app.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInfoView {
    private String name;
    private String email;
    private String lastLoginAt;
    public MemberInfoView(String name, String email, String lastLoginAt){
        this.name = name;
        this.email =email;
        this.lastLoginAt = lastLoginAt;
    }
}
