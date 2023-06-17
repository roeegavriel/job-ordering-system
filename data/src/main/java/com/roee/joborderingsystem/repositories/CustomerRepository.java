package com.roee.joborderingsystem.repositories;

import com.roee.joborderingsystem.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
