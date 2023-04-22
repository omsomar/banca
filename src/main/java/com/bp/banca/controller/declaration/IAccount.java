package com.bp.banca.controller.declaration;

import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(value = "/account")
@Tag(name = "Cuenta")
public interface IAccount {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AccountResponse>> findAll();
    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountResponse> getByNumber(@PathVariable String accountNumber);
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AccountResponse>> getByCustomer(UUID customerId);
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedAccountResponse> create(@RequestBody @Valid CreateAccountCommand accountDTO, UriComponentsBuilder uriComponentsBuilder);
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedAccountResponse> put(@PathVariable UUID id, CreateAccountCommand accountDTO);
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedAccountResponse> patch(@PathVariable UUID id, @RequestBody Map<String, Object> updatesFields);
    @DeleteMapping(value = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> delete(@PathVariable String number);
}
