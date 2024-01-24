package com.example.easy.retry.job;

/**
 * @author zhengweilin
 * @version 1.0.0
 * @date 2024/01/22
 */

import com.aizuda.easy.retry.client.job.core.annotation.JobExecutor;
import com.aizuda.easy.retry.client.job.core.dto.JobArgs;
import com.aizuda.easy.retry.client.model.ExecuteResult;
import com.aizuda.easy.retry.common.core.util.JsonUtil;
import com.example.easy.retry.po.FailOrderPo;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@JobExecutor(name = "testBroadcastJobExecutor")
public class TestBroadcastJobExecutor {

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        if (new Random().nextInt(9) % 5 == 0) {
            throw new NullPointerException("广播节点抛异常了" + JsonUtil.toJsonString(jobArgs));
        }
        FailOrderPo failOrderPo = new FailOrderPo();
        failOrderPo.setOrderId("xiaowoniu");
        return ExecuteResult.success(failOrderPo);
    }
}
