package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.UUID;

@Component
@CommandLine.Command(name = "get", mixinStandardHelpOptions = true, description = "get a specific task")
public class GetTaskCommand implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(GetTaskCommand.class);
    @CommandLine.Option(names = {"-i", "--id"}, description = "ID of task", required = true)
    private UUID id;

    private final TaskApplicationService taskApplicationService;

    public GetTaskCommand(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    @Override
    public void run() {
        log.info("[COMMAND]: task get --id='{}'", id);
        taskApplicationService.getById(id);
    }
}
