package com.example.snailjob.job;

import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.job.core.executor.AbstractMapReduceExecutor;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.constant.SystemConstants;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;


/**
 * @author: opensnail
 * @date : 2024-06-13
 * @since : sj_1.1.0
 */
@Component
public class TestMapReduceJobExecutor extends AbstractMapReduceExecutor {

    @Override
    public ExecuteResult doJobMapExecute(MapArgs mapArgs) {
        if (mapArgs.getTaskName().equals(SystemConstants.MAP_ROOT)) {
            doMap(Lists.newArrayList(Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6)), "TWO_MAP");
            return ExecuteResult.success();
        } else if (mapArgs.getTaskName().equals("TWO_MAP")) {
            return ExecuteResult.success(mapArgs.getArgsStr());
        } else {
            return ExecuteResult.success();
        }
    }

    @Override
    protected ExecuteResult doReduceExecute(ReduceArgs reduceArgs) {
        return ExecuteResult.success(reduceArgs.getMapResult());
    }

    @Override
    protected ExecuteResult doMergeReduceExecute(ReduceArgs reduceArgs) {
        return ExecuteResult.success(reduceArgs.getMapResult());
    }
}
