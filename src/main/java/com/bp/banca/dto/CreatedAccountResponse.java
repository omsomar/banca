package com.bp.banca.dto;

import com.bp.banca.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class CreatedAccountResponse {

    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private String customer;
    private boolean isActive;

}
