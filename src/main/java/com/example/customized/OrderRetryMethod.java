package com.example.customized;

import org.springframework.stereotype.Component;

import com.aizuda.snailjob.client.core.strategy.ExecutorMethod;
import com.example.vo.OrderVo;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderRetryMethod implements ExecutorMethod {
    @Override
    public Object doExecute(Object params) {
        // 将特定类型的 Object 对象指定为 Object[]
        Object[] args = (Object[]) params;
        OrderVo orderVo = (OrderVo) args[0];
        log.info("进入指定自定义的异常处理类, 参数信息是{}", JSONUtil.toJsonStr(orderVo));
        throw new ArithmeticException("自定义的异常处理类处理");
    }
}
