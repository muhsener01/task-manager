package github.muhsener01.task.manager.data.access.adapter;

import github.muhsener01.task.manager.data.access.DaoConfig;
import github.muhsener01.task.manager.data.access.exception.DuplicatedRecordException;
import github.muhsener01.task.manager.data.access.mapper.DataAccessMapper;
import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfig.class)
@ActiveProfiles("local")
public class TaskRepositoryMapDBAdapterTest {

    @Autowired
    TaskRepositoryMapDBAdapter adapter;


    @Test
    void testAdapterNotNull() {
        assertNotNull(adapter);
    }

    @Test
    void givenTask_whenSave_thenReturnsSavedTaskEntity() {
        Task task = buildAProperTask();

        Task savedTask = adapter.save(task);


        Optional<Task> optional = adapter.findById(savedTask.getId());

        assertTrue(optional.isPresent());
        Task found = optional.get();

        assertEquals(savedTask.getId(), found.getId());
        assertEquals(savedTask.getTitle(), found.getTitle());
        assertEquals(savedTask.getDescription(), found.getDescription());
        assertEquals(savedTask.getStatus(), found.getStatus());

        Optional<TaskDetailsDTO> optionalDetails = adapter.queryById(savedTask.getId());
        assertTrue(optionalDetails.isPresent());

        TaskDetailsDTO detailsDTO = optionalDetails.get();
        assertNotNull(detailsDTO.getCreatedAt());
        assertNull(detailsDTO.getUpdatedAt());

        assertTrue(detailsDTO.getCreatedAt().minusSeconds(3).isBefore(LocalDateTime.now()));
    }

    @Test
    void givenNullId_whenFindById_thenThrowsDataAccessException() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> adapter.findById(null));

        assertEquals("null id argument for method findById()", ex.getMessage());
        assertInstanceOf(IllegalArgumentException.class, ex.getCause());
    }


    @Test
    void givenNullId_whenQueryById_thenThrowsDataAccessException() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> adapter.queryById(null));

        assertEquals("null id argument for method queryById()", ex.getMessage());
        assertInstanceOf(IllegalArgumentException.class, ex.getCause());
    }

    @Test
    void givenDuplicatedTask_whenSave_thenThrowsDataAccessException() {
        Task task = buildAProperTask();
        adapter.save(task);

        DataAccessException dataAccessException = assertThrows(DataAccessException.class, () -> adapter.save(task));

        assertEquals("Duplicated Task record with ID: "+ task.getId().val() , dataAccessException.getMessage());
        assertInstanceOf(DuplicatedRecordException.class , dataAccessException.getCause());
    }

    @Test
    void givenIdOfNonExistingTask_whenFindById_thenReturnsEmptyOptional() {
        Optional<Task> optional = adapter.findById(TaskId.of(UUID.randomUUID()));

        assertTrue(optional.isEmpty());
    }

    @Test
    void givenIdOfNonExistingTask_whenQueryById_thenReturnsEmptyOptional() {
        Optional<TaskDetailsDTO> optional = adapter.queryById(TaskId.of(UUID.randomUUID()));

        assertTrue(optional.isEmpty());
    }


    public Task buildAProperTask(){
        return Task.builder()
                .id(TaskId.of(UUID.randomUUID()))
                .title(Title.of("test title"))
                .description(Description.of("test description"))
                .status(TaskStatus.DONE)
                .build();
    }
}
