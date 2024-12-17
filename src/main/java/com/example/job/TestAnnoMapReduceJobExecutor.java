package com.example.job;

import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MapExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MergeReduceExecutor;
import com.aizuda.snailjob.client.job.core.annotation.ReduceExecutor;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.MergeReduceArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: opensnail
 * @date : 2024-06-26
 */
@Component
@JobExecutor(name = "testAnnoMapReduceJobExecutor")
public class TestAnnoMapReduceJobExecutor {

    @MapExecutor
    public ExecuteResult rootMapExecute(MapArgs mapArgs, MapHandler mapHandler) {
        System.out.println(mapArgs);
        System.out.println(mapArgs.getWfContext());
        mapArgs.appendContext("name", "zsg");
        return mapHandler.doMap(Lists.newArrayList("1", "2", "3", "4", "5", "6"), "MONTH_MAP");
    }

    @MapExecutor(taskName = "MONTH_MAP")
    public ExecuteResult monthMapExecute(MapArgs mapArgs) {
        System.out.println(mapArgs);
        System.out.println(mapArgs.getWfContext());
        mapArgs.appendContext("age", "111");
        return ExecuteResult.success(Integer.parseInt((String) mapArgs.getMapResult()) * 2);
    }

    @ReduceExecutor
    public ExecuteResult reduceExecute(ReduceArgs mapReduceArgs) {
        System.out.println(mapReduceArgs);
        System.out.println(mapReduceArgs.getWfContext());
        return ExecuteResult.success(
                mapReduceArgs.getMapResult()
                        .stream()
                        .map(String::valueOf)
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue).sum());
    }

    /**
     * 当只有一个reduce任务时无此执行器
     */
    @MergeReduceExecutor
    public ExecuteResult mergeReduceExecute(MergeReduceArgs mergeReduceArgs) {
        System.out.println(mergeReduceArgs);
        System.out.println(mergeReduceArgs.getWfContext());
        return ExecuteResult.success(
                mergeReduceArgs.getReduces()
                        .stream()
                        .map(String::valueOf)
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue).sum());
    }
}
