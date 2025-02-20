package github.muhsener01.task.manager.data.access.adapter;

import github.muhsener01.task.manager.data.access.DaoConfig;
import github.muhsener01.task.manager.data.access.exception.DuplicatedRecordException;
import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfig.class)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskRepositoryMapDBAdapterTest {

    @Autowired
    TaskRepositoryMapDBAdapter adapter;
    @Autowired
    private TaskRepositoryMapDBAdapter taskRepositoryMapDBAdapter;

    @BeforeEach
    public void setupDatabase() {

    }


    @Test
    @Order(1)
    void testAdapterNotNull() {
        assertNotNull(adapter);
    }

    @Test
    @Order(2)
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
    @Order(3)
    void givenNullId_whenFindById_thenThrowsDataAccessException() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> adapter.findById(null));

        assertEquals("null id argument for method findById()", ex.getMessage());
        assertInstanceOf(IllegalArgumentException.class, ex.getCause());
    }


    @Test
    @Order(4)
    void givenNullId_whenQueryById_thenThrowsDataAccessException() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> adapter.queryById(null));

        assertEquals("null id argument for method queryById()", ex.getMessage());
        assertInstanceOf(IllegalArgumentException.class, ex.getCause());
    }

    @Test
    @Order(5)
    void givenDuplicatedTask_whenSave_thenThrowsDataAccessException() {
        Task task = buildAProperTask();
        adapter.save(task);

        DataAccessException dataAccessException = assertThrows(DataAccessException.class, () -> adapter.save(task));

        assertEquals("Duplicated Task record with ID: " + task.getId().val(), dataAccessException.getMessage());
        assertInstanceOf(DuplicatedRecordException.class, dataAccessException.getCause());
    }

    @Test
    @Order(6)
    void givenIdOfNonExistingTask_whenFindById_thenReturnsEmptyOptional() {
        Optional<Task> optional = adapter.findById(TaskId.of(UUID.randomUUID()));

        assertTrue(optional.isEmpty());
    }

    @Test
    @Order(7)
    void givenIdOfNonExistingTask_whenQueryById_thenReturnsEmptyOptional() {
        Optional<TaskDetailsDTO> optional = adapter.queryById(TaskId.of(UUID.randomUUID()));

        assertTrue(optional.isEmpty());
    }


    @Test
    @Order(10)
    void givenFiveTasksInDB_whenQueryAll_thenReturnsListOfTasksInDescendingOrderByCreatedAt() throws InterruptedException {
        adapter.removeAll();

        for (int i = 0; i < 5; i++) {
            Task task = buildAProperTask();
            Thread.sleep(500);
            taskRepositoryMapDBAdapter.save(task);
        }

        List<TaskDetailsDTO> tasks = taskRepositoryMapDBAdapter.queryAll();
        assertEquals(5, tasks.size());

        for (int i = 0; i < tasks.size() - 1; i++) {
            assertTrue(tasks.get(i).getCreatedAt().isAfter(tasks.get(i + 1).getCreatedAt()));
        }
    }


    @Test
    @Order(11)
    void givenTenRecordsInDb_whenRemoveAll_thenRemovesAllRecords() {
        adapter.removeAll();

        for (int i = 0; i < 10; i++) {
            adapter.save(buildAProperTask());
        }


        assertEquals(10, adapter.queryAll().size());

        adapter.removeAll();


        assertEquals(0, adapter.queryAll().size());

    }

    @Test
    void givenTaskEntity_whenUpdateTask_thenUpdatesTaskAndReturnsUpdatedOne() {
        Task task = buildAProperTask();

        Task savedTask = adapter.save(task);


        savedTask.update(Title.of("newTitle"), null, null);

        adapter.update(savedTask);


        Optional<TaskDetailsDTO> optional = adapter.queryById(savedTask.getId());
        assertTrue(optional.isPresent());

        TaskDetailsDTO updatedTaskDetails = optional.get();
        assertEquals("newTitle" , updatedTaskDetails.getTitle());
        assertNotNull(updatedTaskDetails.getCreatedAt());
        assertTrue(updatedTaskDetails.getCreatedAt().isBefore(LocalDateTime.now()));
        assertNotNull(updatedTaskDetails.getUpdatedAt());
        assertTrue(updatedTaskDetails.getCreatedAt().isBefore(updatedTaskDetails.getUpdatedAt()));


    }

    public Task buildAProperTask() {
        return Task.builder().id(TaskId.of(UUID.randomUUID())).title(Title.of("test title")).description(Description.of("test description")).status(TaskStatus.DONE).build();
    }
}
