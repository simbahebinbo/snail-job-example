package com.example.easy.retry.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.easy.retry.service.LocalRetryService;

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
    @ApiOperation(
        value = "@Retryable在接口上执行重试"
    )
    public void localRetryWithAnnoOnInterface(@ApiParam(name = "params", value = "测试参数", defaultValue = "test") @RequestParam("params") String params){
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
    @ApiOperation(
            value = "指定自定义的异常处理类",
            notes ="🥇什么是自定义的异常处理类: https://www.easyretry.com/pages/540554/#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E5%99%A8"
    )
    public boolean localRetryWithRetryMethod(@RequestBody OrderVo orderVo){
       return localRetryService.localRetryWithRetryMethod(orderVo);
    }
}
