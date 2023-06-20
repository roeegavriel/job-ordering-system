package com.roee.joborderingsystem.services.customer;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }
}
