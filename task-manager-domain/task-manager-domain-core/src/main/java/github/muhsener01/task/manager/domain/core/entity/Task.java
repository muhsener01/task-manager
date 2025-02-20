package github.muhsener01.task.manager.domain.core.entity;

import github.muhsener01.task.manager.domain.core.exception.Assert;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import github.muhsener01.task.manager.domain.core.exception.ValidationError;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task extends BaseEntity<TaskId> {

    private Title title;
    private Description description;
    private TaskStatus status;


    private Task(TaskBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;

        if (id == null)
            initialize();

        validate();
    }


    private void initialize() {
        this.id = TaskId.of(UUID.randomUUID());
        this.status = TaskStatus.TODO;
    }

    private void validate() {
        List<ValidationError> errorList = new ArrayList<>();

        //check title
        if (title == null || title.isBlank() || title.isLongerThan(200))
            errorList.add(ValidationError.createBuiltInErrorFor("task", "title"));

        //check description
        if (description != null) {
            if (description.isBlank() || description.isLongerThan(500))
                errorList.add(ValidationError.createBuiltInErrorFor("task", "description"));
        }


        // check id and status
        if (id != null && status == null)
            throw new IllegalStateException("Task ID is provided but status is null. This is an invalid state.");


        //throw exception if there are any violations
        if (!errorList.isEmpty())
            throw new InvalidDomainException("Task", errorList);


    }


    /**
     * Updates {@code Task} with provided non-null {@code params} and validates
     * according to the following rules:
     * <ul>
     *     <li>Title must be at most 200 chars.</li>
     *     <li>Description must be at most 50 chars.</li>
     * </ul>
     *
     * @param title       new title
     * @param description new description
     * @param status      new status
     * @throws InvalidDomainException if there is violation in domain constraints.
     */
    public void update(Title title, Description description, TaskStatus status) throws InvalidDomainException {
        if (title != null)
            this.title = title;

        if (description != null)
            this.description = description;

        if (status != null)
            this.status = status;

        validate();

    }


    public void changeStatus(TaskStatus status) throws IllegalArgumentException {
        Assert.notNull(status, "argument status cannot be null");
        this.status = status;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }


    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public static class TaskBuilder {
        private TaskId id;
        private Title title;
        private Description description;
        private TaskStatus status;


        public TaskBuilder id(TaskId val) {
            this.id = val;
            return this;
        }

        public TaskBuilder title(Title val) {
            this.title = val;
            return this;
        }

        public TaskBuilder description(Description val) {
            this.description = val;
            return this;
        }

        public TaskBuilder status(TaskStatus val) {
            this.status = val;
            return this;
        }


        /**
         * Builds and returns a {@code Task} entity with the following rules:
         *
         * <ul>
         *     <li>If neither ID nor status is provided, a random ID is assigned and the default status is {@code TODO}</li>
         *     <li>If an ID is provided, a status must also be provided.</li>
         *     <li>Validates domain invariants:</li>
         *     <ul>
         *         <li>Title must be non-blank and at most 200 characters.</li>
         *         <li>Description is optional, but if provided, it must be non-blank and at most 500 characters</li>
         *     </ul>
         * </ul>
         *
         * @return A valid <code>Task</code> instance.
         * @throws IllegalStateException  If an ID is provided without a corresponding status.
         * @throws InvalidDomainException If any domain constraint is violated.
         */
        public Task build() throws IllegalStateException, InvalidDomainException {
            return new Task(this);
        }

    }
}
