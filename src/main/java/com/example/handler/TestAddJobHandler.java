package com.example.handler;

import com.aizuda.snailjob.client.job.core.enums.AllocationAlgorithmEnum;
import com.aizuda.snailjob.client.job.core.enums.TriggerTypeEnum;
import com.aizuda.snailjob.client.job.core.openapi.SnailJobOpenApi;
import com.aizuda.snailjob.common.core.enums.BlockStrategyEnum;
import org.springframework.stereotype.Component;

@Component
public class TestAddJobHandler {

    /**
     * 新增集群模式的任务
     *
     * @param jobName 任务名称
     * @return 任务id
     */
    public Long addClusterJob(String jobName) {
        return SnailJobOpenApi.addClusterJob()
                .setRouteKey(AllocationAlgorithmEnum.RANDOM)
                .setJobName(jobName)
                .setExecutorInfo("testJobExecutor")
                .setExecutorTimeout(30)
                .setDescription("add")
                .setBlockStrategy(BlockStrategyEnum.DISCARD)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.SCHEDULED_TIME)
                .setTriggerInterval(String.valueOf(60))
                .addArgsStr("测试数据", 123)
                .addArgsStr("addArg", "args")
                .setRetryInterval(3)
                .execute();

    }

    /**
     * 新增集群模式的任务
     *
     * @param jobName 任务名称
     * @return 任务id
     */
    public Long addBroadcastJob(String jobName) {
        return SnailJobOpenApi.addBroadcastJob()
                .setJobName(jobName)
                .setExecutorInfo("testJobExecutor")
                .setExecutorTimeout(30)
                .setDescription("add")
                .setBlockStrategy(BlockStrategyEnum.DISCARD)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.CRON)
                .setTriggerInterval("afas")
                .addArgsStr("测试数据", 123)
                .addArgsStr("addArg", "args")
                .setRetryInterval(3)
                .execute();

    }

    /**
     * 新增Sharding模式的任务
     *
     * @param jobName 任务名称
     * @return 任务id
     */
    public Long addShardingJob(String jobName) {
        return SnailJobOpenApi.addShardingJob()
                .setJobName(jobName)
                .setExecutorInfo("testJobExecutor")
                .setExecutorTimeout(30)
                .setDescription("add")
                .setBlockStrategy(BlockStrategyEnum.DISCARD)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.SCHEDULED_TIME)
                .setTriggerInterval(60)
                .addShardingArgs("分片1", "分片2", "分片3")
                .setParallelNum(1)
                .setRetryInterval(3)
                .execute();

    }

    /**
     * 新增MapReduce模式的任务
     *
     * @param jobName 任务名称
     * @return 任务id
     */
    public Long addMapJob(String jobName) {
        return SnailJobOpenApi.addMapJob()
                .setJobName(jobName)
                .setExecutorInfo("testJobExecutor")
                .setExecutorTimeout(30)
                .setDescription("add")
                .setBlockStrategy(BlockStrategyEnum.DISCARD)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.SCHEDULED_TIME)
                .setTriggerInterval(String.valueOf(60))
                .setParallelNum(3)
                .setRetryInterval(3)
                .execute();

    }

    /**
     * 新增MapReduce模式的任务
     *
     * @param jobName 任务名称
     * @return 任务id
     */
    public Long addMapReduceJob(String jobName) {
        return SnailJobOpenApi.addMapReduceJob()
                .setJobName(jobName)
                .setExecutorInfo("testJobExecutor")
                .setExecutorTimeout(30)
                .setDescription("add")
                .setBlockStrategy(BlockStrategyEnum.DISCARD)
                .setMaxRetryTimes(1)
                .setTriggerType(TriggerTypeEnum.SCHEDULED_TIME)
                .setTriggerInterval(String.valueOf(60))
                .setParallelNum(3)
                .setShardNum(2)
                .setRetryInterval(3)
                .execute();

    }
}
