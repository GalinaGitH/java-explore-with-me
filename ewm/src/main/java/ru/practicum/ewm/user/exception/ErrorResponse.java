package ru.practicum.ewm.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private List<String> errors;
    private String message;
    private HttpStatus status;
}
