package com.roee.joborderingsystem.commands.updatejob;

import java.time.LocalDateTime;

public record UpdateJobCommandParameters(
    long jobId,
    String category,
    String description,
    long customerId,
    LocalDateTime dueDate,
    String paymentMethod,
    double price
) {}
