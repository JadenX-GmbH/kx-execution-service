package com.jadenx.kxexecutionservice.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class FeignRetryConfig implements Retryer {

    private int retryMaxAttempt;

    private long retryInterval;

    private int attempt = 1;


    public FeignRetryConfig(final int retryMaxAttempt, final Long retryInterval) {
        this.retryMaxAttempt = retryMaxAttempt;
        this.retryInterval = retryInterval;
    }

    @Override
    public void continueOrPropagate(final RetryableException retryableException) {
        log.info("Feign retry attempt {} due to {} ", attempt, retryableException.getMessage());

        if (attempt++ == retryMaxAttempt) {
            throw retryableException;
        }
        try {
            Thread.sleep(retryInterval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public Retryer clone() {
        return new FeignRetryConfig(5, 2000L);
    }
}
