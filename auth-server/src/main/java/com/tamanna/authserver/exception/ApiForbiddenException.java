package com.tamanna.authserver.exception;

import org.springframework.http.HttpStatus;

public class ApiForbiddenException extends ApiException {

    public ApiForbiddenException() {
    }

    /**
     * @param message
     */
    public ApiForbiddenException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ApiForbiddenException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ApiForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getServiceStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
