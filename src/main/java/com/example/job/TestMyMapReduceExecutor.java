package com.example.job;

import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.MergeReduceArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.job.core.executor.AbstractMapReduceExecutor;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.constant.SystemConstants;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author opensnail
 * @date 2024-07-07 12:06:57
 * @since sj_1.1.0
 */
@Component
public class TestMyMapReduceExecutor extends AbstractMapReduceExecutor {

    @Override
    public ExecuteResult doJobMapExecute(MapArgs mapArgs, MapHandler mapHandler) {
        if (SystemConstants.ROOT_MAP.equals(mapArgs.getTaskName())) {
            return mapHandler.doMap(Lists.newArrayList("1", "2", "3", "4"), "TWO_MAP");
        }

        return ExecuteResult.success(mapArgs.getMapResult());
    }

    @Override
    protected ExecuteResult doReduceExecute(ReduceArgs reduceArgs) {
        List<String> mapResult = (List<String>) reduceArgs.getMapResult();
        return ExecuteResult.success(mapResult.stream().mapToInt(Integer::parseInt).sum());
    }

    @Override
    protected ExecuteResult doMergeReduceExecute(MergeReduceArgs mergeReduceArgs) {
        List<String> reduces = (List<String>) mergeReduceArgs.getReduces();
        return ExecuteResult.success(reduces.stream().mapToInt(Integer::parseInt).sum());
    }


}
