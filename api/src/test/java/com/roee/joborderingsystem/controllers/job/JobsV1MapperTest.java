package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.commands.getjob.GetJobCommandResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandJobResponse;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import com.roee.joborderingsystem.generated.server.model.JobResponse;
import com.roee.joborderingsystem.generated.server.model.JobUpdateData;
import com.roee.joborderingsystem.generated.server.model.JobsResponseJobsInner;
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

    @Test
    @DisplayName("validate toUpdateJobCommandParameters map correctly")
    void validateToUpdateJobCommandParameters() {
        long jobId = 27092023L;
        JobUpdateData jobUpdateData = Instancio.create(JobUpdateData.class);

        UpdateJobCommandParameters updateJobCommandParameters = jobsV1Mapper.toUpdateJobCommandParameters(jobId, jobUpdateData);

        assertAll(
            () -> assertEquals(jobId, updateJobCommandParameters.jobId()),
            () -> assertEquals(jobUpdateData.getCustomerId(), updateJobCommandParameters.customerId()),
            () -> assertEquals(jobUpdateData.getCategory(), updateJobCommandParameters.category()),
            () -> assertEquals(jobUpdateData.getDescription(), updateJobCommandParameters.description()),
            () -> assertEquals(jobUpdateData.getDueDate(), updateJobCommandParameters.dueDate()),
            () -> assertEquals(jobUpdateData.getPaymentMethod(), updateJobCommandParameters.paymentMethod()),
            () -> assertEquals(jobUpdateData.getPrice(), updateJobCommandParameters.price())
        );
    }

    @Test
    @DisplayName("validate fromGetJobCommandResponse map correctly")
    void validateFromGetJobCommandResponse() {
        GetJobCommandResponse getJobCommandResponse = Instancio.create(GetJobCommandResponse.class);

        JobResponse jobResponse = jobsV1Mapper.fromGetJobCommandResponse(getJobCommandResponse);

        assertAll(
            () -> assertEquals(getJobCommandResponse.category(), jobResponse.getCategory()),
            () -> assertEquals(getJobCommandResponse.description(), jobResponse.getDescription()),
            () -> assertEquals(getJobCommandResponse.dueDate(), jobResponse.getDueDate()),
            () -> assertEquals(getJobCommandResponse.paymentMethod(), jobResponse.getPaymentMethod()),
            () -> assertEquals(getJobCommandResponse.price(), jobResponse.getPrice()),
            () -> assertEquals(getJobCommandResponse.customerId(), jobResponse.getCustomerId()),
            () -> assertEquals(getJobCommandResponse.workerId(), jobResponse.getAcceptedWorkerId()),
            () -> assertEquals(getJobCommandResponse.createdAt(), jobResponse.getCreatedAt()),
            () -> assertEquals(getJobCommandResponse.updatedAt(), jobResponse.getUpdatedAt())
        );
    }

    @Test
    @DisplayName("validate fromIndexJobCommandJobResponse map correctly")
    void validateFromIndexJobCommandJobResponse() {
        IndexJobCommandJobResponse indexJobCommandJobResponse = Instancio.create(IndexJobCommandJobResponse.class);

        JobsResponseJobsInner JobsResponseJobsInner = jobsV1Mapper.fromIndexJobCommandJobResponse(indexJobCommandJobResponse);

        assertAll(
            () -> assertEquals(indexJobCommandJobResponse.id(), JobsResponseJobsInner.getId()),
            () -> assertEquals(indexJobCommandJobResponse.category(), JobsResponseJobsInner.getCategory()),
            () -> assertEquals(indexJobCommandJobResponse.description(), JobsResponseJobsInner.getDescription()),
            () -> assertEquals(indexJobCommandJobResponse.dueDate(), JobsResponseJobsInner.getDueDate()),
            () -> assertEquals(indexJobCommandJobResponse.paymentMethod(), JobsResponseJobsInner.getPaymentMethod()),
            () -> assertEquals(indexJobCommandJobResponse.price(), JobsResponseJobsInner.getPrice()),
            () -> assertEquals(indexJobCommandJobResponse.customerId(), JobsResponseJobsInner.getCustomerId()),
            () -> assertEquals(indexJobCommandJobResponse.createdAt(), JobsResponseJobsInner.getCreatedAt()),
            () -> assertEquals(indexJobCommandJobResponse.updatedAt(), JobsResponseJobsInner.getUpdatedAt())
        );
    }
}
