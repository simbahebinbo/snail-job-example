package com.example.executor;

import com.aizuda.snailjob.client.core.annotation.ExecutorMethodRegister;
import com.aizuda.snailjob.client.core.strategy.ExecutorMethod;
import com.example.vo.OrderVo;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@ExecutorMethodRegister(scene = ManualRetryExecutorTask.SCENE)
@Slf4j
public class ManualRetryExecutorTask implements ExecutorMethod {
    /**
     * 自定义场景值
     */
    public final static String SCENE = "manualRetry";

    @Override
    public Object doExecute(Object params) {
        // 将特定类型的 Object 对象指定为 Object[]
        Object[] args = (Object[]) params;
        OrderVo orderVo = JSONUtil.toBean(JSONUtil.toJsonStr(args[0]), OrderVo.class);
        log.info("进入手动重试方法,参数信息是{}", JSONUtil.toJsonStr(orderVo));
        return true;
    }
}
