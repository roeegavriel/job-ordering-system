package com.roee.joborderingsystem.repositories;

import com.roee.joborderingsystem.entities.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {

}
