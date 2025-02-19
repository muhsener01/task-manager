package github.muhsener01.task.manager.domain.application.ports.output;

import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;

public interface ErrorHandlingOutputPort {

    void presentError(Exception e);

    void presentNotFoundException(TaskNotFoundException id);

    void presentInvalidDomainError(InvalidDomainException e);
}
