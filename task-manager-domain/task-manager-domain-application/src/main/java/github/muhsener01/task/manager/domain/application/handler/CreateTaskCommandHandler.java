package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.mapper.TaskMapper;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;

public class CreateTaskCommandHandler extends AbstractCommandHandler {

    public CreateTaskCommandHandler(TaskPresenter presenter, TaskRepository repository) {
        super(presenter, repository);
    }

    public void createTask(CreateTaskCommand command) {
        try {

            Task taskToBeSaved = null;
            try {
                taskToBeSaved = TaskMapper.toDomainEntity(command);
            } catch (InvalidDomainException exception) {
                super.presenter.presentInvalidDomainError(exception);
                return;
            }

            Task savedTask = super.repository.save(taskToBeSaved);

            super.presenter.presentTaskCreated(savedTask);
        } catch (
                Exception e) {
            super.presenter.presentError(e);
        }
    }

}
