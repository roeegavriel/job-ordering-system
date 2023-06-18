package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.repositories.JobRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CreateJobParametersMapper createJobParametersMapper;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
    }

    @Nested
    class CreateJobTests {

        @Test
        @DisplayName("validate findById invokes createJobParametersMapper.toJob")
        void validateFindByIdInvokesCreateJobParametersMapperToJob() {
            CreateJobParameters createJobParameters = Instancio.create(CreateJobParameters.class);

            jobService.createJob(createJobParameters);

            verify(createJobParametersMapper).toJob(createJobParameters);
        }

        @Test
        @DisplayName("validate findById invokes jobRepository.save")
        void validateFindByIdInvokesJobRepositorySave() {
            Job job = Instancio.create(Job.class);
            when(createJobParametersMapper.toJob(any())).thenReturn(job);

            jobService.createJob(null);

            verify(jobRepository).save(job);
        }

        @Test
        @DisplayName("validate findById returns the created job")
        void validateFindByIdReturnsTheCreatedJob() {
            Job job = Instancio.create(Job.class);
            when(jobRepository.save(any())).thenReturn(job);

            Job returnedJob = jobService.createJob(null);

            assertEquals(job, returnedJob);
        }
    }
}
