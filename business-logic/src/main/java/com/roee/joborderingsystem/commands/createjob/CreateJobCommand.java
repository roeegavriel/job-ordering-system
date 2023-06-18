package com.roee.joborderingsystem.commands.createjob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.CreateJobParameters;
import com.roee.joborderingsystem.services.job.JobService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateJobCommand {

    private final JobService jobService;

    private final CreateJobCommandParametersMapper createJobCommandParametersMapper;

    @Transactional
    public long execute(CreateJobCommandParameters createJobCommandParameters) {
        CreateJobParameters createJobParameters = createJobCommandParametersMapper.toCreateJobParameters(createJobCommandParameters);
        Job createdJob = jobService.createJob(createJobParameters);
        return createdJob.getId();
    }
}
