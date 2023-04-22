package com.bp.banca.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    private LocalDateTime transactionDate;
    private String transactionType;
    private BigDecimal value;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private boolean isTransactionDone;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
