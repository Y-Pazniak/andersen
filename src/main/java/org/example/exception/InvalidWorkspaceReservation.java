package org.example.exception;

public class InvalidWorkspaceReservation extends RuntimeException{
    public InvalidWorkspaceReservation(String message) {
        super(message);
    }
}
