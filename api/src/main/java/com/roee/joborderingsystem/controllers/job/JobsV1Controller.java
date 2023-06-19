package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommand;
import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.commands.deletejob.DeleteJobCommand;
import com.roee.joborderingsystem.commands.getjob.GetJobCommand;
import com.roee.joborderingsystem.commands.getjob.GetJobCommandResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandRequestFilters;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobsCommand;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommand;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.api.JobsV1Api;
import com.roee.joborderingsystem.generated.server.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class JobsV1Controller implements JobsV1Api {

    private final GetJobCommand getJobCommand;

    private final IndexJobsCommand indexJobsCommand;

    private final CreateJobCommand createJobCommand;

    private final UpdateJobCommand updateJobCommand;

    private final DeleteJobCommand deleteJobCommand;

    private final JobsV1Mapper jobsV1Mapper;

    @Override
    public ResponseEntity<JobResponse> get(Long jobId) {
        GetJobCommandResponse getJobCommandResponse = getJobCommand.execute(jobId);
        JobResponse jobResponse = jobsV1Mapper.fromGetJobCommandResponse(getJobCommandResponse);
        return ResponseEntity.ok(jobResponse);
    }

    @Override
    public ResponseEntity<JobsPaginationResponse> index(Long customerId, String pageInfo, Integer limit) {
        IndexJobCommandResponse indexJobCommandResponse = indexJobsCommand.execute(new IndexJobCommandRequestFilters(customerId, limit), pageInfo);
        return ResponseEntity.ok(new JobsPaginationResponse()
            .jobs(indexJobCommandResponse.jobs().stream().map(jobsV1Mapper::fromIndexJobCommandJobResponse).toList())
            .nextPageInfo(indexJobCommandResponse.pageInfo())
        );
    }

    @Override
    public ResponseEntity<CreatedEntityId> create(JobCreateData jobCreateData) {
        CreateJobCommandParameters createJobCommandParameters = jobsV1Mapper.toCreateJobCommandParameters(jobCreateData);
        long createdJobId = createJobCommand.execute(createJobCommandParameters);
        return ResponseEntity.ok(new CreatedEntityId().id(createdJobId));
    }

    @Override
    public ResponseEntity<Void> update(Long jobId, JobUpdateData jobUpdateData) {
        UpdateJobCommandParameters updateJobCommandParameters = jobsV1Mapper.toUpdateJobCommandParameters(jobId, jobUpdateData);
        updateJobCommand.execute(updateJobCommandParameters);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long jobId) {
        deleteJobCommand.execute(jobId);
        return ResponseEntity.ok().build();
    }
}
