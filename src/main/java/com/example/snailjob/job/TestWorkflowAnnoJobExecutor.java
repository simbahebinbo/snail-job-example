package com.example.snailjob.job;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.example.snailjob.po.FailOrderPo;
import org.springframework.stereotype.Component;

/**
 * @author www.byteblogs.com
 * @date 2023-09-28 22:54:07
 * @since 2.6.0
 */
@Component
@JobExecutor(name = "testWorkflowAnnoJobExecutor")
public class TestWorkflowAnnoJobExecutor {

    public ExecuteResult jobExecute(JobArgs jobArgs) throws InterruptedException {
//        for (int i = 0; i < 30; i++) {
//            SnailJobLog.REMOTE.info("任务执行开始. [{}]", i + "" + JsonUtil.toJsonString(jobArgs));
//        }
        FailOrderPo failOrderPo = new FailOrderPo();
        failOrderPo.setOrderId("xiaowoniu");
        return ExecuteResult.success(failOrderPo);
    }

}
