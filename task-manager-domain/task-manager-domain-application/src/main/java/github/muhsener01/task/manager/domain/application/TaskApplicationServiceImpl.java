package github.muhsener01.task.manager.domain.application;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.dto.TaskUpdateCommand;
import github.muhsener01.task.manager.domain.application.handler.CreateTaskCommandHandler;
import github.muhsener01.task.manager.domain.application.handler.GetAllTasksQueryHandler;
import github.muhsener01.task.manager.domain.application.handler.GetTaskQueryHandler;
import github.muhsener01.task.manager.domain.application.handler.UpdateTaskCommandHandler;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final GetTaskQueryHandler getTaskQueryHandler;
    private final GetAllTasksQueryHandler getAllTasksQueryHandler;
    private final UpdateTaskCommandHandler updateTaskCommandHandler;

    public TaskApplicationServiceImpl(CreateTaskCommandHandler createTaskCommandHandler,
                                      GetTaskQueryHandler getTaskQueryHandler,
                                      GetAllTasksQueryHandler getAllTasksQueryHandler,
                                      UpdateTaskCommandHandler updateTaskCommandHandler) {
        this.createTaskCommandHandler = createTaskCommandHandler;
        this.getTaskQueryHandler = getTaskQueryHandler;
        this.getAllTasksQueryHandler = getAllTasksQueryHandler;
        this.updateTaskCommandHandler = updateTaskCommandHandler;
    }

    @Override
    public void createTask(CreateTaskCommand command) {
        createTaskCommandHandler.createTask(command);
    }

    @Override
    public void getById(UUID id) {
        getTaskQueryHandler.getTaskById(id);
    }

    @Override
    public void getAll() {
        getAllTasksQueryHandler.handle();
    }

    @Override
    public void updateTask(TaskUpdateCommand taskUpdateCommand) {
        updateTaskCommandHandler.handle(taskUpdateCommand);
    }
}
