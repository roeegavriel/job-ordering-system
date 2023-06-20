package com.roee.joborderingsystem.commands.getjob;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.Worker;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetJobCommandResponseMapperTest {

    private GetJobCommandResponseMapper getJobCommandResponseMapper = new GetJobCommandResponseMapperImpl();

    @Test
    @DisplayName("validate fromJobAndWorker map the job correctly")
    void validateFromJobAndWorker() {
        Job job = Instancio.create(Job.class);
        Customer customer = Instancio.create(Customer.class);
        job.setCustomer(customer);

        GetJobCommandResponse getJobCommandResponse = getJobCommandResponseMapper.fromJobAndWorker(job, Optional.empty());

        assertAll(
            () -> assertEquals(job.getCategory(), getJobCommandResponse.category()),
            () -> assertEquals(job.getDescription(), getJobCommandResponse.description()),
            () -> assertEquals(job.getDueDate(), getJobCommandResponse.dueDate()),
            () -> assertEquals(job.getPaymentMethod(), getJobCommandResponse.paymentMethod()),
            () -> assertEquals(job.getPrice(), getJobCommandResponse.price()),
            () -> assertEquals(job.getCustomer().getId(), getJobCommandResponse.customerId()),
            () -> assertEquals(job.getCreatedAt(), getJobCommandResponse.createdAt()),
            () -> assertEquals(job.getUpdatedAt(), getJobCommandResponse.updatedAt())
        );
    }

    @Test
    @DisplayName("validate fromJobAndWorker set the correct worker id when worker is present")
    void validatePresentWorkerFromJobAndWorker() {
        Job job = Instancio.create(Job.class);
        Customer customer = Instancio.create(Customer.class);
        job.setCustomer(customer);
        Worker worker = Instancio.create(Worker.class);

        GetJobCommandResponse getJobCommandResponse = getJobCommandResponseMapper.fromJobAndWorker(job, Optional.of(worker));

        assertEquals(worker.getId(), getJobCommandResponse.workerId());
    }

    @Test
    @DisplayName("validate fromJobAndWorker set null worker id when worker is absent")
    void validateAbsentWorkerFromJobAndWorker() {
        Job job = Instancio.create(Job.class);
        Customer customer = Instancio.create(Customer.class);
        job.setCustomer(customer);

        GetJobCommandResponse getJobCommandResponse = getJobCommandResponseMapper.fromJobAndWorker(job, Optional.empty());

        assertNull(getJobCommandResponse.workerId());
    }
}
