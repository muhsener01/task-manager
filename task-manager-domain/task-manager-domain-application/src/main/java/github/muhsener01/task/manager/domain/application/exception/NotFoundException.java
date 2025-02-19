package github.muhsener01.task.manager.domain.application.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String resourceName , String key, Object value) {
        super("No such [%s] found with provided [%s]: [%s]".formatted(resourceName , key , value));
    }
}
