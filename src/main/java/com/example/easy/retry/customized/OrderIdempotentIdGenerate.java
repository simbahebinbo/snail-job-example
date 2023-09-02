package com.example.easy.retry.customized;

import org.apache.catalina.security.SecurityUtil;

import com.aizuda.easy.retry.client.core.IdempotentIdGenerate;
import com.aizuda.easy.retry.common.core.model.IdempotentIdContext;
import com.example.easy.retry.vo.OrderVo;

import cn.hutool.crypto.SecureUtil;


public class OrderIdempotentIdGenerate implements IdempotentIdGenerate {

    @Override
    public String idGenerate(IdempotentIdContext idempotentIdContext) throws Exception {
        Object[] args = idempotentIdContext.getArgs();
        OrderVo orderVo = (OrderVo) args[0];
        return SecureUtil.md5(orderVo.getOrderId());
    }

}
