package com.example.snailjob.handler;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.job.core.handler.RequestTriggerJobHandler;
import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@JobExecutor(name = "testTriggerWorkFlowExecutor")
public class TestTriggerWorkFlowExecutor {
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        Object jobParams = jobArgs.getJobParams();
        Map<Object, Object> hashMap = JsonUtil.parseHashMap((String) jobParams);
        String id = String.valueOf(hashMap.get("id"));
        RequestTriggerJobHandler jobHandler = SnailJobOpenApi.triggerWorkFlow(Long.valueOf(id));
        return ExecuteResult.success(jobHandler.execute());
    }
}
