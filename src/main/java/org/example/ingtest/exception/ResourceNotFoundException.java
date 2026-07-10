package org.example.ingtest.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, UUID id) {
        super(String.format("%s with id %s has not been found", entityName, id));
    }
}
