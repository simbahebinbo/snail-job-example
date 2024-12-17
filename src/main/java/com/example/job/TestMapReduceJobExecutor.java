package com.example.job;

import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.MergeReduceArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.job.core.executor.AbstractMapReduceExecutor;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.example.job.TestMapReduceJobExecutor.QuarterMap.SubTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    public ExecuteResult doJobMapExecute(MapArgs mapArgs, MapHandler mapHandler) {
        MapEnum mapEnum = MapEnum.ofMap(mapArgs.getTaskName());
        if (Objects.nonNull(mapEnum) && Objects.nonNull(mapEnum.getMap())) {
            Map map = mapEnum.getMap();
            MapEnum nextMap = mapEnum.getNextMap();
            String nextName = null;
            if (Objects.nonNull(nextMap)) {
                nextName = nextMap.name();
            }

            return mapHandler.doMap(map.map(mapArgs), nextName);
        }

        // 未找到map的任务，则说明当前需要进行处理
        JsonNode json = JsonUtil.toJson(mapArgs.getMapResult());
        SnailJobLog.LOCAL.info("LAST_MAP 开始执行 mapResult:{}", json);
        // 获取最后一次map的信息.
        SubTask subTask = JsonUtil.parseObject(json.toString(), SubTask.class);
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
    protected ExecuteResult doMergeReduceExecute(MergeReduceArgs reduceArgs) {
        List<?> reduces = reduceArgs.getReduces();
        SnailJobLog.LOCAL.info("merge reduce {}", reduces);
        return ExecuteResult.success(111);
    }

    @Getter
    private enum MapEnum {
        LAST_MAP(null, null),
        QUARTER_MAP(new QuarterMap(), LAST_MAP),
        MAP_ROOT(new RootMap(), QUARTER_MAP),
        ;

        private final Map map;
        private final MapEnum nextMap;

        MapEnum(Map map, MapEnum nextMap) {
            this.map = map;
            this.nextMap = nextMap;
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

    private static class RootMap implements Map {

        @Override
        public List map(MapArgs args) {
            // 第一层按照商家ID分片
            // 假设总共有一百万商家 每个分片处理10万商家
            List<List<Long>> ranges = doSharding(1L, 1000000L, 100000);
            return ranges;
        }
    }

    public static class QuarterMap implements Map {

        @Override
        public List map(MapArgs args) {

            // 第二层按照月分片
            // 4个季度
            JsonNode json = JsonUtil.toJson(args.getMapResult());
            List<SubTask> list = new ArrayList<>();
            for (JsonNode jsonNode : json) {
                long id = jsonNode.asLong();
                for (int i = 1; i <= 4; i++) {
                    list.add(new SubTask(id, i));
                }
            }
            return list;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SubTask {
            // 商家id
            private Long id;

            // 需要处理的月份
            private Integer quarter;

        }
    }

    interface Map {
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
