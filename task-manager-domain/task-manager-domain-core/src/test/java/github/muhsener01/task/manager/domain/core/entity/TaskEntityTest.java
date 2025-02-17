package github.muhsener01.task.manager.domain.core.entity;

import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import github.muhsener01.task.manager.domain.core.exception.ValidationError;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskEntityTest {

    private TaskId properId = TaskId.of(UUID.randomUUID());
    private Title properTitle = Title.of("proper title");
    private Description properDescription = Description.of("proper description");
    private TaskStatus doneStatus = TaskStatus.DONE;


    @Test
    void givenTitleAndDescription_whenTaskBuild_thenReturnsTaskEntity() {
        Title title = Title.of("task title test-1");
        Description description = Description.of("task description test-1");

        Task task = Task.builder()
                .title(title)
                .description(description)
                .build();
        assertNotNull(task);
        assertNotNull(task.getId());
        assertNotNull(task.getStatus());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals("task title test-1", task.getTitle().val());
        assertEquals("task description test-1", task.getDescription().val());
    }


    @Test
    void givenJustTitle_whenTaskBuild_thenReturnsTaskEntityWithoutDescription() {
        Task task = Task.builder()
                .title(properTitle)
                .build();
        assertNotNull(task);
        assertNotNull(task.getId());
        assertNotNull(task.getStatus());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals("proper title", task.getTitle().val());
        assertNull(task.getDescription());
    }


    @Test
    void givenNotTitle_whenTaskBuild_thenThrowsInvalidDomainException() {

        InvalidDomainException ex = assertThrows(InvalidDomainException.class, () -> {
            Task.builder()
                    .description(properDescription)
                    .build();
        });

        assertNotNull(ex);
        assertEquals(1, ex.getErrors().size());
        ValidationError validationError = ex.getErrors().getFirst();
        assertEquals("task", validationError.getObjectName());
        assertEquals("title", validationError.getFieldName());
        assertEquals("Task must have a title that is non-blank and at most 200 characters long.", validationError.getMessage());

    }


    @Test
    void givenNoTitleAndTooLongDescription_whenTaskBuild_thenThrowsInvalidDomainException() {


        InvalidDomainException ex = assertThrows(InvalidDomainException.class, () -> {
            Task.builder()
                    .description(Description.of(generateString(510)))
                    .build();
        });

        assertNotNull(ex);
        assertEquals(2, ex.getErrors().size());
        ValidationError titleError = ex.getErrors().getFirst();
        assertEquals("task", titleError.getObjectName());
        assertEquals("title", titleError.getFieldName());
        assertEquals("Task must have a title that is non-blank and at most 200 characters long.", titleError.getMessage());
        ValidationError descriptionError = ex.getErrors().get(1);
        assertEquals("task", descriptionError.getObjectName());
        assertEquals("description", descriptionError.getFieldName());
        assertEquals("Task description is optional, but if provided, it must be non-blank and at most 500 characters long.", descriptionError.getMessage());

    }


    @Test
    void givenAllFields_whenTaskBuild_thenReturnsValidTaskEntity() {
        Task task = Task.builder()
                .id(properId)
                .title(properTitle)
                .description(properDescription)
                .status(doneStatus)
                .build();

        assertNotNull(task);
        assertEquals(properId, task.getId());
        assertEquals(properTitle, task.getTitle());
        assertEquals(properDescription, task.getDescription());
        assertEquals(doneStatus, task.getStatus());
    }


    @Test
    void givenIdProvidedButNotStatus_whenTaskBuild_thenThrowsIllegalStateException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Task.builder()
                    .id(properId)
                    .title(properTitle)
                    .description(properDescription)
                    .build();
        });

        assertNotNull(exception);
        assertEquals("Task ID is provided but status is null. This is an invalid state." , exception.getMessage());
    }








    private String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("x");
        }

        return sb.toString();
    }
}
