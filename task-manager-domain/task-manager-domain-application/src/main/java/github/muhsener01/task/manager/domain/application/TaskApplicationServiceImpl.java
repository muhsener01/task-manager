package github.muhsener01.task.manager.domain.application;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.handler.CreateTaskCommandHandler;
import github.muhsener01.task.manager.domain.application.handler.GetTaskQueryHandler;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final GetTaskQueryHandler getTaskQueryHandler;

    public TaskApplicationServiceImpl(CreateTaskCommandHandler createTaskCommandHandler, GetTaskQueryHandler getTaskQueryHandler) {
        this.createTaskCommandHandler = createTaskCommandHandler;
        this.getTaskQueryHandler = getTaskQueryHandler;
    }

    @Override
    public void createTask(CreateTaskCommand command) {
        createTaskCommandHandler.createTask(command);
    }

    @Override
    public void getById(UUID id) {
        getTaskQueryHandler.getTaskById(id);
    }
}
