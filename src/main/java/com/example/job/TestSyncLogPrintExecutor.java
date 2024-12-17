package com.example.job;

import com.aizuda.snailjob.client.common.log.report.LogMeta;
import com.aizuda.snailjob.client.common.log.support.SnailJobLogManager;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.job.core.executor.JobContextManager;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.model.JobContext;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.aizuda.snailjob.common.log.enums.LogTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author PT
 * @date 2024-09-02 上午10:50
 * @Description 线程异步情况下日志打印，场景：定时调用接口更新数据，
 * 由于数据量每天大小不一致，无法界定执行时间，所以需要异步执行，但是异步执行时日志也需要进行打印
 */
@Slf4j
@Component
public class TestSyncLogPrintExecutor {

    @JobExecutor(name = "TestSyncLogPrintExecutor")
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        JobContext jobContext = JobContextManager.getJobContext();
        LogMeta logMeta = SnailJobLogManager.getLogMeta();
        LogTypeEnum logType = SnailJobLogManager.getLogType();

        //此处简单模拟开启新线程执行，实际生产建议使用线程池执行
        new Thread(() -> {
            JobContextManager.setJobContext(jobContext);
            SnailJobLogManager.setLogMeta(logMeta);
            SnailJobLogManager.setLogType(logType);
            //执行业务
            doSomething();
        });
        return ExecuteResult.success();
    }

    private void doSomething() {
        try {
            TimeUnit.SECONDS.sleep(10);
            SnailJobLog.REMOTE.info("测试异步打印");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
