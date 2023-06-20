package com.roee.joborderingsystem.controllers.worker;

import com.roee.joborderingsystem.commands.responsetojob.ResponseToJobCommand;
import com.roee.joborderingsystem.generated.server.api.WorkersV1Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorkersV1Controller implements WorkersV1Api {

    private final ResponseToJobCommand responseToJobCommand;

    @Override
    public ResponseEntity<Void> accept(Long jobId, Long workerId) {
        responseToJobCommand.execute(workerId, jobId, true);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reject(Long jobId, Long workerId) {
        responseToJobCommand.execute(workerId, jobId, false);
        return ResponseEntity.ok().build();
    }
}
