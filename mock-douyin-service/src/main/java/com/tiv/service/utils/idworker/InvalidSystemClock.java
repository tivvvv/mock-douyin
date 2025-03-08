package com.tiv.service.utils.idworker;

public class InvalidSystemClock extends RuntimeException {

    public InvalidSystemClock(String message) {
        super(message);
    }
}
