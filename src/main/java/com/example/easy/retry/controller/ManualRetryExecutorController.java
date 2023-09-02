package com.example.easy.retry.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.easy.retry.service.ManualRetryExecutorMethodService;

@RestController
@RequestMapping("/manual")
@Api(value = "模拟手动执行重试案例", tags = "手动执行重试上报")
public class ManualRetryExecutorController {

    @Autowired
    private ManualRetryExecutorMethodService manualRetryExecutorMethodService;
    @ApiOperation(
        value = "手动重试",
        notes = "❤️如果不知道这个手动重试的使用场景可以参考: https://www.easyretry.com/pages/406a68/#%E5%8F%91%E9%80%81mq%E5%9C%BA%E6%99%AF \n"
            + "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @GetMapping("/retry")
    public void remoteRetryWithCallback(@ApiParam(name = "params", value = "测试参数", defaultValue = "test") @RequestParam("params") String params){
        manualRetryExecutorMethodService.myExecutorMethod(params);
    }
}
