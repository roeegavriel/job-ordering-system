package com.roee.joborderingsystem.commands.getjob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetJobCommand {

    private final JobService jobService;

    private final JobResponseService jobResponseService;

    private final GetJobCommandResponseMapper getJobCommandResponseMapper;

    @Transactional
    public GetJobCommandResponse execute(long jobId) {
        Job job = jobService.findById(jobId);
        Optional<Worker> worker = jobResponseService.getJobAcceptedResponse(job).map(JobResponse::getWorker);
        GetJobCommandResponse getJobCommandResponse = getJobCommandResponseMapper.fromJobAndWorker(job, worker);
        return getJobCommandResponse;
    }
}
