package github.muhsener01.task.manager.application.cli.adapter;

import github.muhsener01.task.manager.domain.application.ports.output.TaskPresenter;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskPresenterImpl extends AbstractCLIPresenter implements TaskPresenter {


    private static final Logger log = LoggerFactory.getLogger(TaskPresenterImpl.class);

    @Override
    public void presentTaskCreated(Task savedTask) {
        String outputMessage = "Task added successfully: %s".formatted(savedTask.getId().val());
        super.printSuccessMessageAndExit(outputMessage, 0);
    }

    @Override
    public void presentInvalidDomainError(InvalidDomainException exception) {

        super.logError(exception);

        String errorMessageTemplate = "[%s][%s] %s";


        List<String> messages = exception.getErrors().stream()
                .map(validationError -> errorMessageTemplate
                        .formatted(validationError.getObjectName(),
                                validationError.getFieldName(),
                                validationError.getMessage()))
                .toList();

        printErrorMessageAndExit(messages, 1);

    }

    @Override
    public void presentError(Exception e) {
        super.logError(e);
        printErrorMessageAndExit(List.of("Unknown internal error"), 1);
    }


}
