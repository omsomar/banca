package com.bp.banca.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCustomerCommand {

    private PersonDTO person;
    private String password;

}
