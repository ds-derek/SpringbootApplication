package com.example.app.repositories;

import com.example.app.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findOneWithRolesByName(String name);
    Optional<Account> findOneWithRolesByEmailAddrIgnoreCase(String emailAddr);
    Optional<Account> findOneByEmailAddr(String emailAddr);
}
