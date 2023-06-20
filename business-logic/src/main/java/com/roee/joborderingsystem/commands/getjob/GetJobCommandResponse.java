package com.roee.joborderingsystem.commands.getjob;

import java.time.LocalDateTime;

public record GetJobCommandResponse(
    String category,
    String description,
    long customerId,
    LocalDateTime dueDate,
    String paymentMethod,
    double price,
    Long workerId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
