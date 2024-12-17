package com.example.job;

import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MapExecutor;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

/**
 * @author: opensnail
 * @date : 2024-06-26
 */
@Component
@JobExecutor(name = "testAnnoMapJobExecutor")
public class TestAnnoMapJobExecutor {

    @MapExecutor
    public ExecuteResult rootMapExecute(MapArgs mapArgs, MapHandler mapHandler) {
        System.out.println(mapArgs);
        System.out.println(mapArgs.getWfContext());
        return mapHandler.doMap(Lists.newArrayList("1", "2", "3"), "MONTH_MAP");
    }

    @MapExecutor(taskName = "MONTH_MAP")
    public ExecuteResult monthMapExecute(MapArgs mapArgs) {
        System.out.println(mapArgs);
        System.out.println(mapArgs.getWfContext());
        return ExecuteResult.success(123);
    }

    @MapExecutor(taskName = "LAST_MAP")
    public ExecuteResult lastMapExecute(MapArgs mapArgs, MapHandler mapHandler) {
        System.out.println(mapArgs);
        System.out.println(mapArgs.getWfContext());
        return ExecuteResult.success();
    }

}
