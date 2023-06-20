package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommand;
import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.commands.deletejob.DeleteJobCommand;
import com.roee.joborderingsystem.commands.getjob.GetJobCommand;
import com.roee.joborderingsystem.commands.getjob.GetJobCommandResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandJobResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandRequestFilters;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobCommandResponse;
import com.roee.joborderingsystem.commands.indexjobs.IndexJobsCommand;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommand;
import com.roee.joborderingsystem.commands.updatejob.UpdateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobsV1ControllerTest {

    @Mock
    private GetJobCommand getJobCommand;

    @Mock
    private IndexJobsCommand indexJobsCommand;

    @Mock
    private CreateJobCommand createJobCommand;

    @Mock
    private UpdateJobCommand updateJobCommand;

    @Mock
    private DeleteJobCommand deleteJobCommand;

    @Mock
    private JobsV1Mapper jobsV1Mapper;

    @InjectMocks
    private JobsV1Controller jobsV1Controller;

    @Nested
    class GetTests {

        @Test
        @DisplayName("validate get invokes getJobCommand.execute")
        void validateGetJobCommandInvoked() {
            long jobId = Instancio.create(long.class);

            jobsV1Controller.get(jobId);

            verify(getJobCommand).execute(jobId);
        }

        @Test
        @DisplayName("validate get invokes jobsV1Mapper.fromGetJobCommandResponse")
        void validateFromGetJobCommandResponseInvoked() {
            GetJobCommandResponse getJobCommandResponse = Instancio.create(GetJobCommandResponse.class);
            when(getJobCommand.execute(anyLong())).thenReturn(getJobCommandResponse);

            jobsV1Controller.get(0L);

            verify(jobsV1Mapper).fromGetJobCommandResponse(getJobCommandResponse);
        }

        @Test
        @DisplayName("validate get return value")
        void validateReturnValue() {
            JobResponse jobResponse = Instancio.create(JobResponse.class);
            when(jobsV1Mapper.fromGetJobCommandResponse(any())).thenReturn(jobResponse);

            ResponseEntity<JobResponse> responseEntity = jobsV1Controller.get(0L);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(jobResponse, responseEntity.getBody());
        }
    }

    @Nested
    class IndexTests {

        @Test
        @DisplayName("validate index invokes indexJobsCommand.indexJobs")
        void validateIndexJobsCommandInvoked() {
            IndexJobCommandResponse indexJobCommandResponse = Instancio.create(IndexJobCommandResponse.class);
            when(indexJobsCommand.execute(any(), anyString())).thenReturn(indexJobCommandResponse);
            Long customerId = Instancio.create(Long.class);
            String pageInfo = Instancio.create(String.class);
            Integer limit = Instancio.create(Integer.class);

            jobsV1Controller.index(customerId, pageInfo, limit);

            ArgumentCaptor<IndexJobCommandRequestFilters> indexJobCommandRequestFiltersArgumentCaptor = ArgumentCaptor.forClass(IndexJobCommandRequestFilters.class);
            verify(indexJobsCommand).execute(indexJobCommandRequestFiltersArgumentCaptor.capture(), eq(pageInfo));
            IndexJobCommandRequestFilters indexJobCommandRequestFilters = indexJobCommandRequestFiltersArgumentCaptor.getValue();
            assertEquals(customerId, indexJobCommandRequestFilters.costumerId());
            assertEquals(limit, indexJobCommandRequestFilters.limit());
            assertEquals(0L, indexJobCommandRequestFilters.jobIdCourser());
        }

        @Test
        @DisplayName("validate index invokes jobsV1Mapper.fromIndexJobCommandJobResponse for each job")
        void validateFromIndexJobCommandJobResponse() {
            IndexJobCommandJobResponse jobResponse1 = Instancio.create(IndexJobCommandJobResponse.class);
            IndexJobCommandJobResponse jobResponse2 = Instancio.create(IndexJobCommandJobResponse.class);
            IndexJobCommandJobResponse jobResponse3 = Instancio.create(IndexJobCommandJobResponse.class);
            IndexJobCommandResponse indexJobCommandResponse = new IndexJobCommandResponse(List.of(jobResponse1, jobResponse2, jobResponse3), Instancio.create(String.class));
            when(indexJobsCommand.execute(any(), anyString())).thenReturn(indexJobCommandResponse);
            Long customerId = Instancio.create(Long.class);
            String pageInfo = Instancio.create(String.class);
            Integer limit = Instancio.create(Integer.class);

            ResponseEntity<JobsPaginationResponse> response = jobsV1Controller.index(customerId, pageInfo, limit);

            verify(jobsV1Mapper).fromIndexJobCommandJobResponse(jobResponse1);
            verify(jobsV1Mapper).fromIndexJobCommandJobResponse(jobResponse2);
            verify(jobsV1Mapper).fromIndexJobCommandJobResponse(jobResponse3);
        }

        @Test
        @DisplayName("validate index return value")
        void validateIndexReturnValue() {
            IndexJobCommandJobResponse jobResponse1 = Instancio.create(IndexJobCommandJobResponse.class);
            IndexJobCommandJobResponse jobResponse2 = Instancio.create(IndexJobCommandJobResponse.class);
            IndexJobCommandResponse indexJobCommandResponse = new IndexJobCommandResponse(List.of(jobResponse1, jobResponse2), Instancio.create(String.class));
            when(indexJobsCommand.execute(any(), anyString())).thenReturn(indexJobCommandResponse);
            JobsResponseJobsInner jobsResponseJobsInner1 = Instancio.create(JobsResponseJobsInner.class);
            JobsResponseJobsInner jobsResponseJobsInner2 = Instancio.create(JobsResponseJobsInner.class);
            when(jobsV1Mapper.fromIndexJobCommandJobResponse(any())).thenReturn(jobsResponseJobsInner1, jobsResponseJobsInner2);
            Long customerId = Instancio.create(Long.class);
            String pageInfo = Instancio.create(String.class);
            Integer limit = Instancio.create(Integer.class);

            ResponseEntity<JobsPaginationResponse> response = jobsV1Controller.index(customerId, pageInfo, limit);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(indexJobCommandResponse.pageInfo(), response.getBody().getNextPageInfo());
            assertEquals(List.of(jobsResponseJobsInner1, jobsResponseJobsInner2), response.getBody().getJobs());
        }
    }

    @Nested
    class CreateTests {

        @Test
        @DisplayName("validate create invokes jobsV1Mapper.toCreateJobCommandParameters")
        void validateToCreateJobCommandParametersInvoked() {
            JobCreateData jobCreateData = Instancio.create(JobCreateData.class);

            jobsV1Controller.create(jobCreateData);

            verify(jobsV1Mapper).toCreateJobCommandParameters(jobCreateData);
        }

        @Test
        @DisplayName("validate create invokes createJobCommand.execute")
        void validateCreateJobCommandInvoked() {
            CreateJobCommandParameters createJobCommandParameters = Instancio.create(CreateJobCommandParameters.class);
            when(jobsV1Mapper.toCreateJobCommandParameters(any())).thenReturn(createJobCommandParameters);

            jobsV1Controller.create(null);

            verify(createJobCommand).execute(createJobCommandParameters);
        }

        @Test
        @DisplayName("validate create returns created job id")
        void validateReturnValue() {
            Long createdJobId = 19081983L;
            when(createJobCommand.execute(any())).thenReturn(createdJobId);

            ResponseEntity<CreatedEntityId> createdEntityIdResponseEntity = jobsV1Controller.create(null);

            assertEquals(HttpStatus.OK, createdEntityIdResponseEntity.getStatusCode());
            assertEquals(createdJobId, createdEntityIdResponseEntity.getBody().getId());
        }
    }

    @Nested
    class UpdateTests {

        @Test
        @DisplayName("validate update invokes jobsV1Mapper.toUpdateJobCommandParameters")
        void validateToCreateJobCommandParametersInvoked() {
            Long jobId = 17022019L;
            JobUpdateData jobUpdateData = Instancio.create(JobUpdateData.class);

            jobsV1Controller.update(jobId, jobUpdateData);

            verify(jobsV1Mapper).toUpdateJobCommandParameters(jobId, jobUpdateData);
        }

        @Test
        @DisplayName("validate update invokes updateJobCommand.execute")
        void validateCreateJobCommandInvoked() {
            UpdateJobCommandParameters updateJobCommandParameters = Instancio.create(UpdateJobCommandParameters.class);
            when(jobsV1Mapper.toUpdateJobCommandParameters(anyLong(), any())).thenReturn(updateJobCommandParameters);

            jobsV1Controller.update(1L, null);

            verify(updateJobCommand).execute(updateJobCommandParameters);
        }

        @Test
        @DisplayName("validate update returns 200 ok")
        void validateReturnValue() {
            ResponseEntity<Void> responseEntity = jobsV1Controller.update(1L, null);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Nested
    class DeleteTests {

        @Test
        @DisplayName("validate delete invokes deleteJobCommand.execute")
        void validateDeleteJobCommandInvoked() {
            Long jobId = Instancio.create(Long.class);

            jobsV1Controller.delete(jobId);

            verify(deleteJobCommand).execute(jobId);
        }

        @Test
        @DisplayName("validate delete returns 200 ok")
        void validateReturnValue() {
            ResponseEntity<Void> responseEntity = jobsV1Controller.delete(1L);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }
}
