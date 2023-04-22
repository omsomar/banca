package com.bp.banca.mapper;

import com.bp.banca.enums.AccountType;
import com.bp.banca.domain.model.Account;
import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;

import java.util.List;
import java.util.UUID;

public class AccountMapper {

    private AccountMapper(){}

    public static Account createAccountCommandToAccount(CreateAccountCommand accountDTO) {
        return Account.builder()
                .accountId(UUID.randomUUID())
                .accountNumber(accountDTO.getAccountNumber())
                .accountType(accountDTO.getAccountType().name())
                .initialBalance(accountDTO.getInitialBalance())
                .isActive(Boolean.TRUE)
                .build();
    }

    public static CreatedAccountResponse accountToCreatedAccountResponse(Account account) {
        return CreatedAccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountType.valueOf(account.getAccountType()))
                .initialBalance(account.getInitialBalance())
                .isActive(account.isActive())
                .customer(account.getCustomer().getPerson().getName())
                .build();
    }

    public static List<AccountResponse> accountListToAccountResponseList(List<Account> accountList) {
        return accountList.stream().map(AccountMapper::accountToAccountResponse).toList();
    }

    public static AccountResponse accountToAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .isActive(account.isActive())
                .type(AccountType.valueOf(account.getAccountType()))
                .customer(account.getCustomer().getPerson().getName())
                .initialBalance(account.getInitialBalance())
                .build();
    }
}
