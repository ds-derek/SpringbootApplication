package com.example.app;

import com.example.app.dtos.SignUpForm;
import com.example.app.entities.Account;
import com.example.app.repositories.AccountRepository;
import com.example.app.repositories.RoleRepository;
import com.example.app.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MemberServiceTest {
    private final Logger log = LoggerFactory.getLogger(MemberServiceTest.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    MemberService memberService;

    @BeforeEach
    public void setup(){
        this.passwordEncoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.memberService = new MemberService(accountRepository, roleRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Test Case for MemberService saveUser method")
    public void test_() throws Exception{
        SignUpForm signUpForm = new SignUpForm();

        signUpForm.setName("myName");
        signUpForm.setId("test@test.com");
        signUpForm.setPassword("12345");

        Account saveMember = memberService.saveUser(signUpForm).orElseThrow(() -> new Exception("save failed"));

        boolean matches = passwordEncoder.matches("12345", saveMember.getPassword());
        assertTrue(matches);
    }

}
