package com.bp.banca.controller.declaration;

import com.bp.banca.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(value = "/customer")
@Tag(name = "Clientes")
public interface ICustomer {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene todos los clientes de la banca")
    ResponseEntity<List<CustomerResponse>> getAll();

    @GetMapping(value = "/{identification}")
    ResponseEntity<CustomerResponse> getById(@PathVariable String identification);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedCustomerResponse> create(@RequestBody CreateCustomerCommand customer, UriComponentsBuilder uriBuilder);

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdatedCustomerResponse> update(@PathVariable UUID id, @RequestBody UpdateCustomerCommand updateCustomerCommand);

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdatedCustomerResponse> patch(@PathVariable UUID id, @RequestBody Map<String, Object> fieldsToUpdate);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
