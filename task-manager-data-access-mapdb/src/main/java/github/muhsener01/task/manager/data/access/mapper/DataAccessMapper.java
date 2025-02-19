package github.muhsener01.task.manager.data.access.mapper;


import github.muhsener01.task.manager.data.access.adapter.TaskDetails;
import github.muhsener01.task.manager.data.access.entity.TaskEntity;
import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;

import java.lang.reflect.Proxy;
import java.util.List;

public class DataAccessMapper {

    public static TaskEntity toJpaEntity(Task task) {
        assert task.getId() != null;
        assert task.getTitle() != null;
        assert task.getStatus() != null;

        return new TaskEntity(task.getId().val(),
                task.getTitle().val(),
                task.getDescription() == null ? null : task.getDescription().val(),
                task.getStatus().name()
        );


    }

    public static Task toDomainEntity(TaskEntity jpa) {
        return Task.builder()
                .id(TaskId.of(jpa.getId()))
                .title(Title.of(jpa.getTitle()))
                .description(jpa.getDescription() == null ? null : Description.of(jpa.getDescription()))
                .status(TaskStatus.of(jpa.getStatus()))
                .build();
    }

    public static TaskDetailsDTO toDetailsDTO(TaskEntity taskEntity) {
        return new TaskDetails(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(),
                taskEntity.getStatus(), taskEntity.getCreatedAt(), taskEntity.getUpdatedAt());
    }

    public static List<TaskDetailsDTO> toDetailsDTO(List<TaskEntity> taskEntityList) {
        return taskEntityList.stream().map(DataAccessMapper::toDetailsDTO).toList();
    }
}
