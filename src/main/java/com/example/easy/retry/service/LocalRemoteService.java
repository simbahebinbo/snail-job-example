package com.example.easy.retry.service;

/**
 * @author:  www.byteblogs.com
 * @date : 2023-09-06 09:02
 */
public interface LocalRemoteService {

    void localRemote();

    String remoteRetryWithLocalRemote(String requestId);
}
