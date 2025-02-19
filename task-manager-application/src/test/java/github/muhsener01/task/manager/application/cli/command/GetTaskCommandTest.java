package github.muhsener01.task.manager.application.cli.command;


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
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = TestConfig.class)
public class GetTaskCommandTest {

    @Mock
    TaskApplicationService service;

    @InjectMocks
    GetTaskCommand command;

    CommandLine commandLine ;


    @BeforeEach
    void setUp(){
        commandLine = new CommandLine(command);
    }

    @Test
    void givenProperUUID_whenRun_thenCallsApplicationServiceMethodWithProperId() {
        UUID uuid = UUID.randomUUID();

        commandLine.execute("-i=" + uuid);
        Mockito.verify(service,Mockito.times(1)).getById(uuid);
    }


    @Test
    void givenBrokenUUID_whenRun_thenNotCallsApplicationServiceAndOutputErrorMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));


        String brokenId = "asdasd";

        commandLine.execute("-i=" + brokenId);


        Mockito.verify(service,Mockito.times(0)).getById(any(UUID.class));
        assertTrue(outputStream.toString().startsWith("Invalid value for option '--id': cannot convert '%s' to UUID".formatted(brokenId)) );

        System.setErr(System.err);

    }
}
