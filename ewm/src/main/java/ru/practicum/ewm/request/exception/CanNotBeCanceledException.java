package ru.practicum.ewm.request.exception;

public class CanNotBeCanceledException extends RuntimeException {
    public CanNotBeCanceledException(String message) {
        super(message);
    }
}

