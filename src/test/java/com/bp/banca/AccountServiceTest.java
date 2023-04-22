package com.bp.banca;


import com.bp.banca.domain.model.Account;
import com.bp.banca.domain.model.Customer;
import com.bp.banca.domain.model.Person;
import com.bp.banca.dto.AccountResponse;
import com.bp.banca.dto.CreateAccountCommand;
import com.bp.banca.dto.CreatedAccountResponse;
import com.bp.banca.enums.AccountType;
import com.bp.banca.exception.AccountNotFoundException;
import com.bp.banca.exception.CustomerNotFoundException;
import com.bp.banca.repository.AccountRepository;
import com.bp.banca.repository.CustomerRepository;
import com.bp.banca.service.implementation.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.bp.banca.mock.EntityMock.accountMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testCreateAccount() {
        CreateAccountCommand command = new CreateAccountCommand();
        command.setCustomerId(UUID.randomUUID());
        command.setInitialBalance(BigDecimal.TEN);
        command.setAccountType(AccountType.CURRENT);
        command.setAccountNumber("1234");


        Customer customer = new Customer();
        when(customerRepository.findById(command.getCustomerId())).thenReturn(Optional.of(customer));
        when(accountRepository.save(any())).thenReturn(accountMock());
        CreatedAccountResponse response = accountService.create(command, UriComponentsBuilder.newInstance());
        assertNotNull(response);
    }

    @Test
    void testFindByNumber() {
        Account account = new Account();
        account.setAccountNumber("1234");
        when(accountRepository.findByAccountNumber("1234")).thenReturn(Optional.ofNullable(accountMock()));
        AccountResponse response = accountService.findByNumber("1234");
        assertNotNull(response);
        assertEquals("1234", response.getAccountNumber());
    }

    @Test
    void testFindByNumber_InvalidAccountNumber() {
        when(accountRepository.findByAccountNumber("1234")).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.findByNumber("1234"));
    }

    @Test
    void testFindByCustomerId_CustomerNotFound() {
        UUID fakeCustomerId = UUID.randomUUID();
        when(accountRepository.findByCustomerCustomerId(fakeCustomerId)).thenReturn(Optional.empty());
        assertThrows( AccountNotFoundException.class, () -> accountService.findByCustomerId(fakeCustomerId));
    }

    @Test
    void testCreate_CustomerNotFound() {
        UUID fakeCustomerId = UUID.randomUUID();
        CreateAccountCommand accountDTO = new CreateAccountCommand();
        accountDTO.setCustomerId(fakeCustomerId);
        when(customerRepository.findById(fakeCustomerId)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> accountService.create(accountDTO, UriComponentsBuilder.newInstance()));
    }
}
