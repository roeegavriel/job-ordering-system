package com.roee.joborderingsystem.services.jobresponse;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobResponseRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobResponseServiceTest {

    @Mock
    private JobResponseRepository jobResponseRepository;

    @InjectMocks
    private JobResponseService jobResponseService;

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
}
