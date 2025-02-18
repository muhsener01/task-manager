package github.muhsener01.task.manager.domain.application;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.handler.CreateTaskCommandHandler;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;


public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;

    public TaskApplicationServiceImpl(CreateTaskCommandHandler createTaskCommandHandler) {
        this.createTaskCommandHandler = createTaskCommandHandler;
    }

    @Override
    public void createTask(CreateTaskCommand command) {
        createTaskCommandHandler.createTask(command);
    }
}
