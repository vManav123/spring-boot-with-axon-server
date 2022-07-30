package com.example.productservice.global.handler;

import com.example.productservice.command.api.exception.ProductNotFoundException;
import com.example.productservice.global.data.model.rest.ErrorResponse;
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        ProductNotFoundException.class, AxonServerRemoteCommandHandlingException.class
    })
    public ResponseEntity<ErrorResponse> onProductNotFoundException(Exception ex, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setStatus(NO_CONTENT);
        errorResponse.setCode(NO_CONTENT.value());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
    }
}
