package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.TestConfig;
import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.mapper.TaskMapper;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestConfig.class)
public class CreateTaskCommandHandlerTest {

    @InjectMocks
    private CreateTaskCommandHandler handler;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskPresenter taskPresenter;


    @Test
    void testHandlerNotNull() {
        assertNotNull(handler);
        assertNotNull(taskRepository);
        assertNotNull(taskPresenter);

        assertEquals(taskRepository, handler.repository);
        assertEquals(taskPresenter, handler.presenter);

    }

    @Test
    void givenValidCommand_whenCreateTask_thenCreatesTaskSuccessfully() {
        CreateTaskCommand command = new CreateTaskCommand("test title", "test description");
        Task savedTask = TaskMapper.toDomainEntity(command);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        handler.createTask(command);

        verify(taskPresenter, times(0)).presentInvalidDomainError(any(InvalidDomainException.class));
        verify(taskPresenter, times(0)).presentError(any(Exception.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskPresenter, times(1)).presentTaskCreated(savedTask);

    }


    @Test
    void givenInvalidCommand_whenCreateTask_thenHandlesInvalidDomainException() {
        CreateTaskCommand command = new CreateTaskCommand(null, "test description");

        handler.createTask(command);


        verify(taskPresenter, times(1)).presentInvalidDomainError(any(InvalidDomainException.class));
        verify(taskRepository, times(0)).save(any(Task.class));
        verify(taskPresenter, times(0)).presentError(any(Exception.class));
        verify(taskPresenter, times(0)).presentTaskCreated(any(Task.class));

    }


    @Test
    void givenRepoThrowsDataAccessException_whenCreateTask_thenHandlesException() {
        CreateTaskCommand command = new CreateTaskCommand("valid", "test description");
        when(taskRepository.save(any(Task.class))).thenThrow(DataAccessException.class);

        handler.createTask(command);

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskPresenter, times(0)).presentInvalidDomainError(any(InvalidDomainException.class));
        verify(taskPresenter,times(0)).presentTaskCreated(any(Task.class));
        verify(taskPresenter,times(1)).presentError(any(DataAccessException.class));

    }
}
