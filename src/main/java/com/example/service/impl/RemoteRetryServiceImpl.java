package com.example.service.impl;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.lang.Assert;
import com.aizuda.snailjob.client.core.intercepter.RetrySiteSnapshot;
import com.aizuda.snailjob.common.core.enums.RetryStatusEnum;
import com.example.handler.OnlyRemoteRetryHandler;
import com.example.service.RemoteRetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.aizuda.snailjob.client.core.annotation.Retryable;
import com.aizuda.snailjob.client.core.retryer.RetryType;
import com.example.customized.MultiParamIdempotentGenerate;
import com.example.customized.OrderIdempotentIdGenerate;
import com.example.customized.OrderCompleteCallback;
import com.example.customized.OrderRetryMethod;
import com.example.customized.SingleParamIdempotentGenerate;
import com.example.vo.OrderVo;

/**
 * snail-job中的远程重试
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RemoteRetryServiceImpl implements RemoteRetryService {
    private final OnlyRemoteRetryHandler onlyRemoteRetryHandler;

    /**
     * 定义一个最基本的远程重试方法
     */
    @Override
    @Retryable(scene = "remoteRetry", retryStrategy = RetryType.ONLY_REMOTE)
    public void remoteRetry(String params) {
        System.out.println("远程重试方法启动");
        double i = 1 / 0;
    }

    @Override
    @Retryable(scene = "remoteRetrySync", retryStrategy = RetryType.ONLY_REMOTE, async = false, timeout = 500, unit = TimeUnit.MILLISECONDS)
    public void remoteSync(String params) {
        System.out.println("同步远程重试方法启动");
        double i = 1 / 0;
    }

    /**
     * 使用自定义的幂等Id生成类 OrderIdempotentIdGenerate
     */
    @Override
    @Retryable(scene = "remoteRetryWithIdempotentId", retryStrategy = RetryType.ONLY_REMOTE,
            idempotentId = OrderIdempotentIdGenerate.class)
    public boolean remoteRetryWithIdempotentId(OrderVo orderVo) {
        double i = 1 / 0;
        return true;
    }

    /**
     * 使用自定义的幂等Id生成类 SingleParamIdempotentGenerate 测试单参数场景下
     */
    @Override
    @Retryable(scene = "retryWithSingleParamIdempotentGenerate", retryStrategy = RetryType.ONLY_REMOTE,
            idempotentId = SingleParamIdempotentGenerate.class)
    public boolean retryWithSingleParamIdempotentGenerate(String params) {
        throw new NullPointerException();
    }

    /**
     * 使用自定义的幂等Id生成类 MultiParamIdempotentGenerate 测试多个参数场景下的解析
     */
    @Override
    @Retryable(scene = "retryWithMulParamIdempotentGenerate", retryStrategy = RetryType.ONLY_REMOTE,
            idempotentId = MultiParamIdempotentGenerate.class)
    public boolean retryWithMulParamIdempotentGenerate(String uuid, Integer intVal, Double doubleVal,
                                                       Character character, OrderVo orderVo) {
        throw new NullPointerException();
    }

    /**
     * 使用自定义的异常处理类 OrderRetryMethod
     */
    @Override
    @Retryable(scene = "remoteRetryWithRetryMethod", retryStrategy = RetryType.ONLY_REMOTE,
            retryMethod = OrderRetryMethod.class)
    public boolean remoteRetryWithRetryMethod(OrderVo orderVo) {
        throw new NullPointerException();
    }

    /**
     * 自定义BizNo
     */
    @Retryable(scene = "remoteRetryWithBizNo", retryStrategy = RetryType.ONLY_REMOTE, bizNo = "#orderVo.orderId")
    public boolean remoteRetryWithBizNo(OrderVo orderVo) {
        throw new NullPointerException();
    }

    @Override
    @Retryable(scene = "localRetryWithPropagationRequired", retryStrategy = RetryType.LOCAL_REMOTE)
    public boolean localRetryWithPropagationRequired(String params) {
        onlyRemoteRetryHandler.localRetryWithRequires(params);
        return false;
    }

    @Override
    public boolean localRetryWithPropagationRequiredNew(String params) {
        onlyRemoteRetryHandler.localRetryWithRequiresNew(params);
        return false;
    }

    @Override
    public boolean localRetryWithTryCatch1(String params) {
        try {
            // 内部方法需要触发重试
            onlyRemoteRetryHandler.localRetryWithRequiresNew(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }
        return false;
    }

    @Override
    public boolean localRetryWithTryCatch2(String params) {
        // 由于传播机制为{REQUIRED}，异常被捕获,所以内部不会触发重试
        try {
            onlyRemoteRetryHandler.localRetryWithRequires(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }

        return false;
    }

    @Override
    public boolean localRetryWithTwoRetryMethod(String params) {
        onlyRemoteRetryHandler.retryMethod1(params);
        onlyRemoteRetryHandler.retryMethod2(params);
        return false;
    }

    /**
     * 使用自定义的retryCompleteCallback类 OrderCompleteCallback
     */
    @Retryable(scene = "remoteRetryWithCompleteCallback", retryStrategy = RetryType.ONLY_REMOTE,
            retryCompleteCallback = OrderCompleteCallback.class)
    public boolean remoteRetryWithCompleteCallback(String scene, OrderVo orderVo) {

        Assert.notNull(RetrySiteSnapshot.getStage(), () -> new IllegalArgumentException("测试回调"));

        // 本地重试阶段，执行失败，远程的执行成功
        if (RetrySiteSnapshot.getStage().equals(RetrySiteSnapshot.EnumStage.LOCAL.getStage())) {
            // 生成的结果在50%的概率下执行这里的逻辑
            throw new NullPointerException();
        }

        if (scene.equals(RetryStatusEnum.MAX_COUNT.name())) {
            throw new NullPointerException();
        }

        return true;
    }


}
