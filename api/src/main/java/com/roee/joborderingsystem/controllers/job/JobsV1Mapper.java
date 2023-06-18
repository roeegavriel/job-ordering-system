package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.commands.getjob.GetJobCommandResponse;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import com.roee.joborderingsystem.generated.server.model.JobResponse;
import com.roee.joborderingsystem.generated.server.model.JobUpdateData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobsV1Mapper {

    CreateJobCommandParameters toCreateJobCommandParameters(JobCreateData jobCreateData);

    UpdateJobCommandParameters toUpdateJobCommandParameters(long jobId, JobUpdateData jobUpdateData);

    @Mapping(target = "acceptedWorkerId", source = "getJobCommandResponse.workerId")
    JobResponse fromGetJobCommandResponse(GetJobCommandResponse getJobCommandResponse);
}
