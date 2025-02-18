package github.muhsener01.task.manager.application.cli;

import github.muhsener01.task.manager.application.cli.command.TaskManagementCommand;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "" ,version = "0.1", mixinStandardHelpOptions = true, subcommands = {TaskManagementCommand.class})
public class TaskManagerShell {
}
