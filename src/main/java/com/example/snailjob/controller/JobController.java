package com.example.snailjob.controller;

import com.example.snailjob.handler.TestAddJobHandler;
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

    @PostMapping("/custer/add")
    public Long addClusterJob(@RequestBody String jobName) {
        return testAddJobHandler.addClusterJob(jobName);
    }
}
