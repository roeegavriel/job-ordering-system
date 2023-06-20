package com.roee.joborderingsystem.commands.createjob;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.services.customer.CustomerService;
import com.roee.joborderingsystem.services.job.CreateJobParameters;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateJobCommandParametersMapperTest {

    @Mock
    private CustomerService customerService;

    private CreateJobCommandParametersMapper createJobCommandParametersMapper;

    @BeforeEach
    void setUp() {
        createJobCommandParametersMapper = new CreateJobCommandParametersMapperImpl();
        createJobCommandParametersMapper.setCustomerService(customerService);
    }

    @Test
    @DisplayName("validate toCreateJobParameters map correctly")
    void validateToCreateJobParameters() {
        CreateJobCommandParameters createJobCommandParameters = Instancio.create(CreateJobCommandParameters.class);
        Customer customer = Instancio.create(Customer.class);
        when(customerService.findById(createJobCommandParameters.customerId())).thenReturn(customer);

        CreateJobParameters createJobParameters = createJobCommandParametersMapper.toCreateJobParameters(createJobCommandParameters);

        assertAll(
            () -> assertEquals(customer, createJobParameters.customer()),
            () -> assertEquals(createJobCommandParameters.category(), createJobParameters.category()),
            () -> assertEquals(createJobCommandParameters.description(), createJobParameters.description()),
            () -> assertEquals(createJobCommandParameters.dueDate(), createJobParameters.dueDate()),
            () -> assertEquals(createJobCommandParameters.paymentMethod(), createJobParameters.paymentMethod()),
            () -> assertEquals(createJobCommandParameters.price(), createJobParameters.price())
        );
    }
}
