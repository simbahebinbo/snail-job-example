package com.example.handler;

import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import org.springframework.stereotype.Component;

@Component
public class TestTriggerJobHandler {

    /**
     * 手动调度任务
     *
     * @param jobId 任务ID
     * @return
     */
    public Boolean triggerJob(Long jobId) {
        return SnailJobOpenApi.triggerJob(jobId).execute();
    }

    /**
     * 手动调度工作流任务
     *
     * @param workFlowId 工作流任务ID
     * @return
     */
    public Boolean triggerWorkFlow(Long workFlowId) {
        return SnailJobOpenApi.triggerWorkFlow(workFlowId).execute();
    }
}
