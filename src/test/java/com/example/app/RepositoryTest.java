package com.example.app;

import com.example.app.entities.Account;
import com.example.app.entities.Role;
import com.example.app.repositories.AccountRepository;
import com.example.app.repositories.RoleRepository;
import com.example.app.security.AccountDetailsServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RepositoryTest {

    private final Logger log = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;


    @Test
    @DisplayName("Test Case for AccountRepository save and find methods")
    public void account_test(){

        Account testAccount = new Account("testAccount", "test@test.com", "12345");

        Role role_user = roleRepository.findOneByName("ROLE_USER");

        testAccount.addRole(role_user);

        accountRepository.save(testAccount);

        Optional<Account> findAccount = accountRepository.findOneByEmailAddr("test@test.com");
        Account account = findAccount.get();
        assertEquals("testAccount", account.getName());
        assertEquals("test@test.com", account.getEmailAddr());

    }
}
