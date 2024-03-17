package com.example.easy.retry.listener;

import com.aizuda.easy.retry.client.common.event.EasyRetryClosingEvent;
import com.aizuda.easy.retry.client.common.event.EasyRetryStartedEvent;
import com.aizuda.easy.retry.common.log.EasyRetryLog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaowoniu
 * @date 2024-03-14 22:00:58
 * @since 3.1.0
 */
@Component
public class EasyRetryClosingListener implements ApplicationListener<EasyRetryClosingEvent> {
    @Override
    public void onApplicationEvent(EasyRetryClosingEvent event) {
        EasyRetryLog.LOCAL.info("这是一个EasyRetry开始关闭事件");
    }
}
