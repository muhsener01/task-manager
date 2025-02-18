package github.muhsener01.task.manager.application.cli.command;

import github.muhsener01.task.manager.domain.application.dto.CreateTaskCommand;
import github.muhsener01.task.manager.domain.application.ports.input.TaskApplicationService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "Add a new task", mixinStandardHelpOptions = true , requiredOptionMarker = '*' )
@Component
public class AddTaskCommand implements Runnable{

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
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        CreateTaskCommand command = new CreateTaskCommand(title , description);
        taskApplicationService.createTask(command);
    }
}
