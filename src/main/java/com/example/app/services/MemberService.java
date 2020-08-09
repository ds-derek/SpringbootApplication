package com.example.app.services;

import com.example.app.dtos.SignUpForm;
import com.example.app.entities.Account;
import com.example.app.entities.Role;
import com.example.app.exceptions.NoEntityFoundException;
import com.example.app.repositories.AccountRepository;
import com.example.app.repositories.RoleRepository;
import com.example.app.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberService(AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountWithRoles() {
        return SecurityUtils.getCurrentAuthentication().flatMap(x-> accountRepository.findOneWithRolesByEmailAddrIgnoreCase(x.getUsername()));
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccount(){
//        return SecurityUtils.getCurrentAuthentication().flatMap(x-> accountRepository.findOneByEmailAddr(x.getUsername()));
        return SecurityUtils.getCurrentUsername().flatMap(accountRepository::findOneByEmailAddr);

    }

    public Optional<Account> saveUser(SignUpForm signUpForm) {
        Account account = Account.builder()
                .name(signUpForm.getName())
                .emailAddr(signUpForm.getId())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        Role userRole = roleRepository.findOneByName("ROLE_USER");
        account.addRole(userRole);
        return Optional.of(accountRepository.save(account));
    }

    public void updateLastLoginAt(String username) {
        Account member = accountRepository.findOneByEmailAddr(username).orElseThrow(() -> new NoEntityFoundException("Member", username));
        member.setLastLoginAt(LocalDateTime.now());
    }
}
