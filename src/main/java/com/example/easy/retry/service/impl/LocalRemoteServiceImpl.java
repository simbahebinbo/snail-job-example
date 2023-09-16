package com.example.easy.retry.service.impl;

import com.aizuda.easy.retry.client.core.annotation.Retryable;
import com.aizuda.easy.retry.client.core.retryer.RetryType;
import com.example.easy.retry.service.LocalRemoteService;
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
public class LocalRemoteServiceImpl implements LocalRemoteService {

    @Retryable(scene = "localRemote", retryStrategy = RetryType.LOCAL_REMOTE)
    public void localRemote() {
        System.out.println("local retry 方法开始执行");
        double i = 1 / 0;
    }

    /**
     * 使用先本地再远程的策略同步上传重试请求 retryStrategy = LOCAL_REMOTE 代表本地重试3次后再执行远程上报 async = false 代表使用同步上传的方式 timeout = 1 代表超时时间为1
     * unit = MINUTES 代表超时时间的单位是分钟
     */
    @Retryable(scene = "remoteRetryWithSync", retryStrategy = RetryType.LOCAL_REMOTE,
        async = false, timeout = 1, unit = TimeUnit.MINUTES)
    public String remoteRetryWithLocalRemote(String requestId) {
        double i = 1 / 0;
        return requestId;
    }

}
