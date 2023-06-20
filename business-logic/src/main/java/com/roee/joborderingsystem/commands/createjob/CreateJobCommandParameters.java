package com.roee.joborderingsystem.commands.createjob;

import java.time.LocalDateTime;

public record CreateJobCommandParameters(
    String category,
    String description,
    long customerId,
    LocalDateTime dueDate,
    String paymentMethod,
    double price
) {}
