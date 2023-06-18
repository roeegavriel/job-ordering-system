package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final CreateJobParametersMapper createJobParametersMapper;

    public Job findById(long id) {
        return jobRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Job not found"));
    }

    public Job createJob(CreateJobParameters createJobParameters) {
        Job job = createJobParametersMapper.toJob(createJobParameters);
        return jobRepository.save(job);
    }

    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }

    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }
}
