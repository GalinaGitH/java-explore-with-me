package ru.practicum.ewm.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import ru.practicum.ewm.event.exception.ConflictException;
import ru.practicum.ewm.user.exception.EmailNotUniqueException;
import ru.practicum.ewm.user.exception.ErrorResponse;
import ru.practicum.ewm.user.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.PrintWriter;
import java.io.StringWriter;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({NullPointerException.class,
            EntityNotFoundException.class,
            NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        log.error("404 {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String sStackTrace = sw.toString();
        ErrorResponse error = new ErrorResponse(sStackTrace, e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler({ValidationException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            DuplicateKeyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e) {
        log.error("400 {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String sStackTrace = sw.toString();
        ErrorResponse error = new ErrorResponse(sStackTrace, e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler({HttpClientErrorException.Conflict.class,
            ConflictException.class,
            EmailNotUniqueException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(Exception e) {
        log.error("409 {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String sStackTrace = sw.toString();
        ErrorResponse error = new ErrorResponse(sStackTrace, e.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
