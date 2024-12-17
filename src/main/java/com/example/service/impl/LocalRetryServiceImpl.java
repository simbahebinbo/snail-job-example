package com.example.service.impl;

import com.aizuda.snailjob.client.core.retryer.RetryType;
import com.example.customized.OrderRetryMethod;
import com.example.handler.OnlyLocalRetryHandler;
import com.example.service.LocalRetryService;
import com.example.vo.OrderVo;
import com.example.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aizuda.snailjob.client.core.annotation.Retryable;

/**
 * snail-job中的本地重试demo
 */

@Component
@Slf4j
public class LocalRetryServiceImpl implements LocalRetryService {

    @Autowired
    private OnlyLocalRetryHandler onlyLocalRetryHandler;

    /**
     * 入门案例
     * 我们仅仅需要指定场景值scene就可以给方法赋予重试逻辑
     * 其他的参数均可以省略或者使用默认值
     * [参数释义]
     * 场景值scene对应我们后台的场景参数，用于后续的分组
     * 在微服务中建议大家直接使用集群的服务名称即可
     */
    @Override
    @Retryable(scene = "localRetry", retryStrategy = RetryType.ONLY_LOCAL)
    public void localRetry(String params) {
        System.out.println("local retry 方法开始执行");
        double i = 1 / 0;
    }

    @Override
    public boolean localRetryWithTwoRetryMethod(final String params) {
        onlyLocalRetryHandler.retryMethod1(params);
        onlyLocalRetryHandler.retryMethod2(params);
        return true;
    }

    @Override
    public void localRetryWithAnnoOnInterface(final String params) {
        double i = 1 / 0;
    }

    /**
     * 指定基础参数
     * localTimes 本地重试次数
     * localInterval 本地重试间隔时间(默认单位为秒)
     * unit 超时时间单位
     * 以下案例指的是本地重试4次,每次重试之间间隔10s
     */
    @Override
    @Retryable(scene = "localRetryWithBasicParams", localTimes = 4, localInterval = 10, retryStrategy = RetryType.ONLY_LOCAL)
    public void localRetryWithBasicParams(String params) {
        System.out.println("local retry with basic params 方法开始执行");
        double i = 1 / 0;
    }

    /**
     * 指定异常参数
     * include参数指的是当我们遭遇到指定异常时进行重试
     * 在这个案例中我们处理两个场景:
     * 抛出指定异常,例如抛出自定义的ParamException异常，观察是否会重试
     * 抛出非指定异常,例如我们在这里产生一个异常,观察是否会重试
     * 注意:如果此时我们在include 中指定参数为BusinessException(ParamException的父类),同样也会进行重试逻辑
     * 更多用法:如果我们需要在include中指定多个参数,可以使用 include = {ParamException.class,NullPointerException.class}
     */
    @Override
    @Retryable(scene = "localRetryIncludeException", include = ParamException.class, retryStrategy = RetryType.ONLY_LOCAL)
    public void localRetryIncludeException(String params) {
        System.out.println("local retry include exception 方法开始执行");
        if ("NullPointerException".equals(params)) {
            throw new NullPointerException();
        } else {
            throw new ParamException("此处发生了指定异常Param Exception");
        }
    }

    /**
     * 这个参数的作用和include是相反的
     * exclude参数指的是当我们遇到指定异常时则不会进行重试
     * 比如在下述案例中我们指定了遇到ParamException和ArithmeticException后不进行重试
     */
    @Override
    @Retryable(scene = "localRetryExcludeException", exclude = {ParamException.class, ArithmeticException.class}, retryStrategy = RetryType.ONLY_LOCAL)
    public void localRetryExcludeException(String type) {
        System.out.println("local retry exclude exception 方法开始执行");

        if ("ParamException".equals(type)) {
            throw new ParamException("此处发生了指定异常Param Exception");
        } else if ("ArithmeticException".equals(type)) {
            throw new ParamException("此处发生了指定异常Arithme Exception");
        } else {
            throw new UnsupportedOperationException("未知异常");
        }
    }

    @Override
    @Retryable(scene = "localRetryIsThrowException", isThrowException = false, retryStrategy = RetryType.ONLY_LOCAL)
    public void localRetryIsThrowException(String params) {
        System.out.println("local retry is throw exception 方法开始执行");
        throw new ParamException("此处发生了参数异常");
    }

    @Override
    @Retryable(scene = "localRetryWithRetryMethod", retryStrategy = RetryType.ONLY_LOCAL,
            retryMethod = OrderRetryMethod.class)
    public boolean localRetryWithRetryMethod(OrderVo orderVo) {
        throw new NullPointerException();
    }

    @Override
    @Retryable(scene = "localRetryWithPropagationRequired", retryStrategy = RetryType.ONLY_LOCAL)
    public boolean localRetryWithPropagationRequired(String params) {
        onlyLocalRetryHandler.localRetry(params);
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithPropagationRequiredNew", retryStrategy = RetryType.ONLY_LOCAL)
    public boolean localRetryWithPropagationRequiredNew(final String params) {
        onlyLocalRetryHandler.localRetryWithRequiresNew(params);
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithTryCatch1", retryStrategy = RetryType.ONLY_LOCAL)
    public boolean localRetryWithTryCatch1(String params) {
        try {
            // 内部方法需要触发重试
            onlyLocalRetryHandler.localRetryWithRequiresNew(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }
        return false;
    }

    @Override
    @Retryable(scene = "localRetryWithTryCatch2", retryStrategy = RetryType.ONLY_LOCAL)
    public boolean localRetryWithTryCatch2(String params) {
        // 由于传播机制为{REQUIRED}，异常被捕获,所以内部不会触发重试
        try {
            onlyLocalRetryHandler.localRetry(params);
        } catch (Exception e) {
            log.error("inner method encountered an exception", e);
        }

        return false;
    }

}
