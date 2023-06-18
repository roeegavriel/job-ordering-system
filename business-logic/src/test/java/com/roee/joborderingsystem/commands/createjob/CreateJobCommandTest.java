package com.roee.joborderingsystem.commands.createjob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.CreateJobParameters;
import com.roee.joborderingsystem.services.job.JobService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateJobCommandTest {

    @Mock
    private JobService jobService;

    @Mock
    private CreateJobCommandParametersMapper createJobCommandParametersMapper;

    @InjectMocks
    private CreateJobCommand createJobCommand;

    @BeforeEach
    void setUp() {
        when(jobService.createJob(any())).thenReturn(Instancio.create(Job.class));
    }

    @Test
    @DisplayName("validate execute invokes createJobCommandParametersMapper.toCreateJobParameters")
    void validateToCreateJobParameters() {
        CreateJobCommandParameters createJobCommandParameters = Instancio.create(CreateJobCommandParameters.class);

        createJobCommand.execute(createJobCommandParameters);

        verify(createJobCommandParametersMapper).toCreateJobParameters(createJobCommandParameters);
    }

    @Test
    @DisplayName("validate execute invokes jobService.createJob")
    void validateCreateJob() {
        CreateJobParameters createJobParameters = Instancio.create(CreateJobParameters.class);
        when(createJobCommandParametersMapper.toCreateJobParameters(any())).thenReturn(createJobParameters);

        createJobCommand.execute(null);

        verify(jobService).createJob(createJobParameters);
    }

    @Test
    @DisplayName("validate execute return the created job id")
    void validateReturnValue() {
        Job createdJob = Instancio.create(Job.class);
        when(jobService.createJob(any())).thenReturn(createdJob);

        long createdJobId = createJobCommand.execute(null);

        assertEquals(createdJob.getId(), createdJobId);
    }
}