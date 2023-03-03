package com.tamanna.apigateway.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private String extraMessage;

    public ApiException() {
    }

    /**
     * @param message
     */
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Object extraMessage) {
        super(message);
        this.extraMessage = extraMessage.toString();
    }

    /**
     * @param cause
     */
    public ApiException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getExtraMessage() {
        return this.extraMessage;
    }

    abstract HttpStatus getServiceStatus();

    public String getServiceMessage() {
        return "";
    }
}
