package github.muhsener01.task.manager.domain.application.exception;

public class TaskNotFoundException extends NotFoundException {

    public TaskNotFoundException(String key, Object value) {
        super("Task", key, value);
    }
}
