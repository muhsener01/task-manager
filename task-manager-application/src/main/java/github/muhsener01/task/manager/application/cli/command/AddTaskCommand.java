package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "Add a new task", mixinStandardHelpOptions = true , requiredOptionMarker = '*' )
@Component
public class AddTaskCommand implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(AddTaskCommand.class);
    @CommandLine.Option(names = {"-t" , "--title"} , description = "Title of the task" , required = true)
    private String title;

    @CommandLine.Option(names = {"-d" , "--description"} , description = "Description of the task")
    private String description;

    private final TaskApplicationService taskApplicationService;

    public AddTaskCommand(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    @Override
    public void run() {
        log.info("[COMMAND EXECUTED]: task add --title='{}' --description='{}'", title, description);
        CreateTaskCommand command = new CreateTaskCommand(title, description);

        taskApplicationService.createTask(command);
    }
}
