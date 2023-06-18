package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateJobParametersMapperTest {

    private CreateJobParametersMapper createJobParametersMapper = new CreateJobParametersMapperImpl();

    @Test
    @DisplayName("validate toJob map correctly")
    void validateToJob() {
        CreateJobParameters createJobParameters = Instancio.create(CreateJobParameters.class);

        Job job = createJobParametersMapper.toJob(createJobParameters);

        assertAll(
            () -> assertEquals(createJobParameters.customer(), job.getCustomer()),
            () -> assertEquals(createJobParameters.category(), job.getCategory()),
            () -> assertEquals(createJobParameters.description(), job.getDescription()),
            () -> assertEquals(createJobParameters.dueDate(), job.getDueDate()),
            () -> assertEquals(createJobParameters.paymentMethod(), job.getPaymentMethod()),
            () -> assertEquals(createJobParameters.price(), job.getPrice())
        );
    }
}
