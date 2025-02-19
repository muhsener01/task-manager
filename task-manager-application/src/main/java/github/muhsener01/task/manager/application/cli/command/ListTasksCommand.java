package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "list", aliases = {"ls"}, mixinStandardHelpOptions = true, description = "lists all tasks without pagination")
public class ListTasksCommand implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ListTasksCommand.class);
    private final TaskApplicationService taskApplicationService;


    public ListTasksCommand(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    @Override
    public void run() {
        log.info("[COMMAND]: task list");
        taskApplicationService.getAll();
    }
}
