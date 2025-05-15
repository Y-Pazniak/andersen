package org.example.view;

import org.example.controller.CustomerController;
import org.example.model.*;
import org.example.repository.DataStorage;
import org.example.service.WorkspaceService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@Service
public class ConsoleView {
    private Level level;
    private final Customer customer;
    private final CommandProcessor commandProcessor;
    private final WorkspaceService workspaceService;
    private final CustomerController customerController;

    private ConsoleView(CommandProcessor commandProcessor, WorkspaceService workspaceService, CustomerController customerController) {
        level = Level.MAIN_MENU;
        customer = new Customer();
        this.commandProcessor = commandProcessor;
        this.workspaceService = workspaceService;
        this.customerController = customerController;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                showMenu(reader);

                int commandId = readCommand(reader);
                if (commandId == 0) {
                    break;
                }

                commandProcessor.processCommand(commandId, this);
            }
        } catch (IOException | NumberFormatException e) {
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
            case VIEW_ALL -> requestViewAllReservations(bufferedReader);

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
        String readInput = bufferedReader.readLine().trim();

        if (isDigit(readInput)) {
            commandProcessor.processRemoveWorkspace(Integer.parseInt(readInput));
        }
        workspaceService.getAllWorkspaces().forEach(System.out::println);

        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    public void requestWorkspaceAdd(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Command.WORKSPACE_TYPE_REQUEST.getCommand());
        String type = bufferedReader.readLine();

        System.out.println(Command.WORKSPACE_PRICE_REQUEST.getCommand());
        int price = Integer.parseInt(bufferedReader.readLine());

        commandProcessor.processAddWorkspace(type, price);

        workspaceService.getAllWorkspaces().forEach(System.out::println);
        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    private void requestViewAllReservations(final BufferedReader bufferedReader) throws IOException {
        List<Reservation> reservations = commandProcessor.processViewReservations();
        reservations.forEach(System.out::println);

        level = Level.ADMIN_MENU;
        showMenu(bufferedReader);
    }

    private void requestAvailableSpaces(final BufferedReader bufferedReader) throws IOException {
        List<Workspace> workspaces = commandProcessor.processBrowseAvailableSpaces();
        workspaces.forEach(System.out::println);

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void requestReservationDetails(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Message.ID_WORKSPACE_REQUEST.getMessage());
        Long idSpace = (long) Integer.parseInt(bufferedReader.readLine());

        System.out.println(Message.FROM.getMessage());
        String from = bufferedReader.readLine();

        System.out.println(Message.TILL.getMessage());
        String till = bufferedReader.readLine();

        commandProcessor.processReservation(customer, idSpace, from, till);

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void requestMyReservations(final BufferedReader bufferedReader) throws IOException {
        List<Reservation> reservations = commandProcessor.viewMyReservations(customer.getId());
        reservations.forEach(System.out::println);

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void cancelMyReservation(final BufferedReader bufferedReader) throws IOException {
        System.out.println(Message.ID_WORKSPACE_REQUEST.getMessage());
        String readInpupt = bufferedReader.readLine().trim();
        if (isDigit(readInpupt)) {
            commandProcessor.cancelReservation((long) Integer.parseInt(readInpupt));
        }

        level = Level.CUSTOMER_MENU;
        showMenu(bufferedReader);
    }

    private void requestCustomerCommand() {
        customerController.addCustomer();
        System.out.println(Message.CUSTOMER.getMessage());
        System.out.println(Command.BROWSE.getCommand());
        System.out.println(Command.RESERVE.getCommand());
        System.out.println(Command.VIEW.getCommand());
        System.out.println(Command.CANCEL.getCommand());
        System.out.println(Command.EXIT.getCommand());
        System.out.println(Command.ROLLBACK.getCommand());
    }

    private boolean isDigit(final String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("Use digits");
            return false;
        }
    }

    private int readCommand(final BufferedReader bufferedReader) {
        try {
            return Integer.parseInt(bufferedReader.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            System.out.println(Message.WRONG_INPUT.getMessage());
            return -12345;
        }
    }
}
