package com.roee.joborderingsystem.commands.indexjobs;

import com.roee.joborderingsystem.entities.Job;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexJobCommandJobResponseMapperTest {

    private IndexJobCommandJobResponseMapper indexJobCommandJobResponseMapper = new IndexJobCommandJobResponseMapperImpl();

    @Test
    @DisplayName("validate fromJob map correctly")
    void validateFromIndexJobCommandJobResponse() {
        Job job = Instancio.create(Job.class);

        IndexJobCommandJobResponse indexJobCommandJobResponse = indexJobCommandJobResponseMapper.fromJob(job);

        assertAll(
            () -> assertEquals(job.getId(), indexJobCommandJobResponse.id()),
            () -> assertEquals(job.getCategory(), indexJobCommandJobResponse.category()),
            () -> assertEquals(job.getDescription(), indexJobCommandJobResponse.description()),
            () -> assertEquals(job.getCustomer().getId(), indexJobCommandJobResponse.customerId()),
            () -> assertEquals(job.getDueDate(), indexJobCommandJobResponse.dueDate()),
            () -> assertEquals(job.getPaymentMethod(), indexJobCommandJobResponse.paymentMethod()),
            () -> assertEquals(job.getPrice(), indexJobCommandJobResponse.price()),
            () -> assertEquals(job.getCreatedAt(), indexJobCommandJobResponse.createdAt()),
            () -> assertEquals(job.getUpdatedAt(), indexJobCommandJobResponse.updatedAt())
        );
    }

}