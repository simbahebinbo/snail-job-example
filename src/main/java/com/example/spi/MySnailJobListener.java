package com.example.spi;

import com.aizuda.snailjob.client.core.event.SnailJobListener;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: www.byteblogs.com
 * @date : 2023-08-28 11:20
 */
@Slf4j
public class MySnailJobListener implements SnailJobListener {

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
