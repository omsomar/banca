package com.bp.banca.controller.implementation;

import com.bp.banca.controller.declaration.ITransaction;
import com.bp.banca.dto.TransactionCommand;
import com.bp.banca.dto.TransactionReportResponse;
import com.bp.banca.dto.TransactionResponse;
import com.bp.banca.service.declaration.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TransactionController implements ITransaction {

    private final ITransactionService iTransactionService;

    @Override
    public ResponseEntity<List<TransactionReportResponse>> getByDateAndUser(LocalDate fechaInicio, LocalDate fechaFin, String identification) {
        return ResponseEntity.ok(iTransactionService.getByDateAndCustomer(fechaInicio, fechaFin, identification));
    }

    @Override
    public ResponseEntity<TransactionResponse> makeTransaction(TransactionCommand transactionDepositCommand) {
        return ResponseEntity.ok(iTransactionService.makeTransaction(transactionDepositCommand));
    }
}
