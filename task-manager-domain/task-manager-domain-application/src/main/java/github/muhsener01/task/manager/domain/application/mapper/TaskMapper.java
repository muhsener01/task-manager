package github.muhsener01.task.manager.domain.application.mapper;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.Title;

public class TaskMapper {


    /**
     * Maps a <code>CreateTaskCommand</code> to a <code>Task</code> domain entity.
     *<p>This method does not explicitly throw exceptions, but it indirectly triggers the domain
     * validation rules within <code>Task</code> entity. If the provided data is invalid,
     * the <code>build()</code> method of TaskBuilder may throw <code>InvalidDomainException</code>.</p>
     *
     * <p>NOTE: The result <code>Task</code> entity will have:
     * <ul>
     *     <li>A system generated <code>id</code> if none is provided.</li>
     *     <li>A default <code>status</code> of <code>TODO</code> if no status is set.</li>
     * </ul>
     *
     * @param command the input command containing task details
     * @return a <code>Task</code> domain entity with random <code>id</code> and default <code>status</code>: <code>TODO</code>
     * @throws InvalidDomainException if the task creation fails due to domain constraints.
     * This exception has a list of <code>ValidationError</code> representing domain violations.
     */
    public static Task toDomainEntity(CreateTaskCommand command){
        return Task.builder()
                .title(command.getTitle() == null ? null : Title.of(command.getTitle()))
                .description(command.getDescription() == null ? null : Description.of(command.getDescription()))
                .build();
    }
}
