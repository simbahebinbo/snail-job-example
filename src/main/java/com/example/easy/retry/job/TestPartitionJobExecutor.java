package com.example.easy.retry.job;

/**
 * @author zhengweilin
 * @version 1.0.0
 * @date 2024/01/22
 */

import com.aizuda.easy.retry.client.job.core.annotation.JobExecutor;
import com.aizuda.easy.retry.client.job.core.dto.JobArgs;
import com.aizuda.easy.retry.client.model.ExecuteResult;
import com.example.easy.retry.po.FailOrderPo;
import org.springframework.stereotype.Component;

@Component
@JobExecutor(name = "testPartitionJobExecutor")
public class TestPartitionJobExecutor {

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        if (jobArgs.getArgsStr().equals("1")) {
            throw new NullPointerException("分区空指针抛异常了");
        }
        FailOrderPo failOrderPo = new FailOrderPo();
        failOrderPo.setOrderId("xiaowoniu");
        return ExecuteResult.success(failOrderPo);
    }
}
