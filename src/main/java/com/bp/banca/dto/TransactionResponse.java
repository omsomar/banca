package com.bp.banca.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TransactionResponse {

    @JsonIgnore
    private UUID transactionId;
    private LocalDateTime date;
    private String transactionType;
    private BigDecimal value;
    private boolean isTransactionDone;
}
