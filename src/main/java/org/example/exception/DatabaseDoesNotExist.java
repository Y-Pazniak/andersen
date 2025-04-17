package org.example.exception;

public class DatabaseDoesNotExist extends Exception{
    public DatabaseDoesNotExist(String message) {
        super(message);
    }
}
