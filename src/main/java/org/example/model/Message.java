package org.example.model;

public enum Message {
    GREETING("Choose your role: "),
    ADMIN("Choose your admin business"),
    CUSTOMER("Choose your user business"),
    WRONG_INPUT("Wrong input, try again"),
    SUCCESSFUL("Success!"),
    NOT_SUCCESSFUL("Not success"),
    EMPTY("Empty"),
    REMOVE("Enter id to remove workspace"),
    FROM("From when"),
    TILL("Till"),
    ID_WORKSPACE_REQUEST("Enter ID of the workspace"),
    ID_USER_REQUEST("Enter your ID");

    private final String message;

    private Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
