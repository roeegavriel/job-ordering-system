package com.roee.joborderingsystem.commands.getjob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetJobCommandTest {

    @Mock
    private JobService jobService;

    @Mock
    private JobResponseService jobResponseService;

    @Mock
    private GetJobCommandResponseMapper getJobCommandResponseMapper;

    @InjectMocks
    private GetJobCommand getJobCommand;

    @Test
    @DisplayName("validate execute invokes jobService.findById")
    void validateFindById() {
        long jobId = Instancio.create(long.class);

        getJobCommand.execute(jobId);

        verify(jobService).findById(jobId);
    }

    @Test
    @DisplayName("validate execute invokes jobResponseService.getJobAcceptedResponse")
    void validateGetJobAcceptedResponse() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);

        getJobCommand.execute(1L);

        verify(jobResponseService).getJobAcceptedResponse(job);
    }

    @Test
    @DisplayName("validate execute invokes getJobCommandResponseMapper.fromJobAndWorker with worker if accepted")
    void validateFromJobAndWorkerWithWorker() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        JobResponse jobResponse = Instancio.create(JobResponse.class);
        Worker worker = Instancio.create(Worker.class);
        jobResponse.setWorker(worker);
        when(jobResponseService.getJobAcceptedResponse(any())).thenReturn(Optional.of(jobResponse));

        getJobCommand.execute(1L);

        verify(getJobCommandResponseMapper).fromJobAndWorker(job, Optional.of(worker));
    }

    @Test
    @DisplayName("validate execute invokes getJobCommandResponseMapper.fromJobAndWorker without worker if not accepted")
    void validateFromJobAndWorkerWithoutWorker() {
        Job job = Instancio.create(Job.class);
        when(jobService.findById(anyLong())).thenReturn(job);
        when(jobResponseService.getJobAcceptedResponse(any())).thenReturn(Optional.empty());

        getJobCommand.execute(1L);

        verify(getJobCommandResponseMapper).fromJobAndWorker(job, Optional.empty());
    }

    @Test
    @DisplayName("validate execute return value")
    void validateReturnValue() {
        GetJobCommandResponse getJobCommandResponse = Instancio.create(GetJobCommandResponse.class);
        when(getJobCommandResponseMapper.fromJobAndWorker(any(), any())).thenReturn(getJobCommandResponse);

        GetJobCommandResponse response = getJobCommand.execute(1L);

        assertEquals(getJobCommandResponse, response);
    }
}
