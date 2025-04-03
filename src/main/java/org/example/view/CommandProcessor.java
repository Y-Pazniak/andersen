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
            case ADD_WORKSPACE -> processAddWorkspace(commandId, view);
            case REMOVE_WORKSPACE -> processRemoveWorkspace(commandId, view);
            case VIEW_ALL -> processViewReservations(commandId, view);

            case CUSTOMER_MENU -> processUserMenu(commandId, view);
            case MAKE_RESERVATION -> processReservation(commandId, view);
            case VIEW_RESERVATIONS -> viewMyReservations(commandId, view);
            case CANCEL_RESERVATION -> cancelReservation(commandId, view);
            default -> System.out.println(Message.WRONG_INPUT.getMessage());
        }
    }

    private void processMainMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case 1 -> view.setLevel(Level.ADMIN_MENU);
            case 2 -> view.setLevel(Level.CUSTOMER_MENU);
        }
    }

    private void processAdminMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case -1 -> view.setLevel(Level.MAIN_MENU);
            case 1 -> view.setLevel(Level.ADD_WORKSPACE);
            case 2 -> view.setLevel(Level.REMOVE_WORKSPACE);
            case 3 -> view.setLevel(Level.VIEW_ALL);
        }
    }

    private void processUserMenu(int commandId, ConsoleView view) {
        switch (commandId) {
            case -1 -> view.setLevel(Level.MAIN_MENU);
            case 1 -> view.setLevel(Level.BROWSE_SPACES);
            case 2 -> view.setLevel(Level.MAKE_RESERVATION);
            case 3 -> view.setLevel(Level.VIEW_RESERVATIONS);
            case 4 -> view.setLevel(Level.CANCEL_RESERVATION);
        }
    }

    private void processAddWorkspace(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.ADMIN_MENU);
        }
    }

    private void processRemoveWorkspace(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.ADMIN_MENU);
        }
    }

    private void processViewReservations(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.ADMIN_MENU);
        }
    }

    private void processReservation(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.CUSTOMER_MENU);
        }
    }

    private void viewMyReservations(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.CUSTOMER_MENU);
        }
    }

    private void cancelReservation(int commandId, ConsoleView view) {
        if (commandId == -1 || commandId == 12345) {
            view.setLevel(Level.CUSTOMER_MENU);
        }
    }

    private static class CommandProcessorHolder {
        private static final CommandProcessor COMMAND_PROCESSOR = new CommandProcessor();
    }
}
