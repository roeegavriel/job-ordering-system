package com.roee.joborderingsystem.commands.indexjobs;

import com.roee.joborderingsystem.entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IndexJobCommandJobResponseMapper {

    @Mapping(target = "customerId", expression = "java(job.getCustomer().getId())")
    IndexJobCommandJobResponse fromJob(Job job);
}
