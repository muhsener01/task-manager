package github.muhsener01.task.manager.mapper;


import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import github.muhsener01.task.manager.entity.TaskJpaEntity;

import java.util.Optional;

public class DataAccessMapper {

    public static TaskJpaEntity toJpaEntity(Task task) {
        assert task.getId() != null;
        assert task.getTitle() != null;
        assert task.getStatus() != null;

        return new TaskJpaEntity(task.getId().val(),
                task.getTitle().val(),
                task.getDescription() == null ? null : task.getDescription().val(),
                task.getStatus().name()
        );


    }

    public static Task toDomainEntity(TaskJpaEntity jpa) {
        return Task.builder()
                .id(TaskId.of(jpa.getId()))
                .title(Title.of(jpa.getTitle()))
                .description(jpa.getDescription() == null ? null : Description.of(jpa.getDescription()))
                .status(TaskStatus.of(jpa.getStatus()))
                .build();
    }
}
