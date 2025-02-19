package github.muhsener01.task.manager.application.cli.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component()
@CommandLine.Command(name = "task", mixinStandardHelpOptions = true,
        description = "Task management commands", version = "0.1", subcommands = {AddTaskCommand.class,GetTaskCommand.class , ListTasksCommand.class})
public class TaskManagementCommand {
}
