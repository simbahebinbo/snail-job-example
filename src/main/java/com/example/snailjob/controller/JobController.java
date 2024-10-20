package com.example.snailjob.controller;

import com.aizuda.snailjob.client.job.core.dto.JobResponseVO;
import com.example.snailjob.handler.TestAddJobHandler;
import com.example.snailjob.handler.TestQueryJobHandler;
import com.example.snailjob.handler.TestTriggerJobHandler;
import com.example.snailjob.handler.TestUpdateJobStatusHandler;
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
    private final TestQueryJobHandler testQueryJobHandler;
    private final TestTriggerJobHandler testTriggerJobHandler;
    private final TestUpdateJobStatusHandler testUpdateJobStatusHandler;

    @PostMapping("/custer/add")
    public Long addClusterJob(@RequestBody String jobName) {
        return testAddJobHandler.addClusterJob(jobName);
    }

    @PostMapping("/broadcast/add")
    public Long addBroadcastJob(@RequestBody String jobName) {
        return testAddJobHandler.addBroadcastJob(jobName);
    }

    @PostMapping("/sharding/add")
    public Long addShardingJob(@RequestBody String jobName) {
        return testAddJobHandler.addShardingJob(jobName);
    }

    @PostMapping("/map/add")
    public Long addMapJob(@RequestBody String jobName) {
        return testAddJobHandler.addMapJob(jobName);
    }

    @PostMapping("/map-reduce/add")
    public Long addMapReduceJob(@RequestBody String jobName) {
        return testAddJobHandler.addMapReduceJob(jobName);
    }

    @GetMapping("/detail/id")
    public JobResponseVO addMapReduceJob(@RequestParam("id") Long id) {
        return testQueryJobHandler.queryJob(id);
    }

    @PostMapping("/trigger/{id}")
    public Boolean triggerJob(@PathVariable("id") Long id) {
        return testTriggerJobHandler.triggerJob(id);
    }

    @PutMapping("/update/status/{id}")
    public Boolean updateJob(@PathVariable("id") Long id) {
        return testUpdateJobStatusHandler.updateJobStatus(id);
    }
}
