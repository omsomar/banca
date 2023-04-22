package com.bp.banca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CustomerResponse {

    private UUID customerId;
    private String name;
    private String address;
    private String phone;
    private String identification;
    private boolean isActive;

}
