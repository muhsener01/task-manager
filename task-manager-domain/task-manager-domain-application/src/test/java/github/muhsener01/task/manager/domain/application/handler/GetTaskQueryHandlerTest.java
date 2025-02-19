package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.TestConfig;
import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
public class GetTaskQueryHandlerTest {
    @Mock
    TaskRepository repository;

    @Mock
    TaskPresenter presenter;

    @InjectMocks
    GetTaskQueryHandler handler;

    @Test
    void testInjections() {
        assertNotNull(repository);
        assertNotNull(presenter);
        assertNotNull(handler);
    }


    @Test
    void givenPassNullArgument_whenGetTaskById_thenCatchesIllegalArgumentExceptionAndCallPresentErrorMethod() {

        handler.getTaskById(null);

        Mockito.verify(repository, Mockito.times(0)).queryById(any(TaskId.class));
        Mockito.verify(presenter, Mockito.times(0)).presentNotFoundException(any(TaskNotFoundException.class));
        Mockito.verify(presenter, Mockito.times(0)).presentTaskFound(any(TaskDetailsDTO.class));
        Mockito.verify(presenter, Mockito.times(1)).presentError(any(IllegalArgumentException.class));
    }


    @Test
    void givenNonExistingTask_whenGetTaskById_thenCallsPresentNotFoundError() {

        when(repository.queryById(any(TaskId.class))).thenReturn(Optional.empty());

        handler.getTaskById(UUID.randomUUID());

        Mockito.verify(repository, Mockito.times(1)).queryById(any(TaskId.class));
        Mockito.verify(presenter, Mockito.times(1)).presentNotFoundException(any(TaskNotFoundException.class));
        Mockito.verify(presenter, Mockito.times(0)).presentTaskFound(any(TaskDetailsDTO.class));
        Mockito.verify(presenter, Mockito.times(0)).presentError(any(IllegalArgumentException.class));

    }



    @Test
    void givenExistingTaskId_whenGetTaskById_thenCallsPresentTaskFound() {
        UUID uuid = UUID.randomUUID();
        TaskDetailsDTO detailsDTO = getTaskDetailsDtoInstance(uuid);
        when(repository.queryById(any(TaskId.class))).thenReturn(Optional.of(detailsDTO));

        handler.getTaskById(uuid);

        Mockito.verify(repository, Mockito.times(1)).queryById(TaskId.of(uuid));
        Mockito.verify(presenter, Mockito.times(0)).presentNotFoundException(any(TaskNotFoundException.class));
        Mockito.verify(presenter, Mockito.times(1)).presentTaskFound(detailsDTO);
        Mockito.verify(presenter, Mockito.times(0)).presentError(any(Exception.class));

    }


    @Test
    void givenProperUUID_whenRepoThrowsDataAccessException_thenHandlesDataAccessExceptionAndCallsPresentErrorMethod() {
      UUID uuid = UUID.randomUUID();

        when(repository.queryById(TaskId.of(uuid))).thenThrow(DataAccessException.class);

        handler.getTaskById(uuid);

        Mockito.verify(repository, Mockito.times(1)).queryById(TaskId.of(uuid));
        Mockito.verify(presenter, Mockito.times(0)).presentNotFoundException(any(TaskNotFoundException.class));
        Mockito.verify(presenter, Mockito.times(0)).presentTaskFound(any(TaskDetailsDTO.class));
        Mockito.verify(presenter, Mockito.times(1)).presentError(any(DataAccessException.class));
    }


    private TaskDetailsDTO getTaskDetailsDtoInstance(UUID uuid){
        return new TaskDetailsDTO() {
            @Override
            public UUID getId() {
                return uuid;
            }

            @Override
            public String getTitle() {
                return "test";
            }

            @Override
            public String getDescription() {
                return "test";
            }

            @Override
            public String getStatus() {
                return "DONE";
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return LocalDateTime.now();
            }
        };
    }



}
