package com.bp.banca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreatedCustomerResponse {

    @EqualsAndHashCode.Include
    private UUID customerId;
    @EqualsAndHashCode.Include
    private boolean status;

}
