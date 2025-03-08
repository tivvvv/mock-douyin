package com.tiv.service.utils.idworker;

public interface RandomCodeStrategy {

    void init();

    int prefix();

    int next();

    void release();
}
