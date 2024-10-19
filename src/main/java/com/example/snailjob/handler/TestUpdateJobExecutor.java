package com.example.snailjob.handler;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.job.core.enums.TriggerTypeEnum;
import com.aizuda.snailjob.client.job.core.handler.RequestUpdateHandler;
import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@JobExecutor(name = "testUpdateJobExecutor")
public class TestUpdateJobExecutor {
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        Object jobParams = jobArgs.getJobParams();
        Map<Object, Object> hashMap = JsonUtil.parseHashMap((String) jobParams);
        String id = String.valueOf(hashMap.get("id"));
        String jobName = String.valueOf(hashMap.get("jobName"));
        RequestUpdateHandler updateHandler = SnailJobOpenApi.updateJob(Long.valueOf(id));
        updateHandler.setJobName(jobName);
        updateHandler.setTriggerType(TriggerTypeEnum.WORK_FLOW);
        return ExecuteResult.success(updateHandler.execute());
    }
}
