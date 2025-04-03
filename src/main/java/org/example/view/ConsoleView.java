package org.example.view;

import org.example.controller.AdminController;
import org.example.controller.CustomerController;
import org.example.model.*;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleView {
    private Level level;
    private Customer currentCustomer;

    private ConsoleView() {
        level = Level.MAIN_MENU;
        currentCustomer = new Customer();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public static ConsoleView getInstance() {
        return ConsoleViewHolder.CONSOLE_VIEW;
    }

    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                showMenu(reader);

                int commandId = readCommand(reader);
                if (commandId == 0) {
                    break;
                }

                CommandProcessor.getInstance().processCommand(commandId, this);
            }
        } catch (IOException e) {
            System.out.println(Message.WRONG_INPUT.getMessage());
            e.printStackTrace();
        }
        System.out.println("bye \uD83D\uDC8B");
    }

    private void showMenu(final BufferedReader bufferedReader) throws IOException {
        switch (level) {
            case MAIN_MENU -> requestUserType();

            case ADMIN_MENU -> requestAdminCommand();
            case ADD_WORKSPACE -> requestWorkspaceAdd(bufferedReader);
            case REMOVE_WORKSPACE -> requestWorkspaceRemove(bufferedReader);
            case VIEW_ALL -> requestAllReservations(bufferedReader);

            case CUSTOMER_MENU -> requestCustomerCommand();
            case BROWSE_SPACES -> requestAvailableSpaces(bufferedReader);
            case MAKE_RESERVATION -> requestReservationDetails(bufferedReader);
            case VIEW_RESERVATIONS -> requestMyReservations(bufferedReader);
            case CANCEL_RESERVATION -> cancelMyReservation(bufferedReader);

        }
    }

    private void requestUserType() {
        System.out.println(Message.GREETING.getMessage());
        System.out.println(Command.ADMIN_LOGIN.getCommand());
        System.out.println(Command.USER_LOGIN.getCommand());
        System.out.println(Command.EXIT.getCommand());
    }

    private void requestAdminCommand() {
        System.out.println(Message.ADMIN.getMessage());
        System.out.println(Command.ADD.getCommand());
        System.out.println(Command.REMOVE.getCommand());
        System.out.println(Command.VIEW_ALL.getCommand());
        System.out.println(Command.EXIT.getCommand());
        System.out.println(Command.ROLLBACK.getCommand());
    }

    private void requestWorkspaceRemove(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Message.REMOVE.getMessage());
        System.out.println(Command.EXIT.getCommand());
        System.out.println(Command.ROLLBACK.getCommand());

        try {
            AdminController.getInstance().removeWorkspace(Integer.parseInt(bufferedReader.readLine()));
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }

        WorkspaceService.getInstance().getAllWorkspaces().forEach(System.out::println);

        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    public void requestWorkspaceAdd(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Command.WORKSPACE_TYPE_REQUEST.getCommand());
        String type = bufferedReader.readLine();

        System.out.println(Command.WORKSPACE_PRICE_REQUEST.getCommand());
        int price = Integer.parseInt(bufferedReader.readLine());

        AdminController.getInstance().addWorkspace(type, price);

        WorkspaceService.getInstance().getAllWorkspaces().forEach(System.out::println);
        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    private void requestAllReservations(final BufferedReader bufferedReader) throws IOException {
        List<Reservation> reservations = AdminController.getInstance().getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println(Message.EMPTY.getMessage());
        } else {
            reservations.forEach(System.out::println);
        }

        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    private void requestReservationDetails(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Message.ID_WORKSPACE_REQUEST.getMessage());
        int idSpace = Integer.parseInt(bufferedReader.readLine());

        System.out.println(Message.FROM.getMessage());
        String from = bufferedReader.readLine();

        System.out.println(Message.TILL.getMessage());
        String till = bufferedReader.readLine();

        ReservationService.getInstance().makeReservation(currentCustomer, idSpace, from, till);

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void requestMyReservations(final BufferedReader bufferedReader) throws IOException {
        List<Reservation> reservations = CustomerController.getInstance().getReservationByUserId(currentCustomer.getId());
        if (reservations.isEmpty()) {
            System.out.println(Message.EMPTY);
        } else {
            reservations.forEach(System.out::println);
        }

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void cancelMyReservation(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Message.ID_WORKSPACE_REQUEST.getMessage());
        CustomerController.getInstance().cancelReservation(Integer.parseInt(bufferedReader.readLine()));

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void requestCustomerCommand() {
        CustomerController.getInstance().addCustomer();
        System.out.println(Message.CUSTOMER.getMessage());
        System.out.println(Command.BROWSE.getCommand());
        System.out.println(Command.RESERVE.getCommand());
        System.out.println(Command.VIEW.getCommand());
        System.out.println(Command.CANCEL.getCommand());
        System.out.println(Command.EXIT.getCommand());
        System.out.println(Command.ROLLBACK.getCommand());
    }

    private void requestAvailableSpaces(final BufferedReader bufferedReader) throws IOException {
        List<Workspace> workspaces = CustomerController.getInstance().getAvailableWorkspaces();
        if (workspaces.isEmpty()) {
            System.out.println(Message.EMPTY.getMessage());
        } else {
            workspaces.forEach(System.out::println);
        }

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private int readCommand(final BufferedReader bufferedReader) {
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println(Message.WRONG_INPUT.getMessage());
            return -12345;
        }
    }

    private static class ConsoleViewHolder {
        private static final ConsoleView CONSOLE_VIEW = new ConsoleView();
    }
}
