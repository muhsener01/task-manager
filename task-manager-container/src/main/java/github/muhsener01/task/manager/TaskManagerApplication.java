package github.muhsener01.task.manager;

import github.muhsener01.task.manager.application.cli.TaskManagerShell;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TaskManagerApplication.class , args)));
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context, CommandLine commandLine) {
        return (args -> {
            int exitCode = commandLine.execute(args);
            System.exit(exitCode);
        });
    }


    @Bean
    public CommandLine commandLine(TaskManagerShell shell, CommandLine.IFactory factory) {
        return new CommandLine(shell, factory);
    }

}
