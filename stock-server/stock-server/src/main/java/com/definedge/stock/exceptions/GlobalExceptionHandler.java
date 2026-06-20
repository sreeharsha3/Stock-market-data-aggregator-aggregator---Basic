package com.definedge.stock.exceptions;

import com.definedge.stock.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SymbolNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSymbolNotFound(
            SymbolNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(ex.getMessage(), 404));
    }

    @ExceptionHandler(InvalidTimeframeException.class)
    public ResponseEntity<ErrorResponse> handleTimeframe(
            InvalidTimeframeException ex) {

        return ResponseEntity.badRequest()
                .body(buildError(ex.getMessage(), 400));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex) {

        return ResponseEntity.badRequest()
                .body(buildError(
                        "Missing parameter: " + ex.getParameterName(),
                        400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex) {

        return ResponseEntity.internalServerError()
                .body(buildError(ex.getMessage(), 500));
    }

    private ErrorResponse buildError(
            String message,
            Integer status) {

        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
