package github.muhsener01.task.manager.application.cli.adapter;

import java.util.List;

public abstract class AbstractCLIPresenter {

    public static final  String ERROR_MESSAGE_TEMPLATE = "[ERROR]: %s";

    public void printSuccessMessageAndExit(String message, int exitCode) {
        System.out.println(message);
        System.exit(exitCode);
    }


    public void printErrorMessageAndExit(List<String> messages, int exitCode) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            sb.append(ERROR_MESSAGE_TEMPLATE.formatted(messages.get(i)));

            if (i != messages.size() - 1)
                sb.append("\n");
        }

        printErrorMessageAndExit(sb.toString(), exitCode);
    }



    private void printErrorMessageAndExit(String message, int exitCode) {
        System.err.println(message);
        System.exit(exitCode);
    }

}
