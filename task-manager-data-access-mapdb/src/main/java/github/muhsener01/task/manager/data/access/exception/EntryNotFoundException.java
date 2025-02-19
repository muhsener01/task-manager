package github.muhsener01.task.manager.data.access.exception;

import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;

public class EntryNotFoundException extends DataAccessException {

    public EntryNotFoundException(String message) {
        super(message);
    }
}
