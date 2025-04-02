package org.example.view;

import org.example.Main;
import org.example.model.Command;
import org.example.model.Level;
import org.example.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ConsoleView {
    private Level level;

    private ConsoleView() {
        level = Level.MAIN_MENU;
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
                showMenu();

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

    private void showMenu() {
        switch (level) {
            case MAIN_MENU -> System.out.println(Message.GREETING.getMessage());
            case ADMIN_MENU -> System.out.println(Message.ADMIN.getMessage());
            case USER_MENU -> System.out.println(Message.USER.getMessage());
        }

        Map<Level, Command[]> commandsOnLevel = new HashMap<>();
        commandsOnLevel.put(Level.MAIN_MENU, new Command[]{Command.ADMIN_LOGIN, Command.USER_LOGIN, Command.EXIT});
        commandsOnLevel.put(Level.ADMIN_MENU, new Command[]{Command.ADD, Command.REMOVE, Command.VIEW_ALL, Command.EXIT, Command.ROLLBACK});
        commandsOnLevel.put(Level.USER_MENU, new Command[]{Command.BROWSE, Command.RESERVE, Command.VIEW, Command.CANCEL, Command.EXIT, Command.ROLLBACK});

        for (Command command : commandsOnLevel.get(level)) {
            System.out.println(command.getCommand());
        }
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
