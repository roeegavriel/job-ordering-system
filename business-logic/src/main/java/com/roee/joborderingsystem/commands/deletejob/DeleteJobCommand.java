package com.roee.joborderingsystem.commands.deletejob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteJobCommand {

    private final JobService jobService;

    private final JobResponseService jobResponseService;

    @Transactional
    public void execute(long jobId) {
        Job job = jobService.findById(jobId);
        if (jobResponseService.isJobRespondedTo(job)) {
            throw new IllegalArgumentException("Job already responded, can't be delete");
        }
        jobService.deleteJob(job);
    }
}
