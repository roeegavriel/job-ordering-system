package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import com.roee.joborderingsystem.generated.server.model.JobUpdateData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobsV1Mapper {

    CreateJobCommandParameters toCreateJobCommandParameters(JobCreateData jobCreateData);

    UpdateJobCommandParameters toUpdateJobCommandParameters(long jobId, JobUpdateData jobUpdateData);
}
