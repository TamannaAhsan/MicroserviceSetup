package com.tamanna.apigateway.exception;

import org.springframework.http.HttpStatus;

public class ApiParameterResolveException extends ApiException {

    private static final long serialVersionUID = 7729045683866048832L;

    public ApiParameterResolveException() {
    }

    ApiParameterResolveException(String message) {
        super(message);
    }

    @Override
    HttpStatus getServiceStatus() {
        return null;
    }
}
