package com.tiv.service.utils.idworker;

public interface WorkerIdStrategy {

    void initialize();

    long availableWorkerId();

    void release();
}
