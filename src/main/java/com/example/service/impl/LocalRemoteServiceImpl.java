package com.example.service.impl;

import com.aizuda.snailjob.client.core.annotation.Retryable;
import com.aizuda.snailjob.client.core.retryer.RetryType;
import com.example.handler.LocalAndRemoteRetryHandler;
import com.example.service.LocalRemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 模拟先本地再远程重试案例
 *
 * @author www.byteblogs.com
 * @date 2023-07-18 22:19:30
 * @since 2.1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocalRemoteServiceImpl implements LocalRemoteService {
    private final LocalAndRemoteRetryHandler localAndRemoteRetryHandler;

    @Override
    @Retryable(scene = "localRemote", retryStrategy = RetryType.LOCAL_REMOTE)
    public void localRemote() {
        System.out.println("local retry 方法开始执行");
        double i = 1 / 0;
    }

    /**
     * 使用先本地再远程的策略同步上传重试请求 retryStrategy = LOCAL_REMOTE 代表本地重试3次后再执行远程上报 async = false 代表使用同步上传的方式 timeout = 1 代表超时时间为1
     * unit = MINUTES 代表超时时间的单位是分钟
     */
    @Override
    @Retryable(scene = "remoteRetryWithSync", retryStrategy = RetryType.LOCAL_REMOTE,
            async = false, timeout = 1, unit = TimeUnit.MINUTES)
    public String remoteRetryWithLocalRemote(String requestId) {
        double i = 1 / 0;
        return requestId;
    }

    @Override
    @Retryable(scene = "localRetryWithPropagationRequired", retryStrategy = RetryType.LOCAL_REMOTE)
    public boolean localRetryWithPropagationRequired(String params) {
        localAndRemoteRetryHandler.localRetryWithRequires(params);
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithPropagationRequiredNew", retryStrategy = RetryType.LOCAL_REMOTE)
    public boolean localRetryWithPropagationRequiredNew(String params) {
        localAndRemoteRetryHandler.localRetryWithRequiresNew(params);
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithTryCatch1", retryStrategy = RetryType.LOCAL_REMOTE)
    public boolean localRetryWithTryCatch1(String params) {
        try {
            // 内部方法需要触发重试
            localAndRemoteRetryHandler.localRetryWithRequiresNew(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithTryCatch2", retryStrategy = RetryType.LOCAL_REMOTE)
    public boolean localRetryWithTryCatch2(String params) {
        // 由于传播机制为{REQUIRED}，异常被捕获,所以内部不会触发重试
        try {
            localAndRemoteRetryHandler.localRetryWithRequires(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }

        return false;
    }

    @Override
    public boolean localRetryWithTwoRetryMethod(String params) {
        localAndRemoteRetryHandler.retryMethod1(params);
        localAndRemoteRetryHandler.retryMethod2(params);
        return false;
    }

}
