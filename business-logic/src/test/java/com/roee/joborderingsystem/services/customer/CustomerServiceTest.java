package com.roee.joborderingsystem.services.customer;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.repositories.CustomerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(Instancio.create(Customer.class)));
    }

    @Nested
    class FindByIdTests {

        @Test
        @DisplayName("validate findById invokes customerRepository.findById")
        void validateFindByIdInvokesCustomerRepositoryFindById() {
            customerService.findById(23071983L);

            verify(customerRepository).findById(23071983L);
        }

        @Test
        @DisplayName("validate findById returns the customer returned by customerRepository.findById")
        void validateFindByIdReturnsCustomerReturnedByCustomerRepositoryFindById() {
            Customer customer = Instancio.create(Customer.class);
            when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

            Customer returnedCustomer = customerService.findById(1L);

            assertEquals(customer, returnedCustomer);
        }

        @Test
        @DisplayName("validate findById throws NoSuchElementException if no customer returned by customerRepository.findById")
        void validateFindByIdThrowsNoSuchElementExceptionIfNoCustomerReturnedByCustomerRepositoryFindById() {
            when(customerRepository.findById(any())).thenReturn(Optional.empty());

            NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> customerService.findById(1L));

            assertEquals("Customer not found", noSuchElementException.getMessage());
        }
    }
}
