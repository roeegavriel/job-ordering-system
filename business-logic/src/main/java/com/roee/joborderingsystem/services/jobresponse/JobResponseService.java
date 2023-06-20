package com.roee.joborderingsystem.services.jobresponse;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.repositories.JobResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobResponseService {

    private final JobResponseRepository jobResponseRepository;

    public JobResponse createJobResponse(Worker worker, Job job, boolean accepted) {
        return jobResponseRepository.save(JobResponse.builder().worker(worker).job(job).accepted(accepted).build());
    }

    public boolean isJobRespondedTo(Job job) {
        return jobResponseRepository.existsByJob(job);
    }

    public boolean isJobRespondedByWorker(Job job, Worker worker) {
        return jobResponseRepository.existsByJobAndWorker(job, worker);
    }

    public boolean isJobAccepted(Job job) {
        return jobResponseRepository.existsByJobAndAccepted(job, true);
    }

    public Optional<JobResponse> getJobAcceptedResponse(Job job) {
        return jobResponseRepository.getByJobAndAccepted(job, true);
    }
}
