package github.muhsener01.task.manager.domain.core.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvalidDomainException extends RuntimeException {

    private final List<ValidationError> errors;

    public InvalidDomainException(String objectName, List<ValidationError> errors) {
        super(generateExceptionMessage(objectName , errors == null ? new ArrayList<>() : errors));
        this.errors = errors == null ? new ArrayList<>() : errors;
    }


    public List<ValidationError> getErrors() {
        return errors;
    }

    private static String generateExceptionMessage(String objectName, List<ValidationError> errors){
        String fieldViolationsTemplate = "[%s] %s";
        String allMessageTemplate = "[%s]: %s";

        String collect = errors.stream()
                .map(error -> fieldViolationsTemplate.formatted(error.getFieldName(), error.getMessage()))
                .collect(Collectors.joining(" : "));

        return allMessageTemplate.formatted(objectName , collect);


    }
}
