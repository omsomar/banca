package com.bp.banca.dto;

import com.bp.banca.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class AccountResponse {

    private String accountNumber;
    private AccountType type;
    private BigDecimal initialBalance;
    private boolean isActive;
    private String customer;
}
