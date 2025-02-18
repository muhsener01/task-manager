package github.muhsener01.task.manager.adapter;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import github.muhsener01.task.manager.entity.TaskJpaEntity;
import github.muhsener01.task.manager.repository.TaskJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@Import({TaskRepositoryAdapter.class})
public class SQLiteIntegrationTest {

    @Autowired
    TaskRepositoryAdapter adapter;

    @Autowired
    TaskJpaRepository repository;
    @Autowired
    private TaskRepository taskRepository;


    @Test
    void testAdapterNotNull() {
        assertNotNull(adapter);
        assertNotNull(repository);
    }

    @Test
    void givenTaskEntity_whenSaveAndFetch_thenReturnsTaskEntity() {

        Task task = Task.builder()
                .id(TaskId.of(UUID.randomUUID()))
                .title(Title.of("test title"))
                .description(Description.of("test description"))
                .status(TaskStatus.DONE)
                .build();


        Task savedTask = adapter.save(task);


        Optional<TaskJpaEntity> byId = repository.findById(savedTask.getId().val());
        assertTrue(byId.isPresent());
        TaskJpaEntity foundJpaEntity = byId.get();
        assertNotNull(foundJpaEntity.getCreatedAt());
        assertTrue(foundJpaEntity.getCreatedAt().minusSeconds(3).isBefore(LocalDateTime.now()));


        assertEquals(savedTask.getId().val(), foundJpaEntity.getId());
        assertEquals(savedTask.getTitle().val(), foundJpaEntity.getTitle());
        assertEquals(savedTask.getDescription().val(), foundJpaEntity.getDescription());
        assertEquals(savedTask.getStatus().name(), foundJpaEntity.getStatus());
        assertNull(foundJpaEntity.getUpdatedAt());
    }


    @Test
    void givenTaskJpaEntityWithoutId_whenSave_thenThrowsJpaSystemException() {
        TaskJpaEntity taskJpaEntity = new TaskJpaEntity(null, "title", "description", "TODO");
        assertThrows(JpaSystemException.class, () -> repository.save(taskJpaEntity));
    }


    @Test
    void givenTask_whenQueryById_thenReturnsTaskDetailsDtoWithAuditingInfo() {
        Task task = Task.builder()
                .id(TaskId.of(UUID.randomUUID()))
                .title(Title.of("test title"))
                .description(Description.of("test description"))
                .status(TaskStatus.DONE)
                .build();


        taskRepository.save(task);
        Optional<TaskDetailsDTO> optional = adapter.queryById(task.getId());

        assertTrue(optional.isPresent());
        TaskDetailsDTO details = optional.get();
        assertEquals(task.getId().val() , details.getId());
        assertEquals("test title" , details.getTitle());
        assertEquals("test description" , details.getDescription());
        assertEquals(TaskStatus.DONE.name() , details.getStatus());
        assertNotNull(details.getCreatedAt());
        assertNull(details.getUpdatedAt());
    }


    @Test
    void giveNullId_whenFindById_thenThrowsDataAccessExceptionWithIllegalArgumentExceptionInside() {
        DataAccessException dataAccessException = assertThrows(DataAccessException.class, () -> adapter.findById(null));

        assertEquals("id argument cannot be null in method findById()" , dataAccessException.getMessage());

        IllegalArgumentException cause = ((IllegalArgumentException) dataAccessException.getCause());
        assertNotNull(cause);

        assertEquals("id argument cannot be null in method findById()" , cause.getMessage());



    }


    @Test
    void giveNullId_whenQueryById_thenThrowsDataAccessExceptionWithIllegalArgumentExceptionInside() {
        DataAccessException dataAccessException = assertThrows(DataAccessException.class, () -> adapter.queryById(null));

        assertEquals("id argument cannot be null in method queryById()" , dataAccessException.getMessage());

        IllegalArgumentException cause = ((IllegalArgumentException) dataAccessException.getCause());
        assertNotNull(cause);

        assertEquals("id argument cannot be null in method queryById()" , cause.getMessage());



    }
}
