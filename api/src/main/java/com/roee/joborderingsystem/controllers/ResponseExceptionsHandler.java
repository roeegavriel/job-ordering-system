package com.roee.joborderingsystem.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;

@ControllerAdvice
@Log4j2
public class ResponseExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(Exception e) {
        logThrowable(e, log::debug);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        logThrowable(e, log::debug);
        StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations != null) {
            constraintViolations.forEach(constraintViolation -> {
                errorMessage.append("  -> Object: " + constraintViolation.getRootBeanClass().getSimpleName());
                errorMessage.append(", Field: " + constraintViolation.getPropertyPath());
                errorMessage.append(", Error: " + constraintViolation.getMessage());
                errorMessage.append("\n");
            });
        }
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleNoSuchElementException(Exception e) {
        logThrowable(e, log::debug);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGeneralException(Exception e) {
        logThrowable(e, log::error);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logThrowable(Throwable t, BiConsumer<String, Throwable> logger) {
        String exceptionName = t.getClass().getSimpleName();
        logger.accept("failed to process request, exception: " + exceptionName, t);
    }
}
