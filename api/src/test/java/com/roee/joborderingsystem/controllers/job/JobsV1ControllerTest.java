package com.roee.joborderingsystem.controllers.job;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommand;
import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.generated.server.model.CreatedEntityId;
import com.roee.joborderingsystem.generated.server.model.JobCreateData;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobsV1ControllerTest {

    @Mock
    private CreateJobCommand createJobCommand;

    @Mock
    private JobsV1Mapper jobsV1Mapper;

    @InjectMocks
    private JobsV1Controller jobsV1Controller;

    @Nested
    class CreateTests {

        @Test
        @DisplayName("validate create invokes jobsV1Mapper.toCreateJobCommandParameters")
        void validateToCreateJobCommandParametersInvoked() {
            JobCreateData jobCreateData = new JobCreateData().description("magical-description");

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

            assertEquals(createdJobId, createdEntityIdResponseEntity.getBody().getId());
        }
    }
}
