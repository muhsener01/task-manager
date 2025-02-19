package github.muhsener01.task.manager.application.cli.command;


import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {TestConfig.class})
public class PicocliCommandLineTest {

    @Mock
    TaskApplicationService service;

    @InjectMocks
    AddTaskCommand command;

    CommandLine commandLine;

    @BeforeEach
    void setup() {
        commandLine = new CommandLine(command);
    }

    @Test
    void testCommandLine() {
        assertNotNull(commandLine);
    }

    @Test
    void givenProperArguments_whenRun_thenCallsApplicationService() {
        int exitCode = commandLine.execute("--title=title");
        assertEquals(0, exitCode);
        Mockito.verify(service, Mockito.times(1)).createTask(any(CreateTaskCommand.class));
    }

    @Test
    void givenMissingTitle_whenRun_thenExistsWithCode2AndNotCallsApplicationServiceAndOutputsMessageStartingWithMissingRequiredOption() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));

        int exitCode = commandLine.execute("-d=falanfilan");
        String output = outputStream.toString().trim();



        assertEquals(2, exitCode);
        Mockito.verify(service, Mockito.times(0)).createTask(any(CreateTaskCommand.class));
        assertTrue(output.startsWith("Missing required option: '--title=<title>'"));

        System.setErr(System.err);
    }


}
