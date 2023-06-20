package com.roee.joborderingsystem.commands.indexjobs;

import java.time.LocalDateTime;

public record IndexJobCommandJobResponse(
    long id,
    String category,
    String description,
    long customerId,
    LocalDateTime dueDate,
    String paymentMethod,
    double price,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
