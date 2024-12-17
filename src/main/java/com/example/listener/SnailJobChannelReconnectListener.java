package com.example.listener;

import com.aizuda.snailjob.client.common.event.SnailChannelReconnectEvent;
import com.aizuda.snailjob.common.log.SnailJobLog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaowoniu
 * @date 2024-03-14 21:58:18
 * @since 3.1.0
 */
@Component
public class SnailJobChannelReconnectListener implements ApplicationListener<SnailChannelReconnectEvent> {
    @Override
    public void onApplicationEvent(SnailChannelReconnectEvent event) {
        SnailJobLog.LOCAL.info("这个一个重连事件");
    }
}
