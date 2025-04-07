package org.example.exception;

public class WorkspaceUnavailableException extends RuntimeException{
    public WorkspaceUnavailableException(String message) {
        super(message);
    }
}
