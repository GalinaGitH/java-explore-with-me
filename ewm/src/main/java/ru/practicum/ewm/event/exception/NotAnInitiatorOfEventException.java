package ru.practicum.ewm.event.exception;

public class NotAnInitiatorOfEventException extends RuntimeException {
    public NotAnInitiatorOfEventException(String message) {
        super(message);
    }
}
