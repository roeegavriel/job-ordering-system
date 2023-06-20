package com.roee.joborderingsystem.commands.responsetojob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.mail.SendJobResponseMail;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import com.roee.joborderingsystem.services.worker.WorkerService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseToJobCommandTest {

    @Mock
    private WorkerService workerService;

    @Mock
    private JobService jobService;

    @Mock
    private JobResponseService jobResponseService;

    @Mock
    private SendJobResponseMail sendJobResponseMail;

    @InjectMocks
    private ResponseToJobCommand responseToJobCommand;

    @Test
    @DisplayName("validate execute invokes workerService.findById")
    void validateWorkerFindById() {
        long workerId = Instancio.create(long.class);

        responseToJobCommand.execute(workerId, 0, false);

        verify(workerService).findById(workerId);
    }

    @Test
    @DisplayName("validate execute invokes jobService.findById")
    void validateJobFindById() {
        long jobId = Instancio.create(long.class);

        responseToJobCommand.execute(0, jobId, false);

        verify(jobService).findById(jobId);
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.isJobRespondedByWorker")
    void validateIsJobRespondedByWorker() {
        Worker worker = Instancio.create(Worker.class);
        when(workerService.findById(anyLong())).thenReturn(worker);
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);

        responseToJobCommand.execute(1L, 2L, false);

        verify(jobResponseService).isJobRespondedByWorker(job, worker);
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if isJobRespondedByWorker is true")
    void validateIsJobRespondedByWorkerThrowsIfJobRespondedByWorker() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobRespondedByWorker(any(), any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> responseToJobCommand.execute(1L, 2L, false));

        assertEquals("Job already responded by this worker", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.isJobAccepted")
    void validateIsJobAccepted() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobRespondedByWorker(any(), any())).thenReturn(false);

        responseToJobCommand.execute(1L, 2L, true);

        verify(jobResponseService).isJobAccepted(job);
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if isJobAccepted is true")
    void validateIsJobRespondedByWorkerThrowsIfJobAccepted() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.isJobRespondedByWorker(any(), any())).thenReturn(false);
        when(jobResponseService.isJobAccepted(any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> responseToJobCommand.execute(1L, 2L, true));

        assertEquals("Job already accepted by another worker, can't be accepted again", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.createJobResponse")
    void validateCreateJobResponse() {
        Worker worker = Instancio.create(Worker.class);
        when(workerService.findById(anyLong())).thenReturn(worker);
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        Boolean accepted = Instancio.create(Boolean.class);
        when(jobResponseService.isJobRespondedByWorker(any(), any())).thenReturn(false);
        if (accepted) {
            when(jobResponseService.isJobAccepted(any())).thenReturn(false);
        }

        responseToJobCommand.execute(1L, 2L, accepted);

        verify(jobResponseService).createJobResponse(worker, job, accepted);
    }

    @Test
    @DisplayName("validate execute invokes sendJobResponseMail.sendJobAcceptedMail")
    void validateSendJobAcceptedMail() {
        Boolean accepted = Instancio.create(Boolean.class);
        when(jobResponseService.isJobRespondedByWorker(any(), any())).thenReturn(false);
        if (accepted) {
            when(jobResponseService.isJobAccepted(any())).thenReturn(false);
        }
        JobResponse jobResponse = Instancio.create(JobResponse.class);
        when(jobResponseService.createJobResponse(any(), any(), anyBoolean())).thenReturn(jobResponse);

        responseToJobCommand.execute(1L, 2L, accepted);

        verify(sendJobResponseMail).sendJobAcceptedMail(jobResponse);
    }
}
