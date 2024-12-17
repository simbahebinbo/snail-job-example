package com.example.handler;

import com.aizuda.snailjob.client.job.core.enums.TriggerTypeEnum;
import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestUpdateJobHandler {

    /**
     * 新增集群模式的任务
     *
     * @return 任务id
     */
    public Boolean updateClusterJob(Long jobId) {
        return SnailJobOpenApi.updateClusterJob(jobId)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.SCHEDULED_TIME)
                .setTriggerInterval(String.valueOf(60))
                .addArgsStr("update测试数据", new Random().nextInt(1000))
                .addArgsStr("updateArg", "args")
                .setRetryInterval(3)
                .execute();

    }

    /**
     * 新增集群模式的任务
     *
     * @return 任务id
     */
    public Boolean updateBroadcastJob(Long jobId) {
        return SnailJobOpenApi.updateBroadcastJob(jobId)
                .addArgsStr("update测试数据", new Random().nextInt(1000))
                .execute();

    }

    /**
     * 新增Sharding模式的任务
     *
     * @return 任务id
     */
    public Boolean updateShardingJob(Long jobId) {
        return SnailJobOpenApi.updateShardingJob(jobId)
                .addShardingArgs("update分片1", "update分片2", "update分片3")
                .execute();

    }

    /**
     * 新增MapReduce模式的任务
     *
     * @return 任务id
     */
    public Boolean updateMapJob(Long jobId) {
        return SnailJobOpenApi.updateMapJob(jobId)
                .addArgsStr("update测试数据", new Random().nextInt(1000))
                .setParallelNum(3)
                .execute();

    }

    /**
     * 新增MapReduce模式的任务
     *
     * @return 任务id
     */
    public Boolean updateMapReduceJob(Long jobId) {
        return SnailJobOpenApi.updateMapReduceJob(jobId)
                .addArgsStr("update测试数据", new Random().nextInt(1000))
                .execute();

    }
}
