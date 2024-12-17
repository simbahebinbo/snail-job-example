package com.example.controller;

import com.example.service.LocalRemoteService;
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
                    + "timeout = 1 代表超时时间为1  \n"
                    + "unit = MINUTES 代表超时时间的单位是分钟\n" +
                    "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remoteRetryWithLocalRemote(@Parameter(name = "params", description = "测试参数",
            schema = @Schema(type = "string", description = "测试参数", defaultValue = "test")
    )
                                           @RequestParam("params") String params) {
        localRemoteService.remoteRetryWithLocalRemote(params);
    }

    @GetMapping("/localRetryWithTwoRetryMethod")
    /**
     *
     * 方法内部存在两个串行的方法retryMethod1、retryMethod1 如下所属
     * public boolean localRetryWithTwoRetryMethod(final String params) {
     *         retryHandler.retryMethod1(params);
     *         retryHandler.retryMethod1(params);
     *         return true;
     *     }
     * params: 1 => 则retryMethod1触发重试
     * params: 2 => 则retryMethod2触发重试
     * params: 3 => 则retryMethod1随机触发重试, 若retryMethod1重试成功，则retryMethod2一定触发重试否则只触发retryMethod1重试
     *
     */
    @Operation(
            summary = "N个串行执行的方法触发重试的场景",
            description = "方法内部存在两个串行的方法retryMethod1、retryMethod1\n" +
                    "params: 1 => 则retryMethod1触发重试\n" +
                    "params: 2 => 则retryMethod2触发重试\n" +
                    "params: 3 => 则retryMethod1随机触发重试, 若retryMethod1重试成功，则retryMethod2一定触发重试否则只触发retryMethod1重试"
    )
    public boolean localRetryWithTwoRetryMethod(@RequestParam("params") String params) {
        return localRemoteService.localRetryWithTwoRetryMethod(params);
    }

    /**
     * 外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED,
     * 只执行入口方法重试
     */
    @GetMapping("/localRetryWithPropagationRequired")
    @Operation(
            description = "外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED, 只执行入口方法重试",
            summary = "外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED, 只执行入口方法重试"
    )
    public boolean localRetryWithPropagationRequired(@RequestParam("params") String params) {
        return localRemoteService.localRetryWithPropagationRequired(params);
    }

    /**
     * 外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED_NEW,
     * 外部和内部方法都触发重试
     */
    @GetMapping("/localRetryWithPropagationRequiredNew")
    @Operation(
            description = "外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED_NEW,外部和内部方法都触发重试",
            summary = "外部方法传播机制为REQUIRED，内部方法传播机制为REQUIRED_NEW,外部和内部方法都触发重试"
    )
    public boolean localRetryWithPropagationRequiredNew(@RequestParam("params") String params) {
        return localRemoteService.localRetryWithPropagationRequiredNew(params);
    }

    /**
     * 内部方法传播机制为REQUIRED_NEW，且异常被try catch捕获，内部方法触发重试，外部方法不会触发重试
     */
    @GetMapping("/localRetryWithTryCatch1")
    @Operation(
            description = "",
            summary = "内部方法传播机制为REQUIRED_NEW，且异常被try catch捕获，内部方法触发重试，外部方法不会触发重试"
    )
    public boolean localRetryWithTryCatch1(@RequestParam("params") String params) {
        return localRemoteService.localRetryWithTryCatch1(params);
    }

    /**
     * 内部方法传播机制为REQUIRED，且异常被try catch捕获，内部方法不会触发重试，外部方法也不会触发重试
     */
    @GetMapping("/localRetryWithTryCatch2")
    @Operation(
            description = "",
            summary = "内部方法传播机制为REQUIRED，且异常被try catch捕获，内部方法不会触发重试，外部方法也不会触发重试"
    )
    public boolean localRetryWithTryCatch2(@RequestParam("params") String params) {
        return localRemoteService.localRetryWithTryCatch2(params);
    }
}
