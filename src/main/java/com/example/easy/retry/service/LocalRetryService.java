package com.example.easy.retry.service;

import com.aizuda.easy.retry.client.core.annotation.Retryable;
import com.aizuda.easy.retry.client.core.retryer.RetryType;

/**
 * @author:  www.byteblogs.com
 * @date : 2023-09-06 09:03
 */
public interface LocalRetryService {

    void localRetry(String params);

    @Retryable(scene = "localRetryWithAnnoOnInterface", retryStrategy = RetryType.ONLY_LOCAL)
    void localRetryWithAnnoOnInterface(String params);

    void localRetryWithBasicParams(String params);

    void localRetryIncludeException(String params);

    void localRetryExcludeException(String type);

    void localRetryIsThrowException(String params);


}
