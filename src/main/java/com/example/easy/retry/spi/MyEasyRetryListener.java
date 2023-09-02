package com.example.easy.retry.spi;

import com.aizuda.easy.retry.client.core.event.EasyRetryListener;
import com.aizuda.easy.retry.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: www.byteblogs.com
 * @date : 2023-08-28 11:20
 */
@Slf4j
public class MyEasyRetryListener implements EasyRetryListener {

    @Override
    public void beforeRetry(final String sceneName, final String executorClassName, final Object[] params) {
        log.info("------> beforeRetry sceneName:[{}] executorClassName:[{}] params:[{}]",
            sceneName, executorClassName, JsonUtil.toJsonString(params));
    }

    @Override
    public void successOnRetry(final Object result, final String sceneName, final String executorClassName) {
        log.info("------> successOnRetry sceneName:[{}] executorClassName:[{}] result:[{}]",
            sceneName, executorClassName, JsonUtil.toJsonString(result));
    }

    @Override
    public void failureOnRetry(final String sceneName, final String executorClassName, final Throwable e) {
        log.info("------> failureOnRetry sceneName:[{}] executorClassName:[{}]", sceneName, executorClassName, e);
    }
}
