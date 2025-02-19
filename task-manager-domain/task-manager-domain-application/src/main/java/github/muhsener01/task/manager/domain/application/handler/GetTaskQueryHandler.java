package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetTaskQueryHandler extends AbstractCommandHandler {

    public GetTaskQueryHandler(TaskPresenter presenter, TaskRepository repository) {
        super(presenter, repository);
    }


    public void getTaskById(UUID uuid) {
        try {
            Optional<TaskDetailsDTO> optional = super.repository.queryById(TaskId.of(uuid));

            if(optional.isEmpty()){
                super.presenter.presentNotFoundException(new TaskNotFoundException("id",uuid) );
                return;
            }

            presenter.presentTaskFound(optional.get());


        } catch (Exception e) {
            presenter.presentError(e);
        }
    }
}

