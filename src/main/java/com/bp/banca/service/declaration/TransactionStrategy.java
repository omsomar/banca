package com.bp.banca.service.declaration;

import java.math.BigDecimal;

public interface TransactionStrategy {
    BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal amount);
}
