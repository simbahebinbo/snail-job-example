package com.example.easy.retry.listener;

import com.aizuda.easy.retry.client.common.event.EasyRetryStartedEvent;
import com.aizuda.easy.retry.client.common.event.EasyRetryStartingEvent;
import com.aizuda.easy.retry.common.log.EasyRetryLog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaowoniu
 * @date 2024-03-14 22:00:58
 * @since 3.1.0
 */
@Component
public class EasyRetryStartedListener implements ApplicationListener<EasyRetryStartedEvent> {
    @Override
    public void onApplicationEvent(EasyRetryStartedEvent event) {
        EasyRetryLog.LOCAL.info("这是一个EasyRetry启动完成事件");
    }
}
