package org.example.ingtest.exception;

import lombok.Getter;

@Getter
public class TooManyAttemptsException extends RuntimeException {

    private final long retryAfterSeconds;

    public TooManyAttemptsException(long retryAfterSeconds) {
        super("Too many failed login attempts");
        this.retryAfterSeconds = retryAfterSeconds;
    }
}
