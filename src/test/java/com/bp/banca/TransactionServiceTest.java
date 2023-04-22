package com.bp.banca;

import com.bp.banca.domain.model.Account;
import com.bp.banca.domain.model.Transaction;
import com.bp.banca.dto.TransactionCommand;
import com.bp.banca.dto.TransactionReportResponse;
import com.bp.banca.dto.TransactionResponse;
import com.bp.banca.enums.TransactionType;
import com.bp.banca.exception.AccountNotFoundException;
import com.bp.banca.exception.InsufficientFundsException;
import com.bp.banca.exception.InvalidTransactionTypeException;
import com.bp.banca.exception.TransactionNotFoundException;
import com.bp.banca.repository.AccountRepository;
import com.bp.banca.repository.TransactionRepository;
import com.bp.banca.service.declaration.TransactionStrategy;
import com.bp.banca.service.implementation.DebitTransactionStrategy;
import com.bp.banca.service.implementation.DrawbackTransactionStrategy;
import com.bp.banca.service.implementation.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bp.banca.mock.EntityMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    Map<TransactionType, TransactionStrategy> transactionStrategyMap;
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionStrategyMap = new HashMap<>();
        transactionStrategyMap.put(TransactionType.DEPOSIT, new DebitTransactionStrategy());
        transactionStrategyMap.put(TransactionType.DRAWBACK, new DrawbackTransactionStrategy());

        transactionService = new TransactionService(transactionRepository, accountRepository, transactionStrategyMap);
    }

    @Test
    void testGetByDateAndCustomerWithValidTransactions() {
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFin = LocalDate.of(2022, 12, 31);
        String identificacion = "12345";

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder()
                .transactionId(UUID.fromString("469a17c1-159f-4db2-9773-94f7e224b952"))
                .transactionDate(LocalDateTime.now())
                .initialBalance(BigDecimal.valueOf(1000))
                .currentBalance(BigDecimal.valueOf(900))
                .value(BigDecimal.valueOf(100))
                .transactionType(TransactionType.DEPOSIT.name())
                .account(accountMock())
                .build());

        when(transactionRepository.findByAccountCustomerPersonIdentification(identificacion))
                .thenReturn(Optional.of(transactions));

        List<TransactionReportResponse> response = transactionService.getByDateAndCustomer(fechaInicio, fechaFin, identificacion);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void testMakeTransactionWithValidDeposit() {
        String accountNumber = "12345678901234567890";
        BigDecimal value = BigDecimal.valueOf(500);
        TransactionCommand transactionCommand = new TransactionCommand(accountNumber, value, TransactionType.DEPOSIT);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(accountMock()));
        when(accountRepository.save(any())).thenReturn(accountMock());
        when(transactionRepository.save(any())).thenReturn(transactionMock());

        TransactionResponse response = transactionService.makeTransaction(transactionCommand);

        assertNotNull(response.getTransactionId());
        Assertions.assertEquals(value, response.getValue());
        Assertions.assertEquals(TransactionType.DEPOSIT.name(), response.getTransactionType());
    }

    @Test
    void testMakeTransactionWithValidDrawback() {
        String accountNumber = "12345678901234567890";
        BigDecimal value = BigDecimal.valueOf(500);
        TransactionCommand transactionCommand = new TransactionCommand(accountNumber, value, TransactionType.DRAWBACK);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(accountDrawbackMock()));
        when(accountRepository.save(any())).thenReturn(accountDrawbackMock());
        when(transactionRepository.save(any())).thenReturn(transactionDrawbackMock());

        TransactionResponse response = transactionService.makeTransaction(transactionCommand);

        assertNotNull(response.getTransactionId());
        Assertions.assertEquals(value, response.getValue());
        Assertions.assertEquals(TransactionType.DRAWBACK.name(), response.getTransactionType());
    }

    @Test
    void testGetByDateAndCustomerWithNoTransactions() {
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFin = LocalDate.of(2022, 12, 31);
        String identificacion = "123456789";

        when(transactionRepository.findByAccountCustomerPersonIdentification(identificacion))
                .thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () ->
                transactionService.getByDateAndCustomer(fechaInicio, fechaFin, identificacion));
    }

    @Test
    void testGetByDateAndCustomerThrowsTransactionNotFoundException() {
        when(transactionRepository.findByAccountCustomerPersonIdentification("invalidIdentification")).thenThrow(TransactionNotFoundException.class);

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getByDateAndCustomer(LocalDate.now(), LocalDate.now(), "invalidIdentification");
        });
    }

    @Test
    void testGetByDateAndCustomerReturnsEmptyList() {
        when(transactionRepository.findByAccountCustomerPersonIdentification("validIdentification")).thenReturn(java.util.Optional.of(new ArrayList<>()));

        List<TransactionReportResponse> result = transactionService.getByDateAndCustomer(LocalDate.now(), LocalDate.now(), "validIdentification");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testMakeTransactionThrowsAccountNotFoundException() {
        TransactionCommand command = new TransactionCommand(UUID.randomUUID().toString(), BigDecimal.valueOf(100), TransactionType.DEPOSIT);

        when(accountRepository.findByAccountNumber(command.getAccountNumber())).thenThrow(AccountNotFoundException.class);

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.makeTransaction(command);
        });
    }

    @Test
    public void testMakeTransaction_insufficientFunds() {
        Account account = new Account();
        account.setInitialBalance(BigDecimal.TEN);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        TransactionCommand transactionCommand = new TransactionCommand("000001", BigDecimal.valueOf(20), TransactionType.DRAWBACK);
        assertThrows(InsufficientFundsException.class, () -> transactionService.makeTransaction(transactionCommand));
    }

}
