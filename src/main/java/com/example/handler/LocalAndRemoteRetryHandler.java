package com.example.handler;

import com.aizuda.snailjob.client.core.annotation.Propagation;
import com.aizuda.snailjob.client.core.annotation.Retryable;
import com.aizuda.snailjob.client.core.retryer.RetryType;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author: xiaowoniu
 * @date : 2024-02-05
 * @since : 3.1.0
 */
@Component
public class LocalAndRemoteRetryHandler {

    @Retryable(scene = "localRetryWithTwoRetryMethod1", retryStrategy = RetryType.LOCAL_REMOTE)
    public void retryMethod1(String params) {
        System.out.println("localRetryWithTwoRetryMethod1");
        if (params.equals("1")) {
            throw new RuntimeException("抛出异常");
        }

        if (params.equals("3")) {
            int random = new Random().nextInt(10);
            if (random % 3 == 0) {
                System.out.println("localRetryWithTwoRetryMethod1 is success");
                return;
            }

            throw new RuntimeException("抛出异常");
        }
    }

    @Retryable(scene = "localRetryWithTwoRetryMethod2", retryStrategy = RetryType.LOCAL_REMOTE)
    public void retryMethod2(String params) {
        System.out.println("localRetryWithTwoRetryMethod2");
        if (params.equals("2")) {
            throw new RuntimeException("抛出异常");
        }

        if (params.equals("3")) {
            throw new RuntimeException("抛出异常");
        }
    }

    @Retryable(scene = "localRetryWithRequires", retryStrategy = RetryType.LOCAL_REMOTE)
    public void localRetryWithRequires(String params) {
        System.out.println("local retry 方法开始执行");
        double i = 1 / 0;
    }

    @Retryable(scene = "localRetryWithRequiresNew", retryStrategy = RetryType.LOCAL_REMOTE, propagation = Propagation.REQUIRES_NEW)
    public void localRetryWithRequiresNew(String params) {
        System.out.println("local retry 方法开始执行");
        double i = 1 / 0;
    }
}
