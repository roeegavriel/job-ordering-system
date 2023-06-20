package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateJobParametersMapper {
    Job toJob(CreateJobParameters createJobParameters);
}
