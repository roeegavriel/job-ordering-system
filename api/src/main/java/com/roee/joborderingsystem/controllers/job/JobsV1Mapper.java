package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobsV1Mapper {

    CreateJobCommandParameters toCreateJobCommandParameters(JobCreateData jobCreateData);
}
