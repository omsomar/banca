package com.bp.banca.service.implementation;

import com.bp.banca.service.declaration.TransactionStrategy;

import java.math.BigDecimal;

public class DrawbackTransactionStrategy implements TransactionStrategy {
    @Override
    public BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal amount) {
        return currentBalance.subtract(amount);
    }
}
