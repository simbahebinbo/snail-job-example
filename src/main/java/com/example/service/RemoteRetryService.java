package com.example.service;

import com.example.vo.OrderVo;

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

    boolean localRetryWithPropagationRequired(String params);

    boolean localRetryWithPropagationRequiredNew(String params);

    boolean localRetryWithTryCatch1(String params);

    boolean localRetryWithTryCatch2(String params);

    boolean localRetryWithTwoRetryMethod(String params);
}
