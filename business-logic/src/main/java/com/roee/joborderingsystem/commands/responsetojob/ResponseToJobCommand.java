package com.roee.joborderingsystem.commands.responsetojob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import com.roee.joborderingsystem.services.worker.WorkerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseToJobCommand {

    private final WorkerService workerService;

    private final JobService jobService;

    private final JobResponseService jobResponseService;

    @Transactional
    public void execute(long workerId, long jobId, boolean accepted) {
        Worker worker = workerService.findById(workerId);
        Job job = jobService.findById(jobId);
        if (jobResponseService.isJobRespondedByWorker(job, worker)) {
            throw new IllegalArgumentException("Job already responded by this worker");
        }
        if (accepted && jobResponseService.isJobAccepted(job)) {
            throw new IllegalArgumentException("Job already accepted by another worker, can't be accepted again");
        }
        jobResponseService.createJobResponse(worker, job, accepted);
    }
}
