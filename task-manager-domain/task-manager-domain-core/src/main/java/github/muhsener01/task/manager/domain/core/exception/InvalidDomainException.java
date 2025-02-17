package github.muhsener01.task.manager.domain.core.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidDomainException extends RuntimeException {

    private final List<ValidationError> errors;

    public InvalidDomainException(String objectName, List<ValidationError> errors) {
        super(objectName);
        this.errors = errors == null ? new ArrayList<>() : errors;
    }


    public List<ValidationError> getErrors() {
        return errors;
    }
}
