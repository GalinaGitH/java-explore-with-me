package ru.practicum.ewm.user.exception;


public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
