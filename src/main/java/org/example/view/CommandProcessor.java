package org.example.view;

import org.example.model.Level;
import org.example.model.Message;

public class CommandProcessor {
    private CommandProcessor() {
    }

    public static CommandProcessor getInstance() {
        return CommandProcessorHolder.COMMAND_PROCESSOR;
    }

    public void processCommand(final int commandId, final ConsoleView view) {
        switch (view.getLevel()) {
            case MAIN_MENU -> processMainMenu(commandId, view);
            case ADMIN_MENU -> processAdminMenu(commandId, view);
            case USER_MENU -> processUserMenu(commandId, view);

            default -> System.out.println(Message.WRONG_INPUT.getMessage());
        }
    }

    private void processMainMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case 1 -> view.setLevel(Level.ADMIN_MENU);
            case 2 -> view.setLevel(Level.USER_MENU);
        }
    }

    private void processAdminMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case -1 -> view.setLevel(Level.MAIN_MENU);
            default -> System.out.println("Awesome admin logic is here");
        }
    }

    private void processUserMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case -1 -> view.setLevel(Level.MAIN_MENU);
            default -> System.out.println("Awesome user logic is here");
        }
    }

    private static class CommandProcessorHolder {
        private static final CommandProcessor COMMAND_PROCESSOR = new CommandProcessor();
    }
}
