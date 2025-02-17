package github.muhsener01.task.manager.domain.core.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ValueObjectTest {

    @Test
    void givenTaskId_whenPassValidUUID_thenReturnsValidTaskId() {
        UUID uuid = UUID.randomUUID();


        TaskId taskId = TaskId.of(uuid);

        assertNotNull(taskId);
        assertEquals(uuid, taskId.val());


    }

    @Test
    void givenTaskId_whenPassNullValue_thenThrowsIllegalArgumentException() {
        UUID uuid = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TaskId.of(uuid));
        assertEquals("ID value cannot be null", exception.getMessage());
    }


    @Test
    void givenTitle_whenPassValidValue_thenTitleValueObject() {
        String titleValue = "Task title test-1";
        Title title = Title.of(titleValue);

        assertNotNull(title);
        assertEquals("Task title test-1", title.val());
    }

    @Test
    void givenTitle_whenPassNullArgument_thenThrowsIllegalArgument() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Title.of(null);
        });

        assertNotNull(exception);
        assertEquals("Title value cannot be null", exception.getMessage());
    }


    @Test
    void givenTitleWith200Characters_whenIsLongerThan100_thenReturnsTrue() {
        Title title = Title.of(generateString(200));

        boolean result = title.isLongerThan(100);

        assertTrue(result);
    }

    @Test
    void givenTitleWith200Character_whenIsLongerThan200_thenReturnsFalse() {
        Title title = Title.of(generateString(200));

        boolean result = title.isLongerThan(200);

        assertFalse(result);
    }


    @Test
    void givenBlankTitle_whenIsBlank_thenReturnsTrue() {
        Title title = Title.of("           ");
        boolean result = title.isBlank();
        assertTrue(result);
    }


    @Test
    void givenNonBlankTitle_whenIsBlank_thenReturnsFalse() {
        Title title = Title.of("     1      ");
        boolean result = title.isBlank();
        assertFalse(result);
    }


    @Test
    void givenDescription_whenPassValidArgument_thenReturnsDescriptionValueObject() {
        Description description = Description.of("task description test-1");

        assertNotNull(description);
        assertEquals("task description test-1", description.val());
    }

    @Test
    void givenDescription_whenPassNullArgument_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Description.of(null);
        });

        assertNotNull(exception);
        assertEquals("Description value cannot be null", exception.getMessage());
    }


    @Test
    void givenBlankDescription_whenIsBlank_thenReturnsTrue() {
        Description description = Description.of("");
        boolean result = description.isBlank();

        assertTrue(result);
    }

    @Test
    void givenNonBlankDescription_whenIsBlank_thenReturnsFalse() {
        Description description = Description.of("asd");
        boolean result = description.isBlank();

        assertFalse(result);
    }


    @Test
    void givenDescriptionWith200Characters_whenIsLongerThan200_thenReturnsFalse() {
        Description description = Description.of(generateString(200));

        boolean result = description.isLongerThan(200);
        assertFalse(result);
    }

    @Test
    void givenDescriptionWith200Characters_whenIsLongerThan100_thenReturnsTrue() {
        Description description = Description.of(generateString(200));

        boolean result = description.isLongerThan(100);
        assertTrue(result);
    }


    @Test
    void givenStatusInStringTodo_whenCallOf_thenReturnsTaskStatusTODO() {
        String stringStatus = "todo";

        TaskStatus status = TaskStatus.of(stringStatus);

        assertEquals(TaskStatus.TODO, status);

    }


    @Test
    void givenStatusInWrongString_whenOf_thenThrowsIllegalArgumentException() {
        String stringStatus = "todoXX";
        String stringStatus2 = "asdas";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            TaskStatus.of(stringStatus)
        );


        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
            TaskStatus.of(stringStatus2)
        );


        assertEquals("Unknown status type: '%s'".formatted(stringStatus), exception.getMessage());
        assertEquals("Unknown status type: '%s'".formatted(stringStatus2), exception2.getMessage());
    }


    @Test
    void givenStringInProgressInAllVariants_whenOf_thenReturnsTaskStatusInProgress() {
        String stringStatus1 = "in_progress";
        String stringStatus2 = "in-progress";
        String stringStatus3 = "inproGresS";


        TaskStatus status1 = TaskStatus.of(stringStatus1);
        TaskStatus status2 = TaskStatus.of(stringStatus2);
        TaskStatus status3 = TaskStatus.of(stringStatus3);

        assertEquals(TaskStatus.IN_PROGRESS, status1);
        assertEquals(TaskStatus.IN_PROGRESS, status2);
        assertEquals(TaskStatus.IN_PROGRESS, status3);
    }

    @Test
    void givenStringStatusDone_whenOf_thenReturnsTaskStatusDONE() {
        String stringStatus1 = "done";
        String stringStatus2 = "DONE";
        String stringStatus3 = "   doNe   ";


        TaskStatus status1 = TaskStatus.of(stringStatus1);
        TaskStatus status2 = TaskStatus.of(stringStatus2);
        TaskStatus status3 = TaskStatus.of(stringStatus3);

        assertEquals(TaskStatus.DONE, status1);
        assertEquals(TaskStatus.DONE, status2);
        assertEquals(TaskStatus.DONE, status3);
    }


    String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append("x");


        return sb.toString();
    }


}
