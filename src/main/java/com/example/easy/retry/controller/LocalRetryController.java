package com.example.easy.retry.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.easy.retry.service.LocalRetryService;

@RestController
@RequestMapping("/local")
@Api(value = "模拟本地重试", tags = "本地重试案例 【RetryType.ONLY_LOCAL】")
public class LocalRetryController {

    @Autowired
    private LocalRetryService localRetryService;

    @GetMapping("/retry")
    @ApiOperation(
            value = "一个简单的入门案例",
            notes = "🥇仅仅在本地进行内存重试\n" +
                    "📢任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void onlyLocalRetry(@ApiParam(name = "params", value = "测试参数", defaultValue = "test") @RequestParam("params") String params){
        localRetryService.localRetry(params);
    }

    @GetMapping("/withBasicParams")
    @ApiOperation(
            value = "指定基础参数",
            notes = "localTimes 本地重试次数\n" +
                    "localInterval 本地重试间隔时间(默认单位为秒)\n" +
                    "unit 超时时间单位\n" +
                    "本案例设置为本地重试4次,每次重试之间间隔10s"
    )
    public void localRetryWithBasicParams(@ApiParam(name = "params", value = "测试参数", defaultValue = "test")  @RequestParam("params") String params){
        localRetryService.localRetryWithBasicParams(params);
    }

    @GetMapping("/includeException")
    @ApiOperation(
            value = "指定异常参数",
            notes = "include参数指的是当我们遭遇到指定异常时进行重试\n" +
                    "在这个案例中我们处理两个场景:\n" +
                    "抛出指定异常,例如抛出自定义的ParamException异常，观察是否会重试\n" +
                    "抛出非指定异常,例如我们在这里产生一个异常,观察是否会重试\n" +
                    "注意:如果此时我们在include 中指定参数为BusinessException(ParamException的父类),同样也会进行重试逻辑\n" +
                    "下面参数可以指定:NullPointerException, ParamException"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "异常类型", dataType = "String", paramType = "query",
                    allowableValues = "NullPointerException,ParamException", defaultValue = "ParamException")
    })
    public void localRetryIncludeException(@RequestParam("type") String type){
        localRetryService.localRetryIncludeException(type);
    }

    @GetMapping("/excludeException")
    @ApiOperation(
            value = "非指定异常参数进行重试",
            notes = "这个参数的作用和include是相反的\n" +
                    "exclude参数指的是当我们遇到指定异常时则不会进行重试\n" +
                    "比如在下述案例中我们指定了遇到ParamException和ArithmeticException后不进行重试"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "异常类型", dataType = "String", paramType = "query",
                    allowableValues = "ParamException,ArithmeticException", defaultValue = "ParamException")
    })
    public void localRetryExcludeException(@RequestParam("type") String type){
        localRetryService.localRetryExcludeException(type);
    }

    @GetMapping("/isThrowException")
    @ApiOperation(
            value = "本地重试完成后不抛出异常",
            notes = ""
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "异常类型", dataType = "String", paramType = "query")
    })
    public void localRetryIsThrowException(@RequestParam("params") String params){
        localRetryService.localRetryIsThrowException(params);
    }
}
