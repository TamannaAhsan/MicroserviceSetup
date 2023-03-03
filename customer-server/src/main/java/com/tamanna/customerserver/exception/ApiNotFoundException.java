package com.tamanna.customerserver.exception;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException {

    public ApiNotFoundException() {
    }

    /**
     * @param message
     */
    public ApiNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ApiNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ApiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    HttpStatus getServiceStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
