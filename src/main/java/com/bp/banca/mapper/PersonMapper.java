package com.bp.banca.mapper;

import com.bp.banca.domain.model.Person;
import com.bp.banca.dto.PersonDTO;

import java.util.UUID;

public class PersonMapper {
    public static Person toPerson(PersonDTO personDTO) {
        return Person.builder()
                .address(personDTO.getAddress())
                .dateBirth(personDTO.getDateBirth())
                .gender(personDTO.getGender())
                .identification(personDTO.getIdentification())
                .name(personDTO.getName())
                .personId(UUID.randomUUID())
                .phone(personDTO.getPhone())
                .build();
    }

    public static PersonDTO toPersonDTO(Person person) {
        return PersonDTO.builder()
                .address(person.getAddress())
                .dateBirth(person.getDateBirth())
                .gender(person.getGender())
                .identification(person.getIdentification())
                .name(person.getName())
                .personId(person.getPersonId())
                .phone(person.getPhone())
                .build();
    }
}
