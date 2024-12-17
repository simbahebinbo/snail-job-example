package com.example.service;

import com.aizuda.snailjob.client.core.annotation.Retryable;
import com.aizuda.snailjob.client.core.retryer.RetryType;
import com.example.vo.OrderVo;

/**
 * @author: www.byteblogs.com
 * @date : 2023-09-06 09:03
 */
public interface LocalRetryService {

    void localRetry(String params);

    boolean localRetryWithTwoRetryMethod(String params);

    @Retryable(scene = "localRetryWithAnnoOnInterface", retryStrategy = RetryType.ONLY_LOCAL)
    void localRetryWithAnnoOnInterface(String params);

    void localRetryWithBasicParams(String params);

    void localRetryIncludeException(String params);

    void localRetryExcludeException(String type);

    void localRetryIsThrowException(String params);

    boolean localRetryWithRetryMethod(OrderVo orderVo);

    boolean localRetryWithPropagationRequired(String params);

    boolean localRetryWithPropagationRequiredNew(String params);

    boolean localRetryWithTryCatch1(String params);

    boolean localRetryWithTryCatch2(String params);

}
