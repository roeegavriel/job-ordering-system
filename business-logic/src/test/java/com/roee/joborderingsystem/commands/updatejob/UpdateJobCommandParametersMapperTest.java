package com.roee.joborderingsystem.commands.updatejob;

import com.roee.joborderingsystem.entities.Job;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateJobCommandParametersMapperTest {

    private UpdateJobCommandParametersMapper updateJobCommandParametersMapper = new UpdateJobCommandParametersMapperImpl();

    @Test
    @DisplayName("validate toCreateJobParameters map correctly")
    void validateToCreateJobParameters() {
        Job job = Instancio.create(Job.class);
        UpdateJobCommandParameters updateJobCommandParameters = Instancio.create(UpdateJobCommandParameters.class);

        Job updatedJob = updateJobCommandParametersMapper.updateJob(job, updateJobCommandParameters);

        assertAll(
            () -> assertEquals(job.getId(), updatedJob.getId()),
            () -> assertEquals(job.getCreatedAt(), updatedJob.getCreatedAt()),
            () -> assertEquals(job.getUpdatedAt(), updatedJob.getUpdatedAt()),
            () -> assertEquals(updateJobCommandParameters.category(), updatedJob.getCategory()),
            () -> assertEquals(updateJobCommandParameters.description(), updatedJob.getDescription()),
            () -> assertEquals(updateJobCommandParameters.dueDate(), updatedJob.getDueDate()),
            () -> assertEquals(updateJobCommandParameters.paymentMethod(), updatedJob.getPaymentMethod()),
            () -> assertEquals(updateJobCommandParameters.price(), updatedJob.getPrice())
        );
    }
}