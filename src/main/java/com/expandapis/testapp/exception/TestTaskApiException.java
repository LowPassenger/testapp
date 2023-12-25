package com.expandapis.testapp.exception;

import org.springframework.http.HttpStatus;

public class TestTaskApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public TestTaskApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public TestTaskApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
