package com.example.easy.retry.service;

import org.springframework.stereotype.Component;

import com.aizuda.easy.retry.client.core.retryer.EasyRetryTemplate;
import com.aizuda.easy.retry.client.core.retryer.RetryTaskTemplateBuilder;
import com.example.easy.retry.executor.ManualRetryExecutorTask;
import com.example.easy.retry.vo.OrderVo;

/**
 * easy-retry中的手动重试
 */
@Component
public class ManualRetryExecutorMethodService {

    public void myExecutorMethod(String params) {
        OrderVo orderVo = OrderVo.builder()
            .orderId(params)
            .source(1)
            .build();
        EasyRetryTemplate easyRetryTemplate = RetryTaskTemplateBuilder.newBuilder()
            // 手动指定场景名称
            .withScene(ManualRetryExecutorTask.SCENE)
            // 指定要执行的任务
            .withExecutorMethod(ManualRetryExecutorTask.class)
            // 指定参数
            .withParam(orderVo)
            .build();
        // 执行模板
        easyRetryTemplate.executeRetry();
    }

}
