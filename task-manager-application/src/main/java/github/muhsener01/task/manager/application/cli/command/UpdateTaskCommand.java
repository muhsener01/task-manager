package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.dto.TaskUpdateCommand;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.*;

@Component
@CommandLine.Command(name = "update", description = "updates task", mixinStandardHelpOptions = true, requiredOptionMarker = '*')
public class UpdateTaskCommand implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(UpdateTaskCommand.class);
    @CommandLine.Option(names = {"--id", "-i"}, required = true, description = "Id of tasks")
    private UUID id;

    @CommandLine.Option(names = {"--title", "-t"}, description = "new title")
    private String title;
    @CommandLine.Option(names = {"--description", "-d"}, description = "new description")
    private String description;
    @CommandLine.Option(names = {"--status", "-s"}, description = "new status [TODO, IN_PROGRESS, DONE]")
    private String status;


    private final TaskApplicationService service;


    public UpdateTaskCommand(TaskApplicationService service) {
        this.service = service;
    }

    @Override
    public void run() {
        log.info("[COMMAND]: task update --id='{}' --title={}' --description='{}' --status='{}'  ",
                id, title, description, status);
        service.updateTask(new TaskUpdateCommand(id, title, description, status));

    }
}
