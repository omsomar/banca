package com.bp.banca.dto;

import com.bp.banca.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCommand {
    private String accountNumber;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
