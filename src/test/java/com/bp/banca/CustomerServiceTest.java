package com.bp.banca;

import com.bp.banca.domain.model.Customer;
import com.bp.banca.dto.CreateCustomerCommand;
import com.bp.banca.dto.CreatedCustomerResponse;
import com.bp.banca.dto.CustomerResponse;
import com.bp.banca.exception.CustomerNotFoundException;
import com.bp.banca.exception.FatalException;
import com.bp.banca.repository.CustomerRepository;
import com.bp.banca.service.implementation.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bp.banca.mock.EntityMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomerSuccess() {
        CreateCustomerCommand command = CreateCustomerCommand.builder()
                .person(personMockDTO())
                .build();
        CreatedCustomerResponse expectedResponse = CreatedCustomerResponse.builder()
                .customerId(UUID.fromString("bf53bf3b-0081-4c52-a84e-bbc93cbb6633"))
                .status(Boolean.TRUE)
                .build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("http://localhost:8080/customers");
        when(customerRepository.save(any(Customer.class))).thenReturn(customerMock());
        CreatedCustomerResponse actualResponse = customerService.create(command, builder);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void testCreateCustomerFails() {
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand.builder()
                .person(personMockDTO())
                .build();

        when(customerRepository.save(any(Customer.class))).thenThrow(new FatalException("Error"));
        assertThrows(FatalException.class, () ->
            customerService.create(createCustomerCommand, UriComponentsBuilder.newInstance()));
    }

    @Test
    void testGetAll() {
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setCustomerId(id);
        customer1.setPerson(personMock());
        Customer customer2 = new Customer();
        customer2.setCustomerId(id2);
        customer2.setPerson(personMock());
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerResponse> customerResponses = customerService.getAll();
        assertThat(customerResponses).isNotEmpty().hasSize(2);
        assertThat(customerResponses.get(0).getCustomerId()).isEqualTo(id);
        assertThat(customerResponses.get(1).getCustomerId()).isEqualTo(id2);
    }

    @Test
    void testGetByIdentificationWhenCustomerNotFound() {
        when(customerRepository.findByPersonIdentification("12345678")).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getByIdentification("12345678");
        });
    }

}
