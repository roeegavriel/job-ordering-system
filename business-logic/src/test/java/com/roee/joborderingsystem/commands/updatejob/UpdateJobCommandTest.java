package com.roee.joborderingsystem.commands.updatejob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateJobCommandTest {

    @Mock
    private JobService jobService;

    @Mock
    private JobResponseService jobResponseService;

    @Mock
    private UpdateJobCommandParametersMapper updateJobCommandParametersMapper;

    @InjectMocks
    private UpdateJobCommand updateJobCommand;

    private UpdateJobCommandParameters updateJobCommandParameters;

    @BeforeEach
    void setUp() {
        updateJobCommandParameters = Instancio.create(UpdateJobCommandParameters.class);
    }

    @Test
    @DisplayName("validate execute invokes jobService.findById")
    void validateFindById() {
        updateJobCommand.execute(updateJobCommandParameters);

        verify(jobService).findById(updateJobCommandParameters.jobId());
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.isJobAccepted")
    void validateIsJobAccepted() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);

        updateJobCommand.execute(updateJobCommandParameters);

        verify(jobResponseService).isJobAccepted(job);
    }

    @Test
    @DisplayName("validate IllegalArgumentException is thrown if job is already accepted")
    void validateJobAlreadyAccepted() {
        when(jobResponseService.isJobAccepted(any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateJobCommand.execute(updateJobCommandParameters);
        });

        assertEquals("Job already accepted, can't be altered", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute invokes updateJobCommandParametersMapper.updateJob")
    void validateUpdateJob() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobAccepted(any())).thenReturn(false);

        updateJobCommand.execute(updateJobCommandParameters);

        verify(updateJobCommandParametersMapper).updateJob(job, updateJobCommandParameters);
    }

    @Test
    @DisplayName("validate execute invokes jobService.updateJob(job)")
    void validateUpdateJobService() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobAccepted(any())).thenReturn(false);
        Job updatedJob = Instancio.create(Job.class);
        when(updateJobCommandParametersMapper.updateJob(any(), any())).thenReturn(updatedJob);

        updateJobCommand.execute(updateJobCommandParameters);

        verify(jobService).updateJob(updatedJob);
    }
}
