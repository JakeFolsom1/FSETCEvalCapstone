package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, String> {
    List<Account> findAccountsByIsActive(Boolean isActive);
}
