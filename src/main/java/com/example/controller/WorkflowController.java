package com.example.controller;

import com.example.handler.TestTriggerJobHandler;
import com.example.handler.TestUpdateJobStatusHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author opensnail
 * @date 2024-10-19 10:41:25
 * @since sj_1.2.0-beta2
 */
@RestController
@RequestMapping("/open-api/workflow")
@Tag(name = "JobOpenApi", description = "通过OpenApi可以灵活的实现对的Workflow触发和更新状态功能")
@RequiredArgsConstructor
public class WorkflowController {
    private final TestTriggerJobHandler testTriggerJobHandler;
    private final TestUpdateJobStatusHandler testUpdateJobStatusHandler;

    @Operation(
            description = "手动触发任务"
    )
    @PostMapping("/trigger/{id}")
    public Boolean triggerJob(@PathVariable("id") Long id) {
        return testTriggerJobHandler.triggerWorkFlow(id);
    }

    @Operation(
            description = "根据id更新任务的状态",
            summary = "0:关闭 1:开启"
    )
    @PutMapping("/update/status/{id}/{status}")
    public Boolean updateJob(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        return testUpdateJobStatusHandler.updateWorkFlowStatus(id, status);
    }
}
