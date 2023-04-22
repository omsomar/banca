package com.bp.banca.service.implementation;

import com.bp.banca.domain.model.Customer;
import com.bp.banca.domain.model.Person;
import com.bp.banca.dto.*;
import com.bp.banca.exception.CustomerNotFoundException;
import com.bp.banca.exception.FatalException;
import com.bp.banca.repository.CustomerRepository;
import com.bp.banca.service.declaration.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.bp.banca.mapper.CustomerMapper.*;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAll() {
        return customersToCustomersResponse(customerRepository.findAll());
    }

    @Override
    public CustomerResponse getByIdentification(String identification) {
        Customer customer = customerRepository.findByPersonIdentification(identification)
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        return customerToCustomerResponse(customer);
    }

    @Override
    public CreatedCustomerResponse create(CreateCustomerCommand createCustomerCommand, UriComponentsBuilder uriComponentsBuilder) {
        return Optional.ofNullable(customerToCreatedCustomerResponse(
                customerRepository.save(createCustomerCommandToCustomer(createCustomerCommand))))
                .orElseThrow(() -> new FatalException("No se pudo crear el cliente"));
    }

    @Override
    public UpdatedCustomerResponse update(UUID id, UpdateCustomerCommand updateCustomerCommand) {
        Customer existCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        existCustomer.setPerson(
                Person.builder()
                        .personId(existCustomer.getPerson().getPersonId())
                        .gender(updateCustomerCommand.getGender())
                        .name(updateCustomerCommand.getName())
                        .phone(updateCustomerCommand.getPhone())
                        .address(updateCustomerCommand.getAddress())
                        .identification(existCustomer.getPerson().getIdentification())
                        .dateBirth(updateCustomerCommand.getDateBirth())
                        .customer(existCustomer)
                        .build());
        return customerToUpdatedCustomerResponse(
                        customerRepository.save(existCustomer));

    }

    @Override
    public UpdatedCustomerResponse patch(UUID id, Map<String, Object> updatesFields) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        try {
            BeanUtils.populate(customer.getPerson(), updatesFields);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new FatalException(e.getMessage());
        }

        return customerToUpdatedCustomerResponse(
                customerRepository.save(customer));
    }

    @Override
    public void delete(UUID id) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("El cliente no es válido"));
        customer.setStatus(Boolean.FALSE);
        customerRepository.save(customer);
    }
}
