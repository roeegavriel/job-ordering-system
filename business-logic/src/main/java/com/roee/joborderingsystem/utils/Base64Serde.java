package com.roee.joborderingsystem.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class Base64Serde {

    private final ObjectMapper objectMapper;

    public Base64Serde() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> String serialize(T object) {
        try {
            byte[] valueAsBytes = objectMapper.writeValueAsBytes(object);
            return Base64.getEncoder().encodeToString(valueAsBytes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("failed to serialize object", e);
        }
    }

    public <T> T deserialize(String value, Class<T> clazz) {
        try {
            byte[] valueAsBytes = Base64.getDecoder().decode(value);
            return objectMapper.readValue(valueAsBytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException("failed to deserialize object", e);
        }
    }
}
