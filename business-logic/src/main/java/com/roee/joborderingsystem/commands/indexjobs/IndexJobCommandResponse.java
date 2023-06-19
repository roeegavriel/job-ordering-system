package com.roee.joborderingsystem.commands.indexjobs;

import java.util.List;

public record IndexJobCommandResponse(
    List<IndexJobCommandJobResponse> jobs,
    String pageInfo
) {}
