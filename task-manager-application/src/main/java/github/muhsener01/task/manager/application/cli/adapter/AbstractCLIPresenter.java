package github.muhsener01.task.manager.application.cli.adapter;

import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.application.ports.output.ErrorHandlingOutputPort;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;

import java.util.List;

public abstract class AbstractCLIPresenter extends ErrorHandler implements ErrorHandlingOutputPort {

    public static final String ERROR_MESSAGE_TEMPLATE = "[ERROR]: %s";

    protected void printSuccessMessageAndExit(String message) {
        System.out.println(message);
    }


    private void printErrorMessageAndExit(String message) {
        System.err.println(message);
    }


    protected void printErrorMessageAndExit(List<String> messages, int exitCode) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            sb.append(ERROR_MESSAGE_TEMPLATE.formatted(messages.get(i)));

            if (i != messages.size() - 1) sb.append("\n");
        }

        printErrorMessageAndExit(sb.toString());
    }




    @Override
    public void presentError(Exception e) {
        super.logError(e);
        printErrorMessageAndExit(List.of("Unknown internal error"), 1);
    }

    @Override
    public void presentNotFoundException(TaskNotFoundException exception) {
        super.logError(exception);
        printErrorMessageAndExit(List.of(exception.getMessage()), 1);
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

}
