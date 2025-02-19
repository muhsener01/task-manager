package github.muhsener01.task.manager.domain.application.ports.input;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;

import java.util.UUID;

public interface TaskApplicationService {


    void createTask(CreateTaskCommand command);

    void getById(UUID id);

    void getAll();
}
