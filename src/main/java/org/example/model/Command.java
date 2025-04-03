package org.example.model;

import java.util.Arrays;

public enum Command {
    ADMIN_LOGIN("1 - admin login", 1),
    USER_LOGIN("2 - user login", 2),
    EXIT("0 - exit", 0),
    ROLLBACK("-1 - back", -1),

    ADD("1 - add a new coworking space", 3),
    REMOVE("2 - remove a coworking space", 4),
    VIEW_ALL("3 - view all reservations", 5),

    BROWSE ("1 - browse available spaces", 6),
    RESERVE ("2 - make a reservation", 7),
    VIEW ("3 - view my reservations", 8),
    CANCEL("4 - cancel a reservation", 9),


    WORKSPACE_TYPE_REQUEST("input type of workspace available types: " + Arrays.toString(Type.values()), -2),
    WORKSPACE_PRICE_REQUEST("input price: ", -2), ;

    private final int id;
    private final String command;

    Command(final String command, final int id) {
        this.command = command;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getCommand() {
        return this.command;
    }
}
