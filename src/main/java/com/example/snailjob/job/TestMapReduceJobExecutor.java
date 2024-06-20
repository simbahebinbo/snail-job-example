package com.example.snailjob.job;

import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.job.core.executor.AbstractMapReduceExecutor;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.example.snailjob.job.TestMapReduceJobExecutor.MonthMap.SubTask;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * 以下是一个统计某电商公司商家的一年的营业额的计算过程
 *
 * @author: opensnail
 * @date : 2024-06-13
 * @since : sj_1.1.0
 */
@Component
public class TestMapReduceJobExecutor extends AbstractMapReduceExecutor {

    @Override
    public ExecuteResult doJobMapExecute(MapArgs mapArgs) {
        MapEnum mapEnum = MapEnum.ofMap(mapArgs.getTaskName());
        if (Objects.nonNull(mapEnum)) {
            Map map = mapEnum.getMap();
            return doMap(map.map(mapArgs), mapEnum.name());
        }

        // 未找到map的任务，则说明当前需要进行处理
        String mapResult = mapArgs.getMapResult();

        // 获取最后一次map的信息.
        SubTask subTask = JsonUtil.parseObject(mapResult, SubTask.class);
        // 此处可以统计数据或者做其他的事情
        // 模拟统计营业额
        int turnover = new Random().nextInt(1000000);
        return ExecuteResult.success(turnover);

    }

    @Override
    protected ExecuteResult doReduceExecute(ReduceArgs reduceArgs) {
        return ExecuteResult.success(reduceArgs.getMapResult());
    }

    @Override
    protected ExecuteResult doMergeReduceExecute(ReduceArgs reduceArgs) {
        return ExecuteResult.success(reduceArgs.getMapResult());
    }

    @Getter
    private enum MapEnum {
        MAP_ROOT(new RootMap()),
        MONTH_MAP(new MonthMap())
        ;

        private final Map map;

        MapEnum(Map map) {
            this.map = map;
        }

        public static MapEnum ofMap(String taskName) {
            for (final MapEnum value : MapEnum.values()) {
                if (value.name().equals(taskName)) {
                    return value;
                }
            }

            return null;
        }

    }

    private static class RootMap implements Map  {

        @Override
        public List map(MapArgs args) {
            // 第一层按照商家ID分片
            // 假设总共有一百万商家 每个分片处理10万商家
            List<List<Long>> ranges = doSharding(1L, 1000000L, 100000);
            return ranges;
        }
    }

    public static class MonthMap implements Map  {

        @Override
        public List map(MapArgs args) {

            // 第二层按照月分片
            // 4个季度
            List<Long> lists = JsonUtil.parseList(args.getMapResult(), Long.class);
            List<SubTask> list = new ArrayList<>();
            for (final Long id : lists) {
                for (int i = 1; i <= 4; i++) {
                    list.add(new SubTask(id, i));
                }
            }

            return list;
        }

        @Data
        @AllArgsConstructor
        public static class SubTask {
            // 商家id
            private Long id;

            // 需要处理的月份
            private Integer quarter;

        }
    }

    interface Map{
        List<Object> map(MapArgs args);
    }

    public static List<List<Long>> doSharding(Long min, Long max, int interval) {

        if (max.equals(min)) {
            return new ArrayList<>();
        }

        // 总数量
        long total = max - min + 1;

        // 计算分页总页数
        Long totalPages = total / interval;
        if (total % interval != 0) {
            totalPages++;
        }

        List<List<Long>> partitions = new ArrayList<>();
        for (Long index = 0L; index < totalPages; index++) {

            // 计算起始点 因为是从min开始所以每次需要加上一个min
            Long start = index * interval + min;

            // 结算结束点 若最后一个 start + interval - 1 > max 取max
            // 减一是保证 [start, end] 都是闭区间
            Long end = Math.min(start + interval - 1, max);
            partitions.add(Lists.newArrayList(start, end));
        }

        return partitions;
    }
}
