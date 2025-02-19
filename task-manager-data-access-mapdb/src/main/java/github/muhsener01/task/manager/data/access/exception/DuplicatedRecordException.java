package github.muhsener01.task.manager.data.access.exception;

import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;

public class DuplicatedRecordException extends DataAccessException {

    public DuplicatedRecordException(String message) {
        super(message);
    }
}
