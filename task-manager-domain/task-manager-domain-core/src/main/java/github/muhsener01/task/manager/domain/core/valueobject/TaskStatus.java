package github.muhsener01.task.manager.domain.core.valueobject;

import java.util.Locale;

public enum TaskStatus {
    TODO, IN_PROGRESS, DONE;


    /// Returns TaskStatus based on given case-insensitive string <code>status</code> parameter.
    /// * todo -> <code>TODO</code>
    /// * done -> <code>DONE</code>
    /// * in_progress , inprogress , in-progress -> <code>IN_PROGRESS</code>
    ///
    /// @param status name of enum in string
    /// @return TaskStatus enum
    /// @throws IllegalArgumentException if argument status is unknown
    public static TaskStatus of(String status) throws IllegalArgumentException {
        String statusFormatted =
                status.toLowerCase(Locale.US).trim();

        return switch (statusFormatted) {
            case "todo" -> TODO;
            case "in_progress",
                 "in-progress",
                 "inprogress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException("Unknown status type: '%s'".formatted(status));
        };
    }

}
