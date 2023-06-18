package com.roee.joborderingsystem.services.job;

import com.roee.joborderingsystem.entities.Customer;

import java.time.LocalDateTime;

public record CreateJobParameters(
    String category,
    String description,
    Customer customer,
    LocalDateTime dueDate,
    String paymentMethod,
    double price
) {}
