package com.bp.banca.controller.declaration;

import com.bp.banca.dto.TransactionCommand;
import com.bp.banca.dto.TransactionReportResponse;
import com.bp.banca.dto.TransactionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/transaction")
@Tag(name = "Movimientos")
public interface ITransaction {

    @GetMapping
    ResponseEntity<List<TransactionReportResponse>> getByDateAndUser(@RequestParam("fecha_inicio")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @RequestParam("fecha_fin")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin, @RequestParam("identificacion") String identification);

    @PostMapping
    ResponseEntity<TransactionResponse> makeTransaction(@RequestBody @Valid TransactionCommand transactionDepositCommand);
}
