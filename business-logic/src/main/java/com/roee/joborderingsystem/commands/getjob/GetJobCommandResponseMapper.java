package com.roee.joborderingsystem.commands.getjob;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface GetJobCommandResponseMapper {

    @Mapping(target = "customerId", expression = "java(job.getCustomer().getId())")
    @Mapping(target = "workerId", expression = "java(worker.map(Worker::getId).orElse(null))")
    GetJobCommandResponse fromJobAndWorker(Job job, Optional<Worker> worker);
}
