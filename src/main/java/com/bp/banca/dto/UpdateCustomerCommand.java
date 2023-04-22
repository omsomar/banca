package com.bp.banca.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UpdateCustomerCommand {
    private String name;
    private char gender;
    private LocalDateTime dateBirth;
    private String address;
    private String phone;

}
