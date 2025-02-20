package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.dto.TaskUpdateCommand;
import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateTaskCommandHandler extends AbstractCommandHandler {


    private static final Logger log = LoggerFactory.getLogger(UpdateTaskCommandHandler.class);

    public UpdateTaskCommandHandler(TaskPresenter presenter, TaskRepository repository) {
        super(presenter, repository);
    }


    public void handle(TaskUpdateCommand command) {
        try {
            UUID uuid = command.getId();
            Optional<Task> optional = super.repository.findById(TaskId.of(uuid));

            if (optional.isEmpty()) {
                super.presenter.presentNotFoundException(new TaskNotFoundException("id", uuid));
                return;
            }


            Task taskToUpdate = optional.get();
            try{
                taskToUpdate.update(command.getTitle() == null ? null : Title.of(command.getTitle()),
                        command.getDescription() == null ? null : Description.of(command.getDescription()),
                        command.getStatus() == null ? null : TaskStatus.of(command.getStatus()));
            }catch (InvalidDomainException invalidDomainException){
                super.presenter.presentInvalidDomainError(invalidDomainException);
                return;
            }


            repository.update(taskToUpdate);
            log.info("[TASK UPDATED]: --id='{}' --title='{}' --description='{}' --status='{}'",
                    taskToUpdate.getId().val(), taskToUpdate.getTitle().val(), taskToUpdate.getDescription().val(), taskToUpdate.getStatus().name());

            presenter.presentTaskUpdated(taskToUpdate);


        } catch (Exception exception) {
            super.presenter.presentError(exception);
        }
    }
}
