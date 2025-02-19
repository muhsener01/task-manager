package github.muhsener01.task.manager.application.cli.adapter;


import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.exception.TaskNotFoundException;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.InvalidDomainException;
import github.muhsener01.task.manager.domain.core.exception.ValidationError;
import github.muhsener01.task.manager.domain.core.valueobject.Description;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.domain.core.valueobject.TaskStatus;
import github.muhsener01.task.manager.domain.core.valueobject.Title;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskPresenterImpl.class)
public class TaskPresenterImplTest {

    @Autowired
    TaskPresenterImpl taskPresenter;

    ByteArrayOutputStream outputStream;


    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }

    @AfterEach
    void afterEach() {
        System.setOut(System.out);
        System.setErr(System.err);
    }


    @Test
    void testPresenterInjected() {
        assertNotNull(taskPresenter);
    }

    @Test
    void givenTaskObject_whenPresentTaskCreated_OutputsSuccessMessage() {


        Task task = buildTask();
        taskPresenter.presentTaskCreated(task);


        String output = outputStream.toString().trim();
        assertEquals("Task added successfully: " + task.getId().val(), output);


    }


    @Test
    void givenTaskDetails_whenPresentTaskFound_thenOutputsTaskDetails() {
        LocalDateTime now = LocalDateTime.now();
        TaskDetailsDTO taskDetails = getTaskDetails(buildTask(), now, now);


        taskPresenter.presentTaskFound(taskDetails);

        String output = outputStream.toString().trim();

        assertEquals(prepareOutputMessageForFoundTask(taskDetails), output);


    }


    @Test
    void givenAnException_whenPresentError_thenOutputsUnknownInternalError() {
        Exception exception = new RuntimeException("message");
        taskPresenter.presentError(exception);

        String output = outputStream.toString().trim();

        assertTrue(output.contains("Unknown internal error"));

    }


    @Test
    void givenNotFoundException_WhenPresentNotFoundException_thenOutputsMessageOfException() {
        String title = "Do something";
        TaskNotFoundException taskNotFoundException = new TaskNotFoundException("title", title);


        taskPresenter.presentNotFoundException(taskNotFoundException);

        String output = outputStream.toString();

        assertTrue(output.contains("No such [Task] found with provided [title]: [Do something]"));


    }


    @Test
    void givenInvalidDomainException_whenPresentInvalidDomainException_thenOutputsAnErrorMessageInformingAboutViolations() {
        ValidationError validationError = new ValidationError("book", "isbn", "invalid ISBN");
        ValidationError validationError2 = new ValidationError("author", "name", "invalid author name");
        InvalidDomainException invalidDomainException = new InvalidDomainException("Book", List.of(validationError, validationError2));


        taskPresenter.presentInvalidDomainError(invalidDomainException);


        String output = outputStream.toString();

        String expect = "[ERROR]: [book][isbn] invalid ISBN\n[ERROR]: [author][name] invalid author name";


        // because of logs we cannot obtain exact matching.
        assertTrue(output.contains(expect));


    }

    private Task buildTask() {
        return Task.builder()
                .id(TaskId.of(UUID.randomUUID()))
                .title(Title.of("test"))
                .description(Description.of("test"))
                .status(TaskStatus.DONE)
                .build();
    }

    private TaskDetailsDTO getTaskDetails(Task task, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new TaskDetailsDTO() {
            @Override
            public UUID getId() {
                return task.getId().val();
            }

            @Override
            public String getTitle() {
                return task.getTitle().val();
            }

            @Override
            public String getDescription() {
                return task.getDescription().val();
            }

            @Override
            public String getStatus() {
                return task.getStatus().name();
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }
        };
    }

    private String prepareOutputMessageForFoundTask(TaskDetailsDTO taskDetailsDTO) {
        String template = "ID: %s%nTitle: %s%nDescription: %s%nStatus: %s%nCreated at: %s%nUpdated at: %s";
        return template.formatted(taskDetailsDTO.getId(),
                taskDetailsDTO.getTitle(),
                taskDetailsDTO.getDescription(),
                taskDetailsDTO.getStatus(),
                taskDetailsDTO.getCreatedAt() == null ? null : taskDetailsDTO.getCreatedAt().toString(),
                taskDetailsDTO.getUpdatedAt() == null ? null : taskDetailsDTO.getUpdatedAt().toString());
    }
}
