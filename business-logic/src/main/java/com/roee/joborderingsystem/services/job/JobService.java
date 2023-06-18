package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final CreateJobParametersMapper createJobParametersMapper;

    public Job createJob(CreateJobParameters createJobParameters) {
        Job job = createJobParametersMapper.toJob(createJobParameters);
        return jobRepository.save(job);
    }
}
