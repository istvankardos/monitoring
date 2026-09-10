package com.aldisued.iot.monitoring.exception;

public class SensorNameAlreadyExistsException extends RuntimeException {

    public SensorNameAlreadyExistsException(String name) {
        super("Another sensor already exists with the name: " + name);
    }
}
