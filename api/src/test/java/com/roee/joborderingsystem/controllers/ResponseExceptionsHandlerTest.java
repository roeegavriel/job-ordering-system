package com.roee.joborderingsystem.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ResponseExceptionsHandlerTest {

    private final ResponseExceptionsHandler responseExceptionsHandler = new ResponseExceptionsHandler();

    @Test
    @DisplayName("handleIllegalArgumentException returns BAD_REQUEST")
    void handleIllegalArgumentException() {
        String exceptionMessage = "magicalExceptionMessage";
        IllegalArgumentException exception = new IllegalArgumentException(exceptionMessage);

        ResponseEntity<Object> responseEntity = responseExceptionsHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("ConstraintViolationException returns BAD_REQUEST")
    void handleConstraintViolationException() {
        ClassForValidation classForValidation = ClassForValidation.builder().notNullString(null).email("notAnEmail").intWithMax100(101).build();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ClassForValidation>> validateErrors = validator.validate(classForValidation);
        String exceptionMessage = "magicalExceptionMessage";
        ConstraintViolationException exception = new ConstraintViolationException(exceptionMessage, validateErrors);

        ResponseEntity<Object> responseEntity = responseExceptionsHandler.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        List<String> errorsParts = Arrays.asList(((String) responseEntity.getBody()).split("\n"));
        assertAll(
            () -> assertTrue(errorsParts.contains("Validation errors:")),
            () -> assertTrue(errorsParts.contains("  -> Object: ClassForValidation, Field: notNullString, Error: must not be null")),
            () -> assertTrue(errorsParts.contains("  -> Object: ClassForValidation, Field: intWithMax100, Error: must be less than or equal to 100")),
            () -> assertTrue(errorsParts.contains("  -> Object: ClassForValidation, Field: email, Error: must match \"^[\\w-\\+\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$\""))
        );
    }

    @Test
    @DisplayName("handleNoSuchElementException returns NOT_FOUND")
    void handleNoSuchElementException() {
        String exceptionMessage = "magicalExceptionMessage";
        NoSuchElementException exception = new NoSuchElementException(exceptionMessage);

        ResponseEntity<Object> responseEntity = responseExceptionsHandler.handleNoSuchElementException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody());
    }

    @Test
    @DisplayName("handleGeneralException returns INTERNAL_SERVER_ERROR")
    void handleGeneralException() {
        String exceptionMessage = "magicalExceptionMessage";
        Exception exception = new Exception(exceptionMessage);

        ResponseEntity<Object> responseEntity = responseExceptionsHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody());
    }

    @Data
    @Builder
    private static class ClassForValidation {

        @NotNull
        private String notNullString;

        @Pattern(regexp = "^[\\w-\\+\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        private String email;

        @Max(value = 100)
        private int intWithMax100;
    }
}
