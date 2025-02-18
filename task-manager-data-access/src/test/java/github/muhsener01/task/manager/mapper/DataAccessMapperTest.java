package github.muhsener01.task.manager.mapper;

import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import github.muhsener01.task.manager.entity.TaskJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessMapperTest {

    TaskId taskId = TaskId.of(UUID.randomUUID());
    Title title = Title.of("title");
    Description description = Description.of("description");
    TaskStatus status = TaskStatus.TODO;

    Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(taskId)
                .title(title)
                .description(description)
                .status(status)
                .build();
    }


    @Test
    void givenTaskEntity_whenToJpaEntity_thenReturnsTaskJpaEntity() {
        TaskJpaEntity jpaEntity = DataAccessMapper.toJpaEntity(task);

        assertNotNull(jpaEntity);
        assertEquals(taskId.val(), jpaEntity.getId());
        assertEquals(title.val(), jpaEntity.getTitle());
        assertEquals(description.val(), jpaEntity.getDescription());
        assertEquals(status.name(), jpaEntity.getStatus());
    }




}
