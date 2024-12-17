package com.example.job;

/**
 * @author zhengweilin
 * @version 1.0.0
 * @date 2024/01/22
 */

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.example.po.FailOrderPo;
import org.springframework.stereotype.Component;

@Component
@JobExecutor(name = "testPartitionJobExecutor")
public class TestPartitionJobExecutor {

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        if (jobArgs.getJobParams().equals("1")) {
            throw new NullPointerException("分区空指针抛异常了");
        }
        FailOrderPo failOrderPo = new FailOrderPo();
        failOrderPo.setOrderId("xiaowoniu");
        return ExecuteResult.success(failOrderPo);
    }
}
