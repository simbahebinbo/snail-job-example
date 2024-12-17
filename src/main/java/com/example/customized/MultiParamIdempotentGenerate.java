package com.example.customized;

import com.aizuda.snailjob.client.core.IdempotentIdGenerate;
import com.aizuda.snailjob.common.core.model.IdempotentIdContext;
import com.example.vo.OrderVo;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiParamIdempotentGenerate implements IdempotentIdGenerate {

    @Override
    public String idGenerate(IdempotentIdContext idempotentIdContext) throws Exception {
        Object[] args = idempotentIdContext.getArgs();
        String uuid = (String) args[0];
        Integer intVal = (Integer) args[1];
        Double doubleVal = (Double) args[2];
        Character character = (Character) args[3];
        OrderVo orderVo = (OrderVo) args[4];
        log.info("测试多参数解析,String类型:{},Integer类型:{},Double类型:{}," +
                "Character类型:{},对象类型:{}", uuid, intVal, doubleVal, character, orderVo);
        return SecureUtil.md5(orderVo.getOrderId());
    }
}
