package com.bp.banca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatedCustomerResponse {

    private String name;
    private char gender;
    private LocalDateTime dateBirth;
    private String address;
    private String phone;

}
