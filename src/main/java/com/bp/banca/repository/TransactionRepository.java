package com.bp.banca.repository;

import com.bp.banca.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<List<Transaction>> findByAccountCustomerPersonIdentification(String accountId);
}
