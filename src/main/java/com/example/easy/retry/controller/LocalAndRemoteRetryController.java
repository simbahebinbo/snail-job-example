package com.example.easy.retry.controller;

import com.example.easy.retry.service.LocalRemoteService;
import com.example.easy.retry.service.LocalRetryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/local-remote")
@Api(value = "模拟先本地再远程重试案例", tags = "先本地再远程重试案例【RetryType.LOCAL_REMOTE】")
public class LocalAndRemoteRetryController {

    @Autowired
    private LocalRemoteService localRemoteService;

    @GetMapping("/retry")
    @ApiOperation(value = "一个简单的入门案例")
    public void localRemote() {
        localRemoteService.localRemote();
    }

    @GetMapping("/retryWithLocalRemote")
    @ApiOperation(
        value = "使用同步上报的方式",
        notes = "async = false 代表使用同步上传的方式\n"
            +   "timeout = 1 代表超时时间为1  \n"
            +   "unit = MINUTES 代表超时时间的单位是分钟\n" +
            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remoteRetryWithLocalRemote(@ApiParam(name = "params", value = "测试参数", defaultValue = "test")
    @RequestParam("params") String params) {
        localRemoteService.remoteRetryWithLocalRemote(params);
    }
}
