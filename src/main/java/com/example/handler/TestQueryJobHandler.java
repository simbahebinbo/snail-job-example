package com.example.handler;

import com.aizuda.snailjob.client.job.core.dto.JobResponseVO;
import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import org.springframework.stereotype.Component;

@Component
public class TestQueryJobHandler {

    /**
     * 查看任务详情
     *
     * @param jobId
     * @return 任务详情
     */
    public JobResponseVO queryJob(Long jobId) {
        return SnailJobOpenApi.getJobDetail(jobId).execute();
    }
}
