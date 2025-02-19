package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestConfig.class)
public class ListTasksCommandTest {

    @Mock
    TaskApplicationService service;

    @InjectMocks
    ListTasksCommand command;

    CommandLine commandLine;


    ByteArrayOutputStream outputStream;


    @BeforeEach
    void setUp() {
        commandLine = new CommandLine(command);
        outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setOut(new PrintStream(outputStream));
    }


    @AfterEach
    void clean() {
        System.setOut(System.out);
        System.setErr(System.err);
    }


    @Test
    void givenNoWithout_whenRun_thenCallServiceMethod() {
        commandLine.execute();
        Mockito.verify(service, Mockito.times(1)).getAll();
    }

    @Test
    void givenExtraParameter_whenRun_thenOutputsUnmatchedArgumentError() {


        commandLine.execute("as");

        String output = outputStream.toString().trim();

        assertTrue(output.startsWith("Unmatched argument at index 0"));

        Mockito.verify(service, Mockito.times(0)).getAll();


    }

}
