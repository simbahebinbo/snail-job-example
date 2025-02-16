package com.example.snailjob.listener;

import com.aizuda.snailjob.client.common.event.SnailClientStartingEvent;
import com.aizuda.snailjob.common.log.SnailJobLog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaowoniu
 * @date 2024-03-14 22:00:58
 * @since 3.1.0
 */
@Component
public class SnailJobStartingListener implements ApplicationListener<SnailClientStartingEvent> {
    @Override
    public void onApplicationEvent(SnailClientStartingEvent event) {
        SnailJobLog.LOCAL.info("这是一个SnailJob启动事件");
    }
}
