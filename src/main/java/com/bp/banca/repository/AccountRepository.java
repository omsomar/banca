package com.bp.banca.repository;

import com.bp.banca.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<List<Account>> findByCustomerPersonIdentification(String identification);
    Optional<List<Account>> findByCustomerCustomerId(UUID customerId);
}
