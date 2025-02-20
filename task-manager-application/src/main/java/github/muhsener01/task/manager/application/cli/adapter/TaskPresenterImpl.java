package github.muhsener01.task.manager.application.cli.adapter;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.core.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        printSuccessMessageAndExit(prepareTaskViewMessage(taskDetailsDTO));
    }

    @Override
    public void presentTaskFound(List<TaskDetailsDTO> taskDetailsDTO) {
        String output = taskDetailsDTO.stream()
                .map(this::prepareTaskViewMessage)
                .collect(Collectors.joining("\n------------------------------------------\n"));

        printSuccessMessageAndExit(output);
    }

    @Override
    public void presentTaskUpdated(Task taskToUpdate) {
        String messageTemplate = "Task updated successfully: %s%nNew fields:%n%s";
        String output = messageTemplate.formatted(taskToUpdate.getId().val(), prepareTaskViewMessage(taskToUpdate));
        printSuccessMessageAndExit(output);
    }


    private String prepareTaskViewMessage(TaskDetailsDTO taskDetailsDTO) {
        String template = "ID: %s%nTitle: %s%nDescription: %s%nStatus: %s%nCreated at: %s%nUpdated at: %s";
        return template.formatted(taskDetailsDTO.getId().toString(),
                taskDetailsDTO.getTitle(),
                taskDetailsDTO.getDescription(),
                taskDetailsDTO.getStatus(),
                taskDetailsDTO.getCreatedAt() == null ? null : taskDetailsDTO.getCreatedAt().toString(),
                taskDetailsDTO.getUpdatedAt() == null ? null : taskDetailsDTO.getUpdatedAt().toString());
    }

    private String prepareTaskViewMessage(Task task) {
        String template = "ID: %s%nTitle: %s%nDescription: %s%nStatus: %s";
        return template.formatted(task.getId().val().toString(),
                task.getTitle().val(),
                task.getDescription().val(),
                task.getStatus().name()
        );
    }



}
