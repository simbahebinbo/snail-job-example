package com.example.snailjob.handler;

import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import com.aizuda.snailjob.common.core.enums.StatusEnum;
import org.springframework.stereotype.Component;

@Component
public class TestUpdateJobStatusHandler {

    /**
     * 更新定时任务状态
     *
     * @param jobId 定时任务ID
     * @return
     */
    public Boolean updateJobStatus(Long jobId){
        return SnailJobOpenApi
                .updateJobStatus(jobId)
                .setStatus(StatusEnum.YES)
                .execute();
    }

    /**
     * 更新工作流任务状态
     *
     * @param workFlowId 工作流ID
     * @return
     */
    public Boolean updateWorkFlowStatus(Long workFlowId){
        return SnailJobOpenApi
                .updateWorkFlowStatus(workFlowId)
                .setStatus(StatusEnum.YES)
                .execute();
    }
}
