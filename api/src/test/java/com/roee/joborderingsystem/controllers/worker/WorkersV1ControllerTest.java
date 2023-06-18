package com.roee.joborderingsystem.controllers.worker;

import com.roee.joborderingsystem.commands.responsetojob.ResponseToJobCommand;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WorkersV1ControllerTest {

    @Mock
    private ResponseToJobCommand responseToJobCommand;

    @InjectMocks
    private WorkersV1Controller workersV1Controller;

    @Nested
    class AcceptTests {

        @Test
        @DisplayName("validate accept invokes responseToJobCommand.execute")
        void validateAcceptInvokesResponseToJobCommandExecute() {
            Long jobId = Instancio.create(Long.class);
            Long workerId = Instancio.create(Long.class);

            workersV1Controller.accept(jobId, workerId);

            verify(responseToJobCommand).execute(workerId, jobId, true);
        }

        @Test
        @DisplayName("validate accept returns 200 ok")
        void validateReturnValue() {
            ResponseEntity<Void> responseEntity = workersV1Controller.accept(1L, 2L);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Nested
    class RejectTests {

        @Test
        @DisplayName("validate reject invokes responseToJobCommand.execute")
        void validateRejectInvokesResponseToJobCommandExecute() {
            Long jobId = Instancio.create(Long.class);
            Long workerId = Instancio.create(Long.class);

            workersV1Controller.reject(jobId, workerId);

            verify(responseToJobCommand).execute(workerId, jobId, false);
        }

        @Test
        @DisplayName("validate reject returns 200 ok")
        void validateReturnValue() {
            ResponseEntity<Void> responseEntity = workersV1Controller.reject(1L, 2L);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }
}