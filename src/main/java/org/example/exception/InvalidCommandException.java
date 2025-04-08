package org.example.exception;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String info) {
        super(info);
    }
}
