package github.muhsener01.task.manager.domain.application.ports.output;

public class DataAccessException extends RuntimeException {


    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
