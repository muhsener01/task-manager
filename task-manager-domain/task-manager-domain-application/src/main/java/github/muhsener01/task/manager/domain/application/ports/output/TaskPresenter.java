package github.muhsener01.task.manager.domain.application.ports.output;

import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;

public interface TaskPresenter  extends ErrorHandlingOutputPort{


    void presentTaskCreated(Task savedTask);

    void presentInvalidDomainError(InvalidDomainException exception);
}
