package com.example.easy.retry.service;

import com.example.easy.retry.vo.OrderVo;

/**
 * @author: www.byteblogs.com
 * @date : 2023-09-06 09:04
 */
public interface RemoteRetryService {

    void remoteRetry(String params);

    void remoteSync(String params);

    boolean remoteRetryWithIdempotentId(OrderVo orderVo);

    boolean retryWithSingleParamIdempotentGenerate(String params);

    boolean retryWithMulParamIdempotentGenerate(String uuid, Integer intVal, Double doubleVal,
        Character character, OrderVo orderVo);

    boolean remoteRetryWithRetryMethod(OrderVo orderVo);

    boolean remoteRetryWithCompleteCallback(String scene, OrderVo orderVo);

    boolean remoteRetryWithBizNo(OrderVo orderVo);
}
