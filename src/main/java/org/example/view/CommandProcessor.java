package org.example.view;

import org.example.controller.AdminController;
import org.example.controller.CustomerController;
import org.example.exception.*;
import org.example.model.*;
import org.example.repository.DataStorageSerialization;

import java.util.List;

public class CommandProcessor {
    private final AdminController adminController;
    private final CustomerController customerController;

    private CommandProcessor() {
        adminController = AdminController.getInstance();
        customerController = CustomerController.getInstance();
    }

    public static CommandProcessor getInstance() {
        return CommandProcessorHolder.COMMAND_PROCESSOR;
    }

    public void processCommand(final int commandId, final ConsoleView view) {
        switch (view.getLevel()) {
            case MAIN_MENU -> processMainMenu(commandId, view);
            case ADMIN_MENU -> processAdminMenu(commandId, view);
            case CUSTOMER_MENU -> processUserMenu(commandId, view);
            default -> System.out.println(Message.WRONG_INPUT.getMessage());
        }
    }

    private void processMainMenu(int commandId, ConsoleView view) {
        try {
            switch (commandId) {
                case 1 -> view.setLevel(Level.ADMIN_MENU);
                case 2 -> view.setLevel(Level.CUSTOMER_MENU);
                default -> throw new InvalidCommandException("Invalid command ID: " + commandId);
            }
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processAdminMenu(int commandId, ConsoleView view) {
        try {
            switch (commandId) {
                case -1 -> view.setLevel(Level.MAIN_MENU);
                case 1 -> view.setLevel(Level.ADD_WORKSPACE);
                case 2 -> view.setLevel(Level.REMOVE_WORKSPACE);
                case 3 -> view.setLevel(Level.VIEW_ALL);
                default -> throw new InvalidCommandException("Invalid command ID: " + commandId);
            }
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processUserMenu(int commandId, ConsoleView view) {
        try {
            switch (commandId) {
                case -1 -> view.setLevel(Level.MAIN_MENU);
                case 1 -> view.setLevel(Level.BROWSE_SPACES);
                case 2 -> view.setLevel(Level.MAKE_RESERVATION);
                case 3 -> view.setLevel(Level.VIEW_RESERVATIONS);
                case 4 -> view.setLevel(Level.CANCEL_RESERVATION);
            }
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processAddWorkspace(final String type, final int price) {
        try {
            adminController.addWorkspace(type, price);
        } catch (InvalidRoomTypeException | InvalidPriceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processRemoveWorkspace(final int id) {
        try {
            adminController.removeWorkspace(id);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Reservation> processViewReservations() {
        try {
            return adminController.getAllReservations();
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public List<Workspace> processBrowseAvailableSpaces() {
        try {
            return customerController.getAvailableWorkspaces();
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public void processReservation(final Customer customer, final int idWorkspace, final String start, final String end) {
        try {
            customerController.makeReservation(customer, idWorkspace, start, end);
        } catch (WorkspaceUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Reservation> viewMyReservations(final int id) {
        try {
            return customerController.getReservationsByUserId(id);
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public void cancelReservation(final int id) {
        try {
            customerController.cancelReservation(id);
        } catch (InvalidWorkspaceReservation e) {
            System.out.println(e.getMessage());
        }
    }

    private static class CommandProcessorHolder {
        private static final CommandProcessor COMMAND_PROCESSOR = new CommandProcessor();
    }
}
