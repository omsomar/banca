package com.bp.banca.controller.implementation;

import com.bp.banca.controller.declaration.ICustomer;
import com.bp.banca.dto.*;
import com.bp.banca.service.declaration.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController implements ICustomer {

    private final ICustomerService iCustomerService;

    @Override
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(iCustomerService.getAll());
    }

    @Override
    public ResponseEntity<CustomerResponse> getById(String identification) {
        return ResponseEntity.ok(iCustomerService.getByIdentification(identification));
    }

    @Override
    public ResponseEntity<CreatedCustomerResponse> create(CreateCustomerCommand customer, UriComponentsBuilder uriBuilder) {
        CreatedCustomerResponse customerDTO = iCustomerService.create(customer, uriBuilder);
        URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(customerDTO.getCustomerId()).toUri();
        return ResponseEntity.created(uri).body(customerDTO);
    }

    @Override
    public ResponseEntity<UpdatedCustomerResponse> update(UUID id, UpdateCustomerCommand updateCustomerCommand) {
        return ResponseEntity.ok(iCustomerService.update(id, updateCustomerCommand));
    }

    @Override
    public ResponseEntity<UpdatedCustomerResponse> patch(UUID id, Map<String, Object> fieldsToUpdate) {
        return ResponseEntity.ok(iCustomerService.patch(id, fieldsToUpdate));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        iCustomerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
