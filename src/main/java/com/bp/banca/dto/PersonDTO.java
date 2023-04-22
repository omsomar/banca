package com.bp.banca.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonIgnore
    private UUID personId;
    private String name;
    private char gender;
    private LocalDateTime dateBirth;
    private String identification;
    private String address;
    private String phone;

}
