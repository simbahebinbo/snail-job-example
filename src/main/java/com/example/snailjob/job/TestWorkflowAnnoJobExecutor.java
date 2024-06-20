package com.example.snailjob.job;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.example.snailjob.po.FailOrderPo;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author www.byteblogs.com
 * @date 2023-09-28 22:54:07
 * @since 2.6.0
 */
@Component
@JobExecutor(name = "testWorkflowAnnoJobExecutor")
public class TestWorkflowAnnoJobExecutor {

    public ExecuteResult jobExecute(JobArgs jobArgs) throws InterruptedException {
        FailOrderPo failOrderPo = new FailOrderPo();
        failOrderPo.setOrderId("xiaowoniu");
        // 测试上下文传递
        int i = new Random().nextInt(1000);
        jobArgs.appendContext("name" + i, "小蜗牛" + i);
        jobArgs.appendContext("age", 18);
        return ExecuteResult.success(failOrderPo);
    }

}
