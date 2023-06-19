package com.roee.joborderingsystem.services.jobresponse;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.repositories.JobResponseRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobResponseServiceTest {

    @Mock
    private JobResponseRepository jobResponseRepository;

    @InjectMocks
    private JobResponseService jobResponseService;

    @Nested
    class CreateJobResponseTest {

        @Test
        @DisplayName("validate createJobResponse invokes jobResponseRepository.save")
        void validateCreateJobResponseInvokesJobResponseRepositorySave() {
            Worker worker = Instancio.create(Worker.class);
            Job job = Instancio.create(Job.class);
            boolean accepted = Instancio.create(boolean.class);

            jobResponseService.createJobResponse(worker, job, accepted);

            ArgumentCaptor<JobResponse> jobResponseArgumentCaptor = ArgumentCaptor.forClass(JobResponse.class);
            verify(jobResponseRepository).save(jobResponseArgumentCaptor.capture());
            JobResponse jobResponse = jobResponseArgumentCaptor.getValue();
            assertAll(
                () -> assertEquals(worker, jobResponse.getWorker()),
                () -> assertEquals(job, jobResponse.getJob()),
                () -> assertEquals(accepted, jobResponse.isAccepted())
            );
        }

        @Test
        @DisplayName("validate createJobResponse response value")
        void validateCreateJobResponseReturnValue() {
            JobResponse jobResponse = Instancio.create(JobResponse.class);
            when(jobResponseRepository.save(any())).thenReturn(jobResponse);

            JobResponse createJobResponse = jobResponseService.createJobResponse(null, null, false);

            assertEquals(jobResponse, createJobResponse);
        }
    }

    @Nested
    class IsJobRespondedToTest {

        @Test
        @DisplayName("validate isJobRespondedTo invokes jobResponseRepository.existsByJob")
        void validateIsJobAcceptedInvokesJobResponseRepositoryExistsByJob() {
            Job job = Instancio.create(Job.class);

            jobResponseService.isJobRespondedTo(job);

            verify(jobResponseRepository).existsByJob(job);
        }

        @Test
        @DisplayName("validate isJobRespondedTo returns existsByJob response")
        void validateIsJobRespondedToReturnValue() {
            Boolean isRespondedTo = Instancio.create(Boolean.class);
            when(jobResponseRepository.existsByJob(any())).thenReturn(isRespondedTo);

            boolean jobRespondedTo = jobResponseService.isJobRespondedTo(null);

            assertEquals(isRespondedTo, jobRespondedTo);
        }
    }

    @Nested
    class IsJobRespondedByWorkerTests {

        @Test
        @DisplayName("validate isJobRespondedByWorker invokes jobResponseRepository.existsByJobAndWorker")
        void validateIsJobRespondedByWorkerInvokesJobResponseRepositoryExistsByJobAndWorker() {
            Job job = Instancio.create(Job.class);
            Worker worker = Instancio.create(Worker.class);

            jobResponseService.isJobRespondedByWorker(job, worker);

            verify(jobResponseRepository).existsByJobAndWorker(job, worker);
        }

        @Test
        @DisplayName("validate isJobRespondedByWorker returns existsByJobAndWorker response")
        void validateIsJobRespondedByWorkerReturnValue() {
            Boolean isRespondedByWorker = Instancio.create(Boolean.class);
            when(jobResponseRepository.existsByJobAndWorker(any(), any())).thenReturn(isRespondedByWorker);

            boolean jobRespondedByWorker = jobResponseService.isJobRespondedByWorker(null, null);

            assertEquals(isRespondedByWorker, jobRespondedByWorker);
        }
    }

    @Nested
    class IsJobAcceptedTests {

        @Test
        @DisplayName("validate isJobAccepted invokes jobResponseRepository.existsByJobAndAccepted")
        void validateIsJobAcceptedInvokesJobResponseRepositoryExistsByJobAndAccepted() {
            Job job = Instancio.create(Job.class);

            jobResponseService.isJobAccepted(job);

            verify(jobResponseRepository).existsByJobAndAccepted(job, true);
        }

        @Test
        @DisplayName("validate isJobAccepted returns existsByJobAndAccepted value")
        void validateIsJobAcceptedReturnValue() {
            Boolean isAccepted = Instancio.create(Boolean.class);
            when(jobResponseRepository.existsByJobAndAccepted(any(), eq(true))).thenReturn(isAccepted);

            boolean jobAccepted = jobResponseService.isJobAccepted(null);

            assertEquals(isAccepted, jobAccepted);
        }
    }

    @Nested
    class GetJobAcceptedResponseTests {

        @Test
        @DisplayName("validate getJobAcceptedResponse invokes jobResponseRepository.getByJobAndAccepted")
        void validateGetJobAcceptedResponseInvokesJobResponseRepositoryFindByJobAndAccepted() {
            Job job = Instancio.create(Job.class);

            jobResponseService.getJobAcceptedResponse(job);

            verify(jobResponseRepository).getByJobAndAccepted(job, true);
        }

        @Test
        @DisplayName("validate getJobAcceptedResponse return value")
        void validateGetJobAcceptedResponseReturnValue() {
            JobResponse jobResponse = Instancio.create(JobResponse.class);
            when(jobResponseRepository.getByJobAndAccepted(any(), anyBoolean())).thenReturn(Optional.of(jobResponse));

            Optional<JobResponse> jobAcceptedResponse = jobResponseService.getJobAcceptedResponse(null);

            assertTrue(jobAcceptedResponse.isPresent());
            assertEquals(jobResponse, jobAcceptedResponse.get());
        }
    }
}
