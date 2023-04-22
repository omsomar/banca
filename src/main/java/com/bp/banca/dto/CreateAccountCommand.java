package com.bp.banca.dto;

import com.bp.banca.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCommand {

    @NotNull(message = "Por favor ingresar una cuenta valida")
    private String accountNumber;

    @NotNull(message = "Por favor seleccione un tipo de cuenta")
    private AccountType accountType;

    @NotNull(message = "el valor inicial no puede ser cero")
    private BigDecimal initialBalance;

    @JsonProperty("customerId")
    @NotNull(message = "Debe asociar un cliente a la cuenta")
    private UUID customerId;
}
