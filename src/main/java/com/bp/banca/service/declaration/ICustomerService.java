package com.bp.banca.service.declaration;

import com.bp.banca.dto.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ICustomerService {

    List<CustomerResponse> getAll();
    CustomerResponse getByIdentification(String identification);
    CreatedCustomerResponse create(CreateCustomerCommand customerDTO, UriComponentsBuilder uriComponentsBuilder);
    UpdatedCustomerResponse update(UUID id, UpdateCustomerCommand customerDTO);
    UpdatedCustomerResponse patch(UUID id, Map<String, Object> fieldsToUpdate);
    void delete(UUID id);
}
