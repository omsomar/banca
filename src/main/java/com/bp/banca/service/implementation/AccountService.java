package com.bp.banca.service.implementation;

import com.bp.banca.domain.model.Account;
import com.bp.banca.domain.model.Customer;
import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;
import com.bp.banca.exception.AccountNotFoundException;
import com.bp.banca.exception.CustomerNotFoundException;
import com.bp.banca.exception.FatalException;
import com.bp.banca.repository.AccountRepository;
import com.bp.banca.repository.CustomerRepository;
import com.bp.banca.service.declaration.IAccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bp.banca.mapper.AccountMapper.*;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<AccountResponse> findAll() {
        return accountListToAccountResponseList(accountRepository.findAll());
    }

    @Override
    public AccountResponse findByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no es válida"));
        return accountToAccountResponse(account);
    }

    @Override
    public List<AccountResponse> findByCustomerId(UUID customerId) {
        return accountListToAccountResponseList(accountRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no es válida")));
    }

    @Override
    public CreatedAccountResponse create(CreateAccountCommand accountDTO, UriComponentsBuilder uriComponentsBuilder) {
        final Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        Account account = createAccountCommandToAccount(accountDTO);
        account.setCustomer(customer);
        return accountToCreatedAccountResponse(accountRepository.save(account));
    }

    @Override
    public CreatedAccountResponse update(UUID id, CreateAccountCommand accountDTO) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no es válida"));
        final Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        account.setInitialBalance(accountDTO.getInitialBalance());
        account.setCustomer(customer);
        account.setActive(account.isActive());
        return accountToCreatedAccountResponse(accountRepository.save(account));
    }

    @Override
    public CreatedAccountResponse partialUpdate(UUID id, Map<String, Object> updatesFields) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no es válida"));
        try {
            BeanUtils.populate(account, updatesFields);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new FatalException(e.getMessage());
        }
        return accountToCreatedAccountResponse(accountRepository.save(account));
    }

    @Override
    public void delete(String number) {
        Account account = accountRepository.findByAccountNumber(number)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no es válida"));
        account.setActive(Boolean.FALSE);
        accountRepository.save(account);
    }
}
