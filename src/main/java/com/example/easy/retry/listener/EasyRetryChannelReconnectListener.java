package com.example.easy.retry.listener;

import com.aizuda.easy.retry.client.common.event.SnailChannelReconnectEvent;
import com.aizuda.easy.retry.common.log.EasyRetryLog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaowoniu
 * @date 2024-03-14 21:58:18
 * @since 3.1.0
 */
@Component
public class EasyRetryChannelReconnectListener implements ApplicationListener<SnailChannelReconnectEvent> {
    @Override
    public void onApplicationEvent(SnailChannelReconnectEvent event) {
        EasyRetryLog.LOCAL.info("这个一个重连事件");
    }
}
