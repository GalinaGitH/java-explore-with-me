package ru.practicum.ewm.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private HttpStatus status;
}
