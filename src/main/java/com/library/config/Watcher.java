package com.library.config;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Watcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Watcher.class);

    @AfterReturning("execution(* com.library.service.ReaderService.createReader(..))")
    public void logCreateReader() {
        LOGGER.info("Reader created...");
    }
}
