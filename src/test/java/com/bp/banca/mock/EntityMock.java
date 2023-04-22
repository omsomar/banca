package com.bp.banca.mock;

import com.bp.banca.domain.model.Account;
import com.bp.banca.domain.model.Customer;
import com.bp.banca.domain.model.Person;
import com.bp.banca.domain.model.Transaction;
import com.bp.banca.dto.CreatedCustomerResponse;
import com.bp.banca.dto.PersonDTO;
import com.bp.banca.enums.AccountType;
import com.bp.banca.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EntityMock {

    public static Account accountMock() {
        return Account.builder()
                .accountNumber("1234")
                .accountId(UUID.fromString("bf53bf3b-0081-4c52-a84e-bbc93cbb6633"))
                .isActive(Boolean.TRUE)
                .transaction(List.of(transactionMock()))
                .initialBalance(new BigDecimal(1000L))
                .accountType(AccountType.CURRENT.name())
                .customer(customerMock())
                .build();
    }

    public static Account accountDrawbackMock() {
        return Account.builder()
                .accountNumber("1234")
                .accountId(UUID.fromString("bf53bf3b-0081-4c52-a84e-bbc93cbb6633"))
                .isActive(Boolean.TRUE)
                .transaction(List.of(transactionDrawbackMock()))
                .initialBalance(new BigDecimal(1000L))
                .accountType(AccountType.CURRENT.name())
                .customer(customerMock())
                .build();
    }

    public static Transaction transactionMock() {
        return Transaction.builder()
                .transactionId(UUID.fromString("d98d04f0-1f62-4791-ad1c-e47bc31bc7ce"))
                .currentBalance(new BigDecimal(1500L))
                .transactionType(TransactionType.DEPOSIT.name())
                .value(new BigDecimal(500L))
                .transactionDate(LocalDateTime.now())
                .isTransactionDone(Boolean.TRUE)
                .initialBalance(new BigDecimal(1000L))
                .build();
    }

    public static Transaction transactionDrawbackMock() {
        return Transaction.builder()
                .transactionId(UUID.fromString("d98d04f0-1f62-4791-ad1c-e47bc31bc7ce"))
                .currentBalance(new BigDecimal(1500L))
                .transactionType(TransactionType.DRAWBACK.name())
                .value(new BigDecimal(500L))
                .transactionDate(LocalDateTime.now())
                .isTransactionDone(Boolean.TRUE)
                .initialBalance(new BigDecimal(1000L))
                .build();
    }

    public static Customer customerMock() {
        return Customer.builder()
                .person(personMock())
                .status(Boolean.TRUE)
                .customerId(UUID.fromString("bf53bf3b-0081-4c52-a84e-bbc93cbb6633"))
                .password("1234abc")
                .build();
    }

    public static Person personMock() {
        return Person.builder()
                .identification("12345")
                .phone("1234567890")
                .address("Alpina")
                .gender('M')
                .dateBirth(LocalDate.of(1990, 1, 1).atStartOfDay())
                .name("John Doe")
                .build();

    }

    public static PersonDTO personMockDTO() {
        return PersonDTO.builder()
                .identification("12345")
                .phone("1234567890")
                .address("Alpina")
                .gender('M')
                .dateBirth(LocalDate.of(1990, 1, 1).atStartOfDay())
                .name("John Doe")
                .build();

    }

    public static CreatedCustomerResponse createdCustomerResponseMock() {
        return CreatedCustomerResponse.builder()
                .customerId(UUID.fromString("bf53bf3b-0081-4c52-a84e-bbc93cbb6633"))
                .status(Boolean.TRUE)
                .build();
    }
}
