package com.roee.joborderingsystem.services.worker;

import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    public Worker findById(long id) {
        return workerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Worker not found"));
    }
}
