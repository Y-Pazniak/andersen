package org.example.model;

public enum Message {
    GREETING("Choose your role: "), ADMIN ("Choose your admin business"), USER ("Choose your user business"), WRONG_INPUT("Wrong input, try again");

    private final String message;
    private Message(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
