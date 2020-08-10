package com.example.app.security;

import com.example.app.entities.Account;
import com.example.app.exceptions.UserNotActivatedException;
import com.example.app.repositories.AccountRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Database 와 대조하여 유저 인증.
 * user login id 가 email 형식이면 email column 을 탐색함.
 */
@Component("userDetailsService")
public class AccountDetailsService implements UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(AccountDetailsService.class);

   private final AccountRepository accountRepository;

   public AccountDetailsService(AccountRepository accountRepository) {
      this.accountRepository = accountRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      if (new EmailValidator().isValid(login, null)) {
         UserDetails userDetails = accountRepository.findOneWithRolesByEmailAddrIgnoreCase(login)
            .map(account -> createSpringSecurityUser(login, account))
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
         return userDetails;
      }
      String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
      return accountRepository.findOneWithRolesByName(lowercaseLogin)
         .map(user -> createSpringSecurityUser(lowercaseLogin, user))
         .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

   }

   private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Account account) {
      if (!account.isActivated()) {
         throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }
      List<GrantedAuthority> grantedAuthorities = account.getRoles().stream()
         .map(authority -> new SimpleGrantedAuthority(authority.getName()))
         .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(account.getName(),
         account.getPassword(),
         grantedAuthorities);
   }
}
