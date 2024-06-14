package com.example.snailjob.job;

import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.job.core.dto.MapReduceArgs;
import com.aizuda.snailjob.client.job.core.executor.AbstractMapReduceExecutor;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.constant.SystemConstants;
import com.aizuda.snailjob.common.core.model.JobContext;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

/**
 * @author: opensnail
 * @date : 2024-06-13
 * @since : sj_1.1.0
 */
@Component
public class MapReduceJobExecutor extends AbstractMapReduceExecutor {

    @Override
    public ExecuteResult doJobExecute(final MapReduceArgs mapReduceArgs) {
        if (mapReduceArgs.getMapName().equals(SystemConstants.MAP_ROOT)) {
            doMapExecute(Lists.newArrayList(Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6)),
                "TWO_MAP");
            return ExecuteResult.success();
        } else {
            return ExecuteResult.success(1);
        }

    }

    @Override
    protected ExecuteResult doReduceExecute(final JobContext jobContext, final JobArgs jobArgs) {
        return null;
    }
}
