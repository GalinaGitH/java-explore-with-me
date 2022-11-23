package ru.practicum.ewm.event.exception;

public class InAppropriateStatusException extends RuntimeException {
    public InAppropriateStatusException(String message) {
        super(message);
    }
}
