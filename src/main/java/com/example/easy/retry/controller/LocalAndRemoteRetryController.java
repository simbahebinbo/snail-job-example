package com.example.easy.retry.controller;

import com.example.easy.retry.service.LocalRemoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/local-remote")
@Tag(name = "模拟先本地再远程重试案例", description = "先本地再远程重试案例【RetryType.LOCAL_REMOTE】")
public class LocalAndRemoteRetryController {

    @Autowired
    private LocalRemoteService localRemoteService;

    @GetMapping("/retry")
    @Operation(description = "一个简单的入门案例")
    public void localRemote() {
        localRemoteService.localRemote();
    }

    @GetMapping("/retryWithLocalRemote")
    @Operation(
        summary = "使用同步上报的方式",
        description = "async = false 代表使用同步上传的方式\n"
            +   "timeout = 1 代表超时时间为1  \n"
            +   "unit = MINUTES 代表超时时间的单位是分钟\n" +
            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remoteRetryWithLocalRemote(@Parameter(name = "params", description = "测试参数",
            schema = @Schema(type = "String", description = "测试参数", defaultValue = "test")
    )
    @RequestParam("params") String params) {
        localRemoteService.remoteRetryWithLocalRemote(params);
    }
}
