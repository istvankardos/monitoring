package com.aldisued.iot.monitoring.exception.handler;

import com.aldisued.iot.monitoring.exception.SensorNameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SensorNameAlreadyExistsException.class)
    ProblemDetail handleSensorNameAlreadyExist(SensorNameAlreadyExistsException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }
}
