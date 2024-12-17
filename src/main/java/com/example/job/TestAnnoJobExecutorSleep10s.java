package com.example.job;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import org.springframework.stereotype.Component;

/**
 * @author www.byteblogs.com
 * @date 2023-09-28 22:54:07
 * @since 2.4.0
 */
@Component
@JobExecutor(name = "testAnnoJobExecutorSleep10s")
public class TestAnnoJobExecutorSleep10s {

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        System.out.println(JsonUtil.toJsonString(jobArgs));
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ExecuteResult.success("测试成功");
    }
}
