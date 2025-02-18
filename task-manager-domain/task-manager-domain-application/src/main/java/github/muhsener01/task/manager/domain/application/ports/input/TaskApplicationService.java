package github.muhsener01.task.manager.domain.application.ports.input;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;

public interface TaskApplicationService {


    void createTask(CreateTaskCommand command);
}
