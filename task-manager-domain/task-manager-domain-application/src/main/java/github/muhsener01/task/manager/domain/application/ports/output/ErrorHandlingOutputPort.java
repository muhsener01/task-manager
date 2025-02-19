package github.muhsener01.task.manager.domain.application.ports.output;

public interface ErrorHandlingOutputPort {

    void presentError(Exception e);
}
