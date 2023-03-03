package com.tamanna.apigateway.exception;

import com.tamanna.apigateway.dto.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.tamanna.apigateway.constants.AppConstant.ERROR;
import static com.tamanna.apigateway.constants.AppConstant.STATUS;
import static com.tamanna.apigateway.constants.ErrorMessage.TokenInvalid.MESSAGE;

@ControllerAdvice
public class ExceptionCatcher extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionCatcher.class);
    @Autowired
    private MessageSource messageSource;
    private Locale locale = LocaleContextHolder.getLocale();

    public ExceptionCatcher() {
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleExceptions(final ApiException ex, final WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();

        String message;
        try {
            message = messageSource.getMessage(ex.getMessage(), null, locale);
        } catch (Exception e) {
            message = ex.getMessage();
        }
        logger.info(message);

        response.put(STATUS, ex.getServiceStatus().value());
        response.put(MESSAGE, message);
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getServiceStatus(), request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getMessage());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        ErrorMessage errorMessage = new ErrorMessage(errors);

        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, status.value());
        response.put(MESSAGE, errorMessage);
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, status.value());
        response.put(MESSAGE, messageSource.getMessage("required.path.param", new Object[]{ex.getVariableName()}, locale));
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, status.value());
        response.put(MESSAGE, messageSource.getMessage("required.query.param", new Object[]{ex.getParameterName()}, locale));
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, status.value());
        response.put(MESSAGE, ex.getMessage());
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, status.value());
        response.put(MESSAGE, ex.getMessage());
        body.put(ERROR, response);
        return handleExceptionInternal(ex, body, headers, status, request);
    }


}
