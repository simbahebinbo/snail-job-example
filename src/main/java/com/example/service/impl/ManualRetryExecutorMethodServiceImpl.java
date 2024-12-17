package com.example.service.impl;

import com.aizuda.snailjob.client.core.retryer.RetryTaskTemplateBuilder;
import com.aizuda.snailjob.client.core.retryer.SnailJobTemplate;
import com.example.executor.ManualRetryExecutorTask;
import com.example.service.ManualRetryExecutorMethodService;
import com.example.vo.OrderVo;
import org.springframework.stereotype.Component;

/**
 * snail-job中的手动重试
 */
@Component
public class ManualRetryExecutorMethodServiceImpl implements ManualRetryExecutorMethodService {

    public void myExecutorMethod(String params) {
        OrderVo orderVo = OrderVo.builder()
                .orderId(params)
                .source(1)
                .build();
        SnailJobTemplate snailJobTemplate = RetryTaskTemplateBuilder.newBuilder()
                // 手动指定场景名称
                .withScene(ManualRetryExecutorTask.SCENE)
                // 指定要执行的任务
                .withExecutorMethod(ManualRetryExecutorTask.class)
                // 指定参数
                .withParam(orderVo)
                .build();
        // 执行模板
        snailJobTemplate.executeRetry();
    }

}
