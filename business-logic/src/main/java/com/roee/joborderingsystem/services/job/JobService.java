package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final CreateJobParametersMapper createJobParametersMapper;

    public Job findById(long id) {
        return jobRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Job not found"));
    }

    public List<Job> findPage(Long costumerId, Integer limit, Long jobIdCourser) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        if (costumerId != null) {
            return jobRepository.findPageByCustomerId(costumerId, jobIdCourser, pageRequest);
        } else {
            return jobRepository.findPage(jobIdCourser, pageRequest);
        }
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
