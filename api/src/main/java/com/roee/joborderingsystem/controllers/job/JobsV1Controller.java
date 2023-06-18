package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommand;
import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.api.JobsV1Api;
import com.roee.joborderingsystem.generated.server.model.CreatedEntityId;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class JobsV1Controller implements JobsV1Api {

    private final CreateJobCommand createJobCommand;

    private final JobsV1Mapper jobsV1Mapper;

    @Override
    public ResponseEntity<CreatedEntityId> create(JobCreateData jobCreateData) {
        CreateJobCommandParameters createJobCommandParameters = jobsV1Mapper.toCreateJobCommandParameters(jobCreateData);
        long createdJobId = createJobCommand.execute(createJobCommandParameters);
        return ResponseEntity.ok(new CreatedEntityId().id(createdJobId));
    }
}
