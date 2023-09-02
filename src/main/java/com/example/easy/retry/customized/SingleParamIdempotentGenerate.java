package com.example.easy.retry.customized;

import com.aizuda.easy.retry.client.core.IdempotentIdGenerate;
import com.aizuda.easy.retry.common.core.model.IdempotentIdContext;
import com.example.easy.retry.vo.OrderVo;

import cn.hutool.crypto.SecureUtil;

public class SingleParamIdempotentGenerate implements IdempotentIdGenerate {

    @Override
    public String idGenerate(IdempotentIdContext idempotentIdContext) throws Exception {
        Object[] args = idempotentIdContext.getArgs();
        String params = (String) args[0];
        return SecureUtil.md5(params);
    }
}
