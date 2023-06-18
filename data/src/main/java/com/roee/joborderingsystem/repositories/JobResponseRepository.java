package com.roee.joborderingsystem.repositories;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import org.springframework.data.repository.CrudRepository;

public interface JobResponseRepository extends CrudRepository<JobResponse, Long> {

    boolean existsByJob(Job job);

    boolean existsByJobAndWorker(Job job, Worker worker);

    boolean existsByJobAndAccepted(Job job, boolean accepted);
}
