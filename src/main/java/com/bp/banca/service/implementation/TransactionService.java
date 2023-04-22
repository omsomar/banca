package com.bp.banca.service.implementation;

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
import com.bp.banca.mapper.TransactionMapper;
import com.bp.banca.repository.AccountRepository;
import com.bp.banca.repository.TransactionRepository;
import com.bp.banca.service.declaration.ITransactionService;
import com.bp.banca.service.declaration.TransactionStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bp.banca.mapper.TransactionMapper.transactionToTransactionResponse;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final Map<TransactionType, TransactionStrategy> transactionStrategyMap;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, Map<TransactionType, TransactionStrategy> transactionStrategyMap) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionStrategyMap = transactionStrategyMap;

        this.transactionStrategyMap.put(TransactionType.DEPOSIT, new DebitTransactionStrategy());
        this.transactionStrategyMap.put(TransactionType.DRAWBACK, new DrawbackTransactionStrategy());
    }


    @Override
    public List<TransactionReportResponse> getByDateAndCustomer(LocalDate fechaInicio, LocalDate fechaFin, String identificacion) {
        List<Transaction> transactions = transactionRepository.findByAccountCustomerPersonIdentification(identificacion)
                .orElseThrow(() -> new TransactionNotFoundException("No hay transacciones asociadas al número de identificación"));
        return transactions.stream().map(TransactionMapper::transactionToTransactionReportResponse).toList();

    }

    @Transactional
    @Override
    public TransactionResponse makeTransaction(TransactionCommand transactionCommand) {
        Account account = accountRepository.findByAccountNumber(transactionCommand.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Número de cuenta inválido"));

        TransactionType transactionType = transactionCommand.getTransactionType();
        TransactionStrategy transactionStrategy = transactionStrategyMap.get(transactionType);

        if(transactionStrategy == null) {
            throw new InvalidTransactionTypeException("Tipo de transacción inválida");
        }
        final BigDecimal initialBalance = account.getInitialBalance();
        final BigDecimal updatedBalance = transactionStrategy.calculateNewBalance(account.getInitialBalance(), transactionCommand.getValue());
        if(account.getInitialBalance().compareTo(transactionCommand.getValue()) < 0) {
            throw new InsufficientFundsException("La cuenta no tiene saldo suficiente para realizar esta transacción");
        }

        account.setInitialBalance(updatedBalance);
        Account accountUpdated = accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID())
                .transactionDate(LocalDateTime.now())
                .initialBalance(initialBalance)
                .currentBalance(updatedBalance)
                .value(transactionCommand.getValue())
                .transactionType(transactionType.name())
                .account(accountUpdated)
                .build();

        return transactionToTransactionResponse(transactionRepository.save(transaction));
    }
}
