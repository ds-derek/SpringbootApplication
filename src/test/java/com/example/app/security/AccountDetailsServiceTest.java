package com.example.app.security;

import com.example.app.entities.Account;
import com.example.app.entities.Role;
import com.example.app.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountDetailsServiceTest {
    private final Logger log = LoggerFactory.getLogger(AccountDetailsServiceTest.class);

    private AccountDetailsService accountDetailsService;

    @Autowired
    AccountRepository accountRepository;


    @BeforeEach
    void initialize(){
        this.accountDetailsService = new AccountDetailsService(accountRepository);
        Account test = new Account("test", "test@test.com", "12345");
        Role role = new Role("ROLE_USER");
        test.addRole(role);
        accountRepository.save(test);
    }

    @Test
    @DisplayName("Test Case for AccountDetailService loadUserByUsername method")
    public void test_userDetails(){
        UserDetails userDetails = accountDetailsService.loadUserByUsername("test@test.com");
        assertEquals("test@test.com", userDetails.getUsername());
    }
}