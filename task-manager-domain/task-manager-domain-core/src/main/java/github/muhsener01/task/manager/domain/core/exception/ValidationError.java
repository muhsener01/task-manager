package github.muhsener01.task.manager.domain.core.exception;


import java.util.*;

public class ValidationError {


    private static final Map<String, String> ERROR_MESSAGES = new HashMap<>();

    static {
        ERROR_MESSAGES.put("task.title", "Task must have a title that is non-blank and at most 200 characters long.");
        ERROR_MESSAGES.put("task.description", "Task description is optional, but if provided, it must be non-blank and at most 500 characters long.");

    }

    private String objectName;
    private String fieldName;
    private String message;

    public ValidationError(String objectName, String fieldName, String message) {
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    public static ValidationError createBuiltInErrorFor(String objectName, String fieldName) {
        String key = "%s.%s".formatted(objectName.toLowerCase(), fieldName.toLowerCase());

        String message = ERROR_MESSAGES.get(key);

        if (message == null)
            throw new IllegalArgumentException("Pre-built message not found for: [%s]".formatted(key));

        return new ValidationError(objectName, fieldName, message);

    }
}
