package com.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ManualRetryExecutorMethodService;

@RestController
@RequestMapping("/manual")
@Tag(name = "模拟手动执行重试案例", description = "手动执行重试上报")
public class ManualRetryExecutorController {

    @Autowired
    private ManualRetryExecutorMethodService manualRetryExecutorMethodService;

    @Operation(
            summary = "手动重试",
            description = "❤️如果不知道这个手动重试的使用场景可以参考: https://www.easyretry.com/pages/406a68/#%E5%8F%91%E9%80%81mq%E5%9C%BA%E6%99%AF \n"
                    + "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @GetMapping("/retry")
    public void remoteRetryWithCallback(@Parameter(name = "params", description = "测试参数", schema = @Schema(type = "string", description = "测试参数", defaultValue = "test"))
                                        @RequestParam("params") String params) {
        manualRetryExecutorMethodService.myExecutorMethod(params);
    }
}
