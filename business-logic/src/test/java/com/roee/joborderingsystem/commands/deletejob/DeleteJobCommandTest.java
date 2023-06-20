package com.roee.joborderingsystem.commands.deletejob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import org.instancio.Instancio;
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
class DeleteJobCommandTest {

    @Mock
    private JobService jobService;

    @Mock
    private JobResponseService jobResponseService;

    @InjectMocks
    private DeleteJobCommand deleteJobCommand;

    @Test
    @DisplayName("validate execute invokes jobService.findById")
    void validateFindById() {
        Long jobId = Instancio.create(Long.class);

        deleteJobCommand.execute(jobId);

        verify(jobService).findById(jobId);
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.isJobRespondedTo")
    void validateIsJobRespondedTo() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);

        deleteJobCommand.execute(1L);

        verify(jobResponseService).isJobRespondedTo(job);
    }

    @Test
    @DisplayName("validate IllegalArgumentException is thrown if job is already responded")
    void validateJobAlreadyAccepted() {
        when(jobResponseService.isJobRespondedTo(any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deleteJobCommand.execute(1L));

        assertEquals("Job already responded, can't be delete", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute invokes jobService.deleteJob")
    void validateDeleteJob() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobRespondedTo(any())).thenReturn(false);

        deleteJobCommand.execute(1L);

        verify(jobService).deleteJob(job);
    }
}
