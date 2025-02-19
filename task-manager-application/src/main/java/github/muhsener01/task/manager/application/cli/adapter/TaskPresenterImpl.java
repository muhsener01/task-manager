package github.muhsener01.task.manager.application.cli.adapter;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.core.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskPresenterImpl extends AbstractCLIPresenter implements TaskPresenter {




    @Override
    public void presentTaskCreated(Task savedTask) {
        assert savedTask.getId() != null;

        String outputMessage = "Task added successfully: %s".formatted(savedTask.getId().val());
        super.printSuccessMessageAndExit(outputMessage);
    }

    @Override
    public void presentTaskFound(TaskDetailsDTO taskDetailsDTO) {
        String template = "ID: %s%nTitle: %s%nDescription: %s%nStatus: %s%nCreated at: %s%nUpdated at: %s";
        String formattedMessage = template.formatted(taskDetailsDTO.getId().toString(),
                taskDetailsDTO.getTitle(),
                taskDetailsDTO.getDescription(),
                taskDetailsDTO.getStatus(),
                taskDetailsDTO.getCreatedAt() == null ? null : taskDetailsDTO.getCreatedAt().toString(),
                taskDetailsDTO.getUpdatedAt() == null ? null : taskDetailsDTO.getUpdatedAt().toString());

        printSuccessMessageAndExit(formattedMessage);
    }


}
