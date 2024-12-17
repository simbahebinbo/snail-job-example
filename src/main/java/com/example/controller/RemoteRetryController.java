package com.example.controller;

import java.util.Random;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vo.OrderVo;
import com.example.service.RemoteRetryService;

@RestController
@RequestMapping("/remote")
@Tag(name = "模拟远程重试案例", description = "远程重试案例【RetryType.ONLY_REMOTE】")
public class RemoteRetryController {

    @Autowired
    private RemoteRetryService remoteRetryService;

    /**
     * 一个最简单的远程调用案例
     */
    @GetMapping("/retry")
    @Operation(
            summary = "一个简单的入门案例",
            description = "🥇不进过本地重试阶段，直接上报到服务端\n" +
                    "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remote(@Parameter(name = "params", description = "测试参数",
            schema = @Schema(defaultValue = "test", type = "String", description = "测试参数"))
                       @RequestParam("params") String params) {
        remoteRetryService.remoteRetry(params);
    }

    /**
     * 一个最简单的远程调用案例
     * schema = @Schema(type = "String", defaultValue = "test", description = "测试参数", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
     */
    @GetMapping("/retry/sync")
    @Operation(
            summary = "一个简单的以同步方式远程重试入门案例",
            description = "🥇不进过本地重试阶段，直接上报到服务端\n" +
                    "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remoteSync(@Parameter(name = "params", description = "测试参数",
            schema = @Schema(type = "string", defaultValue = "test", description = "测试参数"))
                           @RequestParam("params") String params) {
        remoteRetryService.remoteSync(params);
    }

    /**
     * 使用自定义的幂等Id生成规则
     */
    @PostMapping("/retryWithIdempotentId")
    @Operation(
            summary = "使用自定义的幂等Id生成规则",
            description =
                    "具体实现类参考: https://gitee.com/zhangyutongxue/easy-retry-demo/blob/master/easy-retry-springboot/src/main/java/com/maluxinyu/easyretry/customized/OrderIdempotentIdGenerate.java\n"
                            + "具体的幂等策略参考: https://www.easyretry.com/pages/97cde9/#%E2%9A%A1%E5%B9%82%E7%AD%89id-idempotentid \n"
                            +
                            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void remoteRetryWithIdempotentId(@RequestBody OrderVo orderVo) {
        remoteRetryService.remoteRetryWithIdempotentId(orderVo);
    }

    /**
     * 使用自定义的单参数幂等Id生成规则
     */
    @Operation(
            summary = "使用自定义的单参数幂等Id生成规则",
            description =
                    "具体实现类参考: https://gitee.com/zhangyutongxue/easy-retry-demo/blob/master/easy-retry-springboot/src/main/java/com/maluxinyu/easyretry/customized/SingleParamIdempotentGenerate.java\n"
                            +
                            "🥇通过对orderId进行md5加密生成幂等ID, 具体的幂等策略参考: https://www.easyretry.com/pages/97cde9/#%E2%9A%A1%E5%B9%82%E7%AD%89id-idempotentid \n"
                            +
                            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @GetMapping("/retryWithSingleParamIdempotentGenerate")
    public void retryWithSingleParamIdempotentGenerate(
            @Parameter(name = "params", description = "测试参数",
                    schema = @Schema(type = "string", description = "测试参数", defaultValue = "test"))
            @RequestParam("params") String params) {
        remoteRetryService.retryWithSingleParamIdempotentGenerate(params);
    }

    /**
     * 使用自定义的多参数幂等Id生成规则
     */
    @PostMapping("/retryWithMulParamIdempotentGenerate")
    @Operation(
            summary = "使用自定义的多参数幂等Id生成规则",
            description =
                    "具体实现类参考: https://gitee.com/zhangyutongxue/easy-retry-demo/blob/master/easy-retry-springboot/src/main/java/com/maluxinyu/easyretry/customized/MultiParamIdempotentGenerate.java\n"
                            +
                            "🥇通过对orderId进行md5加密生成幂等ID, 具体的幂等策略参考: https://www.easyretry.com/pages/97cde9/#%E2%9A%A1%E5%B9%82%E7%AD%89id-idempotentid \n"
                            +
                            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    public void retryWithMulParamIdempotentGenerate(@RequestBody OrderVo orderVo) {
        Random random = new Random();
        remoteRetryService.retryWithMulParamIdempotentGenerate(
                String.valueOf(UUID.randomUUID()),
                random.nextInt(),
                random.nextDouble(),
                'a',
                orderVo
        );
    }

    /**
     * 使用自定义的异常处理类 OrderRetryMethod
     */
    @Operation(
            summary = "指定自定义的异常处理类",
            description =
                    "具体实现类参考: https://gitee.com/zhangyutongxue/easy-retry-demo/blob/master/easy-retry-springboot/src/main/java/com/maluxinyu/easyretry/customized/OrderRetryMethod.java\n"
                            +
                            "🥇什么是自定义的异常处理类: https://www.easyretry.com/pages/540554/#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E5%99%A8\n"
                            +
                            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @PostMapping("/retryWithRetryMethod")
    public void remoteRetryWithRetryMethod(@RequestBody OrderVo orderVo) {
        remoteRetryService.remoteRetryWithRetryMethod(orderVo);
    }

    /**
     * 使用自定义的回调函数
     */
    @Operation(
            summary = "使用自定义的回调函数",
            description =
                    "具体实现类参考: https://gitee.com/zhangyutongxue/easy-retry-demo/blob/master/easy-retry-springboot/src/main/java/com/maluxinyu/easyretry/customized/OrderCompleteCallback.java\n"
                            +
                            "🥇什么情况下触发回调: https://www.easyretry.com/pages/97cde9/#%E2%9A%A1%E5%9B%9E%E8%B0%83\n"
                            +
                            "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @PostMapping("/retryWithCallback/{scene}")
    public void remoteRetryWithCallback(@Parameter(name = "scene", description = "场景 FINISH/MAX_COUNT",
            schema = @Schema(type = "string", description = "测试参数", defaultValue = "FINISH"))
                                        @PathVariable("scene") String scene, @RequestBody OrderVo orderVo) {
        remoteRetryService.remoteRetryWithCompleteCallback(scene, orderVo);
    }

    /**
     * 指定bizNo
     */
    @Operation(
            summary = "指定bizNo",
            description = "🥇什么是bizNo: https://www.easyretry.com/pages/540554/#bizno%E7%94%9F%E6%88%90%E5%99%A8\n"
                    +
                    "📢查看任务列表: http://preview.easyretry.com/#/retry-task/list"
    )
    @PostMapping("/remoteRetryWithBizNo")
    public void remoteRetryWithBizNo(@RequestBody OrderVo orderVo) {
        remoteRetryService.remoteRetryWithBizNo(orderVo);
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
        return remoteRetryService.localRetryWithTwoRetryMethod(params);
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
        return remoteRetryService.localRetryWithPropagationRequired(params);
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
        return remoteRetryService.localRetryWithPropagationRequiredNew(params);
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
        return remoteRetryService.localRetryWithTryCatch1(params);
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
        return remoteRetryService.localRetryWithTryCatch2(params);
    }
}
