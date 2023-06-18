package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobsV1MapperTest {

    JobsV1Mapper jobsV1Mapper = new JobsV1MapperImpl();

    @Test
    @DisplayName("validate toCreateJobCommandParameters map correctly")
    void validateToCreateJobCommandParameters() {
        JobCreateData jobCreateData = Instancio.create(JobCreateData.class);

        CreateJobCommandParameters createJobCommandParameters = jobsV1Mapper.toCreateJobCommandParameters(jobCreateData);

        assertAll(
            () -> assertEquals(jobCreateData.getCustomerId(), createJobCommandParameters.customerId()),
            () -> assertEquals(jobCreateData.getCategory(), createJobCommandParameters.category()),
            () -> assertEquals(jobCreateData.getDescription(), createJobCommandParameters.description()),
            () -> assertEquals(jobCreateData.getDueDate(), createJobCommandParameters.dueDate()),
            () -> assertEquals(jobCreateData.getPaymentMethod(), createJobCommandParameters.paymentMethod()),
            () -> assertEquals(jobCreateData.getPrice(), createJobCommandParameters.price())
        );
    }
}