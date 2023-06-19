package com.roee.joborderingsystem.commands.indexjobs;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.utils.Base64Serde;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexJobsCommandTest {

    @Mock
    private Base64Serde base64Serde;

    @Mock
    private JobService jobService;

    @Mock
    private IndexJobCommandJobResponseMapper indexJobCommandJobResponseMapper;

    @InjectMocks
    private IndexJobsCommand indexJobsCommand;

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if pageInfo and customerId are present")
    void validateExecuteThrowsIllegalArgumentExceptionIfPageInfoAndCustomerIdArePresent() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(1L, null);
        String pageInfo = Instancio.create(String.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> indexJobsCommand.execute(indexJobCommandRequestFilters, pageInfo)
        );

        assertEquals("Cannot use costumerId or limit with pageInfo", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if pageInfo and limit are present")
    void validateExecuteThrowsIllegalArgumentExceptionIfPageInfoAndLimitIdArePresent() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 1);
        String pageInfo = Instancio.create(String.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> indexJobsCommand.execute(indexJobCommandRequestFilters, pageInfo)
        );

        assertEquals("Cannot use costumerId or limit with pageInfo", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if limit is 0")
    void validateExecuteThrowsIllegalArgumentExceptionIfLimitIsZero() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> indexJobsCommand.execute(indexJobCommandRequestFilters, null)
        );

        assertEquals("Limit must be positive and cannot be greater than 100", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if limit is negative")
    void validateExecuteThrowsIllegalArgumentExceptionIfLimitIsNegative() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, -1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> indexJobsCommand.execute(indexJobCommandRequestFilters, null)
        );

        assertEquals("Limit must be positive and cannot be greater than 100", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute throws IllegalArgumentException if limit is negative")
    void validateExecuteThrowsIllegalArgumentExceptionIfLimitIsGreaterThan100() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 101);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> indexJobsCommand.execute(indexJobCommandRequestFilters, null)
        );

        assertEquals("Limit must be positive and cannot be greater than 100", exception.getMessage());
    }

    @Test
    @DisplayName("validate execute invokes base64Serde.deserialize if pageInfo is present")
    void validateExecuteInvokesBase64SerdeDeserializeIfPageInfoIsPresent() {
        String pageInfo = Instancio.create(String.class);
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, null);
        when(base64Serde.deserialize(pageInfo, IndexJobCommandRequestFilters.class)).thenReturn(indexJobCommandRequestFilters);

        indexJobsCommand.execute(indexJobCommandRequestFilters, pageInfo);

        verify(base64Serde).deserialize(pageInfo, IndexJobCommandRequestFilters.class);
    }

    @Test
    @DisplayName("validate execute invokes jobService.findPage with values from pageInfo")
    void validateExecuteInvokesJobServiceFindPageWithValuesFromPageInfo() {
        String pageInfo = Instancio.create(String.class);
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, null);
        Long costumerId = Instancio.create(Long.class);
        Integer limit = 5;
        Long jobIdCourser = Instancio.create(Long.class);
        when(base64Serde.deserialize(pageInfo, IndexJobCommandRequestFilters.class)).thenReturn(new IndexJobCommandRequestFilters(costumerId, limit, jobIdCourser));

        indexJobsCommand.execute(indexJobCommandRequestFilters, pageInfo);

        verify(jobService).findPage(costumerId, limit, jobIdCourser);
    }

    @Test
    @DisplayName("validate execute invokes jobService.findPage with values from IndexJobCommandRequestFilters")
    void validateExecuteInvokesJobServiceFindPageWithValuesFromIndexJobCommandRequestFilters() {
        String pageInfo = Instancio.create(String.class);
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, null);
        Long costumerId = Instancio.create(Long.class);
        Integer limit = 50;
        Long jobIdCourser = Instancio.create(Long.class);
        when(base64Serde.deserialize(pageInfo, IndexJobCommandRequestFilters.class)).thenReturn(new IndexJobCommandRequestFilters(costumerId, limit, jobIdCourser));

        indexJobsCommand.execute(indexJobCommandRequestFilters, pageInfo);

        verify(jobService).findPage(costumerId, limit, jobIdCourser);
    }

    @Test
    @DisplayName("validate execute doesn't invokes base64Serde.serialize if found jobs size is lower then limit")
    void validateExecuteDoesNotInvokesBase64SerdeSerialize() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 3);
        when(jobService.findPage(any(), any(), any())).thenReturn(List.of(Instancio.create(Job.class), Instancio.create(Job.class)));

        indexJobsCommand.execute(indexJobCommandRequestFilters, null);

        verify(base64Serde, never()).serialize(any());
    }

    @Test
    @DisplayName("validate execute invokes base64Serde.serialize if found jobs size equals limit")
    void validateExecuteDoesInvokesBase64SerdeSerialize() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 3);
        Job job1 = Instancio.create(Job.class);
        Job job2 = Instancio.create(Job.class);
        Job job3 = Instancio.create(Job.class);
        when(jobService.findPage(any(), any(), any())).thenReturn(List.of(job1, job2, job3));

        indexJobsCommand.execute(indexJobCommandRequestFilters, null);

        ArgumentCaptor<IndexJobCommandRequestFilters> indexJobCommandRequestFiltersArgumentCaptor = ArgumentCaptor.forClass(IndexJobCommandRequestFilters.class);
        verify(base64Serde).serialize(indexJobCommandRequestFiltersArgumentCaptor.capture());
        IndexJobCommandRequestFilters serializeIndexJobCommandRequestFilters = indexJobCommandRequestFiltersArgumentCaptor.getValue();
        assertAll(
            () -> assertEquals(indexJobCommandRequestFilters.costumerId(), serializeIndexJobCommandRequestFilters.costumerId()),
            () -> assertEquals(indexJobCommandRequestFilters.limit(), serializeIndexJobCommandRequestFilters.limit()),
            () -> assertEquals(job3.getId(), serializeIndexJobCommandRequestFilters.jobIdCourser())
        );
    }

    @Test
    @DisplayName("validate execute invokes indexJobCommandJobResponseMapper.fromJob for each job")
    void validateExecuteInvokesFromJobForEachJob() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 3);
        Job job1 = Instancio.create(Job.class);
        Job job2 = Instancio.create(Job.class);
        Job job3 = Instancio.create(Job.class);
        when(jobService.findPage(any(), any(), any())).thenReturn(List.of(job1, job2, job3));

        indexJobsCommand.execute(indexJobCommandRequestFilters, null);

        verify(indexJobCommandJobResponseMapper).fromJob(job1);
        verify(indexJobCommandJobResponseMapper).fromJob(job2);
        verify(indexJobCommandJobResponseMapper).fromJob(job3);
    }

    @Test
    @DisplayName("validate execute return value")
    void validateExecuteReturnValue() {
        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(null, 3);
        Job job1 = Instancio.create(Job.class);
        Job job2 = Instancio.create(Job.class);
        Job job3 = Instancio.create(Job.class);
        when(jobService.findPage(any(), any(), any())).thenReturn(List.of(job1, job2, job3));
        String nextPageInfo = Instancio.create(String.class);
        when(base64Serde.serialize(any())).thenReturn(nextPageInfo);
        IndexJobCommandJobResponse indexJobCommandJobResponse1 = Instancio.create(IndexJobCommandJobResponse.class);
        IndexJobCommandJobResponse indexJobCommandJobResponse2 = Instancio.create(IndexJobCommandJobResponse.class);
        IndexJobCommandJobResponse indexJobCommandJobResponse3 = Instancio.create(IndexJobCommandJobResponse.class);
        when(indexJobCommandJobResponseMapper.fromJob(any())).thenReturn(indexJobCommandJobResponse1, indexJobCommandJobResponse2, indexJobCommandJobResponse3);

        IndexJobCommandResponse response = indexJobsCommand.execute(indexJobCommandRequestFilters, null);

        assertAll(
            () -> assertEquals(List.of(indexJobCommandJobResponse1, indexJobCommandJobResponse2, indexJobCommandJobResponse3), response.jobs()),
            () -> assertEquals(nextPageInfo, response.pageInfo())
        );
    }
}
