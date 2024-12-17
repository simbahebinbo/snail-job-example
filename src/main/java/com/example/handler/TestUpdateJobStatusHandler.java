package com.example.handler;

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
    public Boolean updateJobStatus(Long jobId, Long status) {
        return SnailJobOpenApi
                .updateJobStatus(jobId)
                .setStatus(StatusEnum.YES.getStatus().equals(status.intValue()) ? StatusEnum.YES : StatusEnum.NO)
                .execute();
    }

    /**
     * 更新工作流任务状态
     *
     * @param workFlowId 工作流ID
     * @param status
     * @return
     */
    public Boolean updateWorkFlowStatus(Long workFlowId, Long status) {
        return SnailJobOpenApi
                .updateWorkFlowStatus(workFlowId)
                .setStatus(StatusEnum.YES.getStatus().equals(status.intValue()) ? StatusEnum.YES : StatusEnum.NO)
                .execute();
    }
}
