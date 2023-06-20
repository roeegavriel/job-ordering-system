package com.roee.joborderingsystem.services.worker;

import com.roee.joborderingsystem.entities.Worker;
import com.roee.joborderingsystem.repositories.WorkerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerService workerService;

    @Nested
    class FindByIdTests {

        @Test
        @DisplayName("validate findById invokes workerRepository.findById")
        void validateFindByIdInvokesWorkerRepositoryFindById() {
            long id = Instancio.create(long.class);
            when(workerRepository.findById(anyLong())).thenReturn(Optional.of(new Worker()));

            workerService.findById(id);

            verify(workerRepository).findById(id);
        }

        @Test
        @DisplayName("validate findById returns workerRepository.findById found worker")
        void validateFindByIdReturnsWorkerRepositoryFindByIdFoundWorker() {
            long id = Instancio.create(long.class);
            Worker worker = Instancio.create(Worker.class);
            when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));

            Worker foundWorker = workerService.findById(id);

            assertEquals(worker, foundWorker);
        }

        @Test
        @DisplayName("validate findById throws NoSuchElementException if workerRepository.findById returns empty")
        void validateFindByIdThrowsNoSuchElementExceptionIfWorkerRepositoryFindByIdReturnsEmpty() {
            long id = Instancio.create(long.class);
            when(workerRepository.findById(anyLong())).thenReturn(Optional.empty());

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> workerService.findById(id));

            assertEquals("Worker not found", exception.getMessage());
        }
    }
}
