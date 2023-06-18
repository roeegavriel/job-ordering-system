package com.roee.joborderingsystem.repositories;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import org.springframework.data.repository.CrudRepository;

public interface JobResponseRepository extends CrudRepository<JobResponse, Long> {

    boolean existsByJobAndAccepted(Job job, boolean accepted);
}
