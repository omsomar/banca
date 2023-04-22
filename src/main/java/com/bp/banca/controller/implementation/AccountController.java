package com.bp.banca.controller.implementation;

import com.bp.banca.controller.declaration.IAccount;
import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;
import com.bp.banca.dto.CreatedCustomerResponse;
import com.bp.banca.service.declaration.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AccountController implements IAccount {

    private final IAccountService iAccountService;

    @Override
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.ok(iAccountService.findAll());
    }

    @Override
    public ResponseEntity<AccountResponse> getByNumber(String accountNumber) {
        return ResponseEntity.ok(iAccountService.findByNumber(accountNumber));
    }

    @Override
    public ResponseEntity<List<AccountResponse>> getByCustomer(UUID customerId) {
        return ResponseEntity.ok(iAccountService.findByCustomerId(customerId));
    }

    @Override
    public ResponseEntity<CreatedAccountResponse> create(CreateAccountCommand accountDTO, UriComponentsBuilder uriComponentsBuilder) {
        final CreatedAccountResponse account = iAccountService.create(accountDTO, uriComponentsBuilder);
        final URI uri = uriComponentsBuilder.path("/account/{number}").buildAndExpand(account.getAccountNumber()).toUri();
        return ResponseEntity.created(uri).body(account);
    }

    @Override
    public ResponseEntity<CreatedAccountResponse> put(UUID id, CreateAccountCommand accountDTO) {
        return ResponseEntity.ok(iAccountService.update(id, accountDTO));
    }

    @Override
    public ResponseEntity<CreatedAccountResponse> patch(UUID id, Map<String, Object> updatesFields) {
        return ResponseEntity.ok(iAccountService.partialUpdate(id, updatesFields));
    }

    @Override
    public ResponseEntity<Void> delete(String number) {
        iAccountService.delete(number);
        return ResponseEntity.noContent().build();
    }
}
