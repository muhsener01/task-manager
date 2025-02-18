package github.muhsener01.task.manager.domain.application.ports.output;


import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task) throws DataAccessException;

    Optional<Task> findById(TaskId id) throws DataAccessException;

    Optional<TaskDetailsDTO> queryById(TaskId id) throws DataAccessException;
}
