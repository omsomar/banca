package com.bp.banca.mapper;

import com.bp.banca.domain.model.Customer;
import com.bp.banca.dto.CreateCustomerCommand;
import com.bp.banca.dto.CreatedCustomerResponse;
import com.bp.banca.dto.CustomerResponse;
import com.bp.banca.dto.UpdatedCustomerResponse;

import java.util.List;
import java.util.UUID;

public class CustomerMapper {

    private CustomerMapper(){}

    public static Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommandDTO) {
        return Customer.builder()
                .customerId(UUID.randomUUID())
                .person(PersonMapper.toPerson(createCustomerCommandDTO.getPerson()))
                .password(createCustomerCommandDTO.getPassword())
                .status(Boolean.TRUE)
                .build();
    }

    public static CreatedCustomerResponse customerToCreatedCustomerResponse(Customer customer) {
        return CreatedCustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .status(customer.isStatus())
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .address(customer.getPerson().getAddress())
                .name(customer.getPerson().getName())
                .phone(customer.getPerson().getPhone())
                .identification(customer.getPerson().getIdentification())
                .isActive(customer.isStatus())
                .build();
    }

    public static List<CustomerResponse> customersToCustomersResponse(List<Customer> customerList) {
        return customerList.stream()
                .map(CustomerMapper::customerToCustomerResponse)
                .toList();
    }

    public static UpdatedCustomerResponse customerToUpdatedCustomerResponse(Customer customer) {
        return UpdatedCustomerResponse.builder()
                .dateBirth(customer.getPerson().getDateBirth())
                .gender(customer.getPerson().getGender())
                .address(customer.getPerson().getAddress())
                .name(customer.getPerson().getName())
                .phone(customer.getPerson().getPhone())
                .build();
    }
}
