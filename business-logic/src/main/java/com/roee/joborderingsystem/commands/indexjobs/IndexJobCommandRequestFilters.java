package com.roee.joborderingsystem.commands.indexjobs;

public record IndexJobCommandRequestFilters (
    Long costumerId,
    Integer limit,
    Long jobIdCourser
) {

    public IndexJobCommandRequestFilters(Long costumerId, Integer limit) {
        this(costumerId, limit, 0L);
    }
}
