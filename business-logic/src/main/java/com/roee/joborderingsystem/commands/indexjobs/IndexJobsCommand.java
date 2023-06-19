package com.roee.joborderingsystem.commands.indexjobs;

import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.services.job.JobService;
import com.roee.joborderingsystem.utils.Base64Serde;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexJobsCommand {

    private static final int DEFAULT_LIMIT = 10;

    private static final int MAX_LIMIT = 100;

    private final Base64Serde base64Serde;

    private final JobService jobService;

    private final IndexJobCommandJobResponseMapper indexJobCommandJobResponseMapper;

    @Transactional
    public IndexJobCommandResponse execute(IndexJobCommandRequestFilters indexJobCommandRequestFilters, String pageInfo) {
        indexJobCommandRequestFilters = extractRequestFilters(indexJobCommandRequestFilters, pageInfo);
        List<Job> jobs = jobService.findPage(
            indexJobCommandRequestFilters.costumerId(),
            indexJobCommandRequestFilters.limit(),
            indexJobCommandRequestFilters.jobIdCourser()
        );

        String nextPageInfo = buildNextPageInfo(jobs, indexJobCommandRequestFilters);
        List<IndexJobCommandJobResponse> indexJobCommandJobResponses = jobs.stream().map(indexJobCommandJobResponseMapper::fromJob).toList();

        return new IndexJobCommandResponse(indexJobCommandJobResponses, nextPageInfo);
    }

    private IndexJobCommandRequestFilters extractRequestFilters(IndexJobCommandRequestFilters indexJobCommandRequestFilters, String pageInfo) {
        if (StringUtils.hasText(pageInfo)) {
            if (indexJobCommandRequestFilters.costumerId() != null || indexJobCommandRequestFilters.limit() != null) {
                throw new IllegalArgumentException("Cannot use costumerId or limit with pageInfo");
            }
            indexJobCommandRequestFilters = base64Serde.deserialize(pageInfo, IndexJobCommandRequestFilters.class);
        }
        return new IndexJobCommandRequestFilters(
            indexJobCommandRequestFilters.costumerId(),
            getValidLimit(indexJobCommandRequestFilters.limit()),
            indexJobCommandRequestFilters.jobIdCourser()
        );
    }

    private int getValidLimit(Integer limit) {
        if (limit == null) {
            return DEFAULT_LIMIT;
        } else if (limit <= 0 || limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Limit must be positive and cannot be greater than " + MAX_LIMIT);
        }
        return limit;
    }

    private String buildNextPageInfo(List<Job> jobs, IndexJobCommandRequestFilters indexJobCommandRequestFilters) {
        if (jobs == null || jobs.size() != indexJobCommandRequestFilters.limit()) {
            return null;
        }

        return base64Serde.serialize(new IndexJobCommandRequestFilters (
            indexJobCommandRequestFilters.costumerId(),
            indexJobCommandRequestFilters.limit(),
            (jobs.get(jobs.size() - 1).getId())
        ));
    }
}
