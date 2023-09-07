package com.example.easy.retry.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.example.easy.retry.service.RemoteRetryService;
import org.springframework.stereotype.Component;

import com.aizuda.easy.retry.client.core.annotation.Retryable;
import com.aizuda.easy.retry.client.core.retryer.RetryType;
import com.example.easy.retry.customized.MultiParamIdempotentGenerate;
import com.example.easy.retry.customized.OrderIdempotentIdGenerate;
import com.example.easy.retry.customized.OrderCompleteCallback;
import com.example.easy.retry.customized.OrderRetryMethod;
import com.example.easy.retry.customized.SingleParamIdempotentGenerate;
import com.example.easy.retry.vo.OrderVo;

/**
 * easy-retry中的远程重试
 */
@Component
public class RemoteRetryServiceImpl implements RemoteRetryService {

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
     * 使用自定义的retryCompleteCallback类 OrderCompleteCallback
     */
    @Retryable(scene = "remoteRetryWithCompleteCallback", retryStrategy = RetryType.LOCAL_REMOTE,
        retryCompleteCallback = OrderCompleteCallback.class)
    public boolean remoteRetryWithCompleteCallback(OrderVo orderVo) {
        Random random = new Random();
        // 生成一个随机数，范围为0到1之间
        double probability = random.nextDouble();
        // 判断随机数是否小于等于0.5，即50%的概率
        if (probability <= 0.5) {
            // 生成的结果在50%的概率下执行这里的逻辑
            throw new NullPointerException();
        }
        return true;
    }

    /**
     * 自定义BizNo
     */
    @Retryable(scene = "remoteRetryWithBizNo", retryStrategy = RetryType.ONLY_REMOTE, bizNo = "#orderVo.orderId")
    public boolean remoteRetryWithBizNo(OrderVo orderVo) {
        throw new NullPointerException();
    }


}
