package github.muhsener01.task.manager.domain.application.handler;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllTasksQueryHandler extends AbstractCommandHandler {


    public GetAllTasksQueryHandler(TaskPresenter presenter, TaskRepository repository) {
        super(presenter, repository);
    }


    public void handle() {
        try {

            List<TaskDetailsDTO> tasks = super.repository.queryAll();


            presenter.presentTaskFound(tasks);

        } catch (Exception e) {
            presenter.presentError(e);
        }
    }
}
