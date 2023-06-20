package com.roee.joborderingsystem.commands.updatejob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.services.jobresponse.JobResponseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateJobCommand {

    private final JobService jobService;

    private final JobResponseService jobResponseService;

    private final UpdateJobCommandParametersMapper updateJobCommandParametersMapper;

    @Transactional
    public void execute(UpdateJobCommandParameters updateJobCommandParameters) {
        Job job = jobService.findById(updateJobCommandParameters.jobId());
        if (jobResponseService.isJobAccepted(job)) {
            throw new IllegalArgumentException("Job already accepted, can't be altered");
        }
        job = updateJobCommandParametersMapper.updateJob(job, updateJobCommandParameters);
        jobService.updateJob(job);
    }
}
