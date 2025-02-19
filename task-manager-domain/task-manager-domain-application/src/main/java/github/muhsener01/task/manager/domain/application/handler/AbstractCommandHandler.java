package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;

public  abstract class AbstractCommandHandler {

    protected final TaskPresenter presenter;
    protected final TaskRepository repository;

    public AbstractCommandHandler(TaskPresenter presenter, TaskRepository repository) {
        this.presenter = presenter;
        this.repository = repository;
    }
}
