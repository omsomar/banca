package com.bp.banca.service.declaration;

import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IAccountService {

    List<AccountResponse> findAll();
    AccountResponse findByNumber(String accountNumber);

    List<AccountResponse> findByCustomerId(UUID customerId);

    CreatedAccountResponse create(CreateAccountCommand accountDTO, UriComponentsBuilder uriComponentsBuilder);

    CreatedAccountResponse update(UUID id, CreateAccountCommand accountDTO);

    CreatedAccountResponse partialUpdate(UUID id, Map<String, Object> updatesFields);

    void delete(String number);
}
