package com.bp.banca.mapper;

import com.bp.banca.domain.model.Transaction;
import com.bp.banca.dto.TransactionReportResponse;
import com.bp.banca.dto.TransactionResponse;
import com.bp.banca.enums.TransactionType;

public class TransactionMapper {

    public static TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType())
                .date(transaction.getTransactionDate())
                .value(transaction.getValue())
                .isTransactionDone(transaction.isTransactionDone())
                .build();
    }

    public static TransactionReportResponse transactionToTransactionReportResponse(Transaction transaction) {
        return TransactionReportResponse.builder()
                .fecha(transaction.getTransactionDate())
                .cliente(transaction.getAccount().getCustomer().getPerson().getName())
                .numeroCuenta(transaction.getAccount().getAccountNumber())
                .tipoCuenta(transaction.getAccount().getAccountType())
                .saldoInicial(transaction.getInitialBalance())
                .estado(transaction.isTransactionDone())
                .movimiento(transaction.getTransactionType().equals(TransactionType.DRAWBACK.name())?transaction.getValue().negate():transaction.getValue())
                .saldoDisponible(transaction.getCurrentBalance())
                .build();
    }
}
