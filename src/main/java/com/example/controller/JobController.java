package com.example.controller;

import com.aizuda.snailjob.client.job.core.dto.JobResponseVO;
import com.example.handler.TestAddJobHandler;
import com.example.handler.TestQueryJobHandler;
import com.example.handler.TestTriggerJobHandler;
import com.example.handler.TestUpdateJobHandler;
import com.example.handler.TestUpdateJobStatusHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author opensnail
 * @date 2024-10-19 10:41:25
 * @since sj_1.2.0-beta2
 */
@RestController
@RequestMapping("/open-api/job")
@Tag(name = "JobOpenApi", description = "通过OpenApi可以灵活的实现对Job的增、改、查功能")
@RequiredArgsConstructor
public class JobController {
    private final TestAddJobHandler testAddJobHandler;
    private final TestUpdateJobHandler testUpdateJobHandler;
    private final TestQueryJobHandler testQueryJobHandler;
    private final TestTriggerJobHandler testTriggerJobHandler;
    private final TestUpdateJobStatusHandler testUpdateJobStatusHandler;

    @Operation(
            description = "添加集群模式的定时任务"
    )
    @PostMapping("/custer/add")
    public Long addClusterJob(@RequestBody String jobName) {
        return testAddJobHandler.addClusterJob(jobName);
    }

    @Operation(
            description = "添加广播模式的定时任务"
    )
    @PostMapping("/broadcast/add")
    public Long addBroadcastJob(@RequestBody String jobName) {
        return testAddJobHandler.addBroadcastJob(jobName);
    }

    @Operation(
            description = "添加静态分片模式的定时任务"
    )
    @PostMapping("/sharding/add")
    public Long addShardingJob(@RequestBody String jobName) {
        return testAddJobHandler.addShardingJob(jobName);
    }

    @Operation(
            description = "添加Map模式的定时任务"
    )
    @PostMapping("/map/add")
    public Long addMapJob(@RequestBody String jobName) {
        return testAddJobHandler.addMapJob(jobName);
    }

    @Operation(
            description = "添加MapReduce模式的定时任务"
    )
    @PostMapping("/map-reduce/add")
    public Long addMapReduceJob(@RequestBody String jobName) {
        return testAddJobHandler.addMapReduceJob(jobName);
    }

    @Operation(
            description = "更新集群模式的定时任务"
    )
    @PutMapping("/custer/update")
    public Boolean updateClusterJob(@RequestBody Long id) {
        return testUpdateJobHandler.updateClusterJob(id);
    }

    @Operation(
            description = "更新广播模式的定时任务"
    )
    @PutMapping("/broadcast/update")
    public Boolean updateBroadcastJob(@RequestBody Long id) {
        return testUpdateJobHandler.updateBroadcastJob(id);
    }

    @Operation(
            description = "更新静态分片模式的定时任务"
    )
    @PutMapping("/sharding/update")
    public Boolean addShardingJob(@RequestBody Long id) {
        return testUpdateJobHandler.updateShardingJob(id);
    }

    @Operation(
            description = "更新Map模式的定时任务"
    )
    @PutMapping("/map/update")
    public Boolean updateMapJob(@RequestBody Long id) {
        return testUpdateJobHandler.updateMapJob(id);
    }

    @Operation(
            description = "更新MapReduce模式的定时任务"
    )
    @PutMapping("/map-reduce/update")
    public Boolean updateMapReduceJob(@RequestBody Long id) {
        return testUpdateJobHandler.updateMapReduceJob(id);
    }

    @Operation(
            description = "通过任务id查询任务的详情"
    )
    @GetMapping("/detail/{id}")
    public JobResponseVO addMapReduceJob(@PathVariable("id") Long id) {
        return testQueryJobHandler.queryJob(id);
    }

    @Operation(
            description = "手动触发任务"
    )
    @PostMapping("/trigger/{id}")
    public Boolean triggerJob(@PathVariable("id") Long id) {
        return testTriggerJobHandler.triggerJob(id);
    }

    @Operation(
            description = "根据id更新任务的状态",
            summary = "0:关闭 1:开启"
    )
    @PutMapping("/update/status/{id}/{status}")
    public Boolean updateJob(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        return testUpdateJobStatusHandler.updateJobStatus(id, status);
    }
}
