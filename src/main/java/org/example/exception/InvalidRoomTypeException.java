package org.example.exception;

public class InvalidRoomTypeException extends RuntimeException{
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}
