package com.roee.joborderingsystem.services.jobresponse;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobResponseService {

    private final JobResponseRepository jobResponseRepository;

    public boolean isJobRespondedTo(Job job) {
        return jobResponseRepository.existsByJob(job);
    }

    public boolean isJobAccepted(Job job) {
        return jobResponseRepository.existsByJobAndAccepted(job, true);
    }
}
