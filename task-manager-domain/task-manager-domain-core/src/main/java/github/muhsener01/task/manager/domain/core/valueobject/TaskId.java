package github.muhsener01.task.manager.domain.core.valueobject;

import java.util.UUID;

public class TaskId extends BaseId<UUID> {

    private TaskId(UUID val) {
        super(val);
    }

    /**
     * @param uuid value of TaskId
     * @return TaskId
     * @throws IllegalArgumentException if argument uuid is null
     */
    public static TaskId of(UUID uuid) throws IllegalArgumentException{
        return new TaskId(uuid);
    }


}
