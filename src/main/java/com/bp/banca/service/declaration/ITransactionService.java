package com.bp.banca.service.declaration;

import com.bp.banca.dto.TransactionCommand;
import com.bp.banca.dto.TransactionReportResponse;
import com.bp.banca.dto.TransactionResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {

    List<TransactionReportResponse> getByDateAndCustomer(LocalDate fechaInicio, LocalDate fechaFin, String identification);

    TransactionResponse makeTransaction(TransactionCommand transactionDepositCommand);
}
