package com.example.controller;

import com.example.vo.OrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LocalRetryService;

@RestController
@RequestMapping("/local")
@Tag(name = "模拟本地重试", description = "本地重试案例 【RetryType.ONLY_LOCAL】")
public class LocalRetryController {

    @Autowired
    private LocalRetryService localRetryService;

    @GetMapping("/retry")
    @Operation(
            summary = "一个简单的入门案例",
            description = "🥇仅仅在本地进行内存重试\n" +
                    "📢任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void onlyLocalRetry(@Param(value = "测试参数") @RequestParam("params") String params) {
        localRetryService.localRetry(params);
    }

    @GetMapping("/localRetryWithAnnoOnInterface")
    @Operation(
            summary = "@Retryable在接口上执行重试"
    )
    public void localRetryWithAnnoOnInterface(
            @Parameter(name = "params", description = "测试参数", in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", description = "测试参数"))
            @RequestParam("params") String params) {
        localRetryService.localRetryWithAnnoOnInterface(params);
    }

    @GetMapping("/withBasicParams")
    @Operation(
            summary = "指定基础参数",
            description = "localTimes 本地重试次数\n" +
                    "localInterval 本地重试间隔时间(默认单位为秒)\n" +
                    "unit 超时时间单位\n" +
                    "本案例设置为本地重试4次,每次重试之间间隔10s"
    )
    public void localRetryWithBasicParams(@Parameter(name = "params") String params) {
        localRetryService.localRetryWithBasicParams(params);
    }

    @GetMapping("/includeException")
    @Operation(
            summary = "指定异常参数",
            description = "include参数指的是当我们遭遇到指定异常时进行重试\n" +
                    "在这个案例中我们处理两个场景:\n" +
                    "抛出指定异常,例如抛出自定义的ParamException异常，观察是否会重试\n" +
                    "抛出非指定异常,例如我们在这里产生一个异常,观察是否会重试\n" +
                    "注意:如果此时我们在include 中指定参数为BusinessException(ParamException的父类),同样也会进行重试逻辑\n" +
                    "下面参数可以指定:NullPointerException, ParamException"
    )
    public void localRetryIncludeException(@Parameter(name = "type", description = "异常类型", in = ParameterIn.QUERY,
            schema = @Schema(type = "string", description = "异常类型")) @RequestParam("type") String type) {
        localRetryService.localRetryIncludeException(type);
    }

    @GetMapping("/excludeException")
    @Operation(
            summary = "非指定异常参数进行重试",
            description = "这个参数的作用和include是相反的\n" +
                    "exclude参数指的是当我们遇到指定异常时则不会进行重试\n" +
                    "比如在下述案例中我们指定了遇到ParamException和ArithmeticException后不进行重试"
    )
    @Parameters({
            @Parameter(name = "type", description = "异常类型", in = ParameterIn.QUERY)
    })
    public void localRetryExcludeException(@RequestParam("type") String type) {
        localRetryService.localRetryExcludeException(type);
    }

    @GetMapping("/isThrowException")
    @Operation(
            summary = "本地重试完成后不抛出异常",
            description = ""
    )
    @Parameters({
            @Parameter(name = "params", description = "异常类型", in = ParameterIn.QUERY)
    })
    public void localRetryIsThrowException(@RequestParam("params") String params) {
        localRetryService.localRetryIsThrowException(params);
    }

    @PostMapping("/localRetryWithRetryMethod")
    /**
     * 使用自定义的异常处理类 OrderRetryMethod
     */
    @Operation(
            description = "指定自定义的异常处理类",
            summary = "🥇什么是自定义的异常处理类: https://www.easyretry.com/pages/540554/#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E5%99%A8"
    )
    public boolean localRetryWithRetryMethod(@RequestBody OrderVo orderVo) {
        return localRetryService.localRetryWithRetryMethod(orderVo);
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
        return localRetryService.localRetryWithTwoRetryMethod(params);
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
        return localRetryService.localRetryWithPropagationRequired(params);
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
        return localRetryService.localRetryWithPropagationRequiredNew(params);
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
        return localRetryService.localRetryWithTryCatch1(params);
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
        return localRetryService.localRetryWithTryCatch2(params);
    }
}
