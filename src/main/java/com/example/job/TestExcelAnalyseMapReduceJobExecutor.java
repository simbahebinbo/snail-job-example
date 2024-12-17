package com.example.job;

import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MapExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MergeReduceExecutor;
import com.aizuda.snailjob.client.job.core.annotation.ReduceExecutor;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.job.core.dto.MergeReduceArgs;
import com.aizuda.snailjob.client.job.core.dto.ReduceArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONArray;
import com.example.bo.PhoneNumberBo;
import com.example.bo.PhoneNumberCheckBo;
import com.example.listener.PhoneNumberExcelListener;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析校验Excel中的手机号，统计出错手机号数量，并返回错误手机号详情
 *
 * @author JichenWang
 * @since 2024/6/27 19:52
 */
@Slf4j
@Component
@JobExecutor(name = "TestExcelAnalyseMapReduceJobExecutor")
public class TestExcelAnalyseMapReduceJobExecutor {

    private final Integer BATCH_SIZE = 100;

    /**
     * 处理手机号文件信息，将文档中的手机号进行分组
     * 比如文档中的手机号总量为307条，每100条一个分组，分组结果为[{0,99}, {100, 199}, {200,299}, {300, 307}]
     *
     * @return ExecuteResult
     * @author JichenWang
     * @since 2024/6/29 14:03
     */
    @MapExecutor
    public ExecuteResult rootMapExecute(MapArgs mapArgs, MapHandler<List<Long>> mapHandler) {
        List<List<Long>> ranges = null;
        // 先获取文件总行数，便于分组
        try {
            @Cleanup InputStream numberInputStream = getClass().getClassLoader().getResourceAsStream("doc/number.xlsx");
            final PhoneNumberCheckBo phoneNumberCheckBo = new PhoneNumberCheckBo();
            PhoneNumberExcelListener phoneNumberExcelListener = new PhoneNumberExcelListener(phoneNumberCheckBo, true, BATCH_SIZE);
            EasyExcel.read(numberInputStream, PhoneNumberBo.class, phoneNumberExcelListener).sheet().headRowNumber(1).doReadSync();

            // 设置区间范围
            ranges = TestMapReduceJobExecutor.doSharding(0L, phoneNumberCheckBo.getTotal(), BATCH_SIZE);
        } catch (Exception e) {
            log.error("文件读取异常", e.getMessage());
        }
        return mapHandler.doMap(ranges, "TWO_MAP");
    }

    /**
     * 处理每个分组内容，如读取{0,99}区间的手机号，并解析
     *
     * @return ExecuteResult
     * @author JichenWang
     * @since 2024/6/29 14:04
     */
    @MapExecutor(taskName = "TWO_MAP")
    public ExecuteResult monthMapExecute(MapArgs mapArgs) {
        // 获取本次要处理的区间
        final List<Integer> mapResult = (List<Integer>) mapArgs.getMapResult();
        log.info("本次要处理的区间为：{}", mapResult);

        // 按照处理区间，去读取数据
        final PhoneNumberCheckBo phoneNumberCheckBo = new PhoneNumberCheckBo();
        try {
            @Cleanup InputStream numberInputStream = getClass().getClassLoader().getResourceAsStream("doc/number.xlsx");
            PhoneNumberExcelListener phoneNumberExcelListener = new PhoneNumberExcelListener(phoneNumberCheckBo, false, BATCH_SIZE);
            EasyExcel.read(numberInputStream, PhoneNumberBo.class, phoneNumberExcelListener).sheet().headRowNumber(mapResult.get(0) + 1).doReadSync();
        } catch (Exception e) {
            log.error("文件读取异常：", e.getMessage());
        }

        return ExecuteResult.success(phoneNumberCheckBo);
    }


    @ReduceExecutor
    public ExecuteResult reduceExecute(ReduceArgs mapReduceArgs) {
        log.info("WJC Test reduceExecute, 参数为：{}", mapReduceArgs.getMapResult());
        final PhoneNumberCheckBo phoneNumberCheckBo = this.buildGatherPhoneNumberCheckBo(mapReduceArgs.getMapResult().toString());
        return ExecuteResult.success(phoneNumberCheckBo);
    }

    /**
     * 当只有一个reduce任务时无此执行器
     */
    @MergeReduceExecutor
    public ExecuteResult mergeReduceExecute(MergeReduceArgs mergeReduceArgs) {
        final PhoneNumberCheckBo phoneNumberCheckBo = this.buildGatherPhoneNumberCheckBo(mergeReduceArgs.getReduces().toString());
        log.info("WJC 最终检测结果为：{}", phoneNumberCheckBo);
        return ExecuteResult.success(phoneNumberCheckBo);
    }

    /**
     * 构造汇总手机号校验结果BO
     *
     * @param phoneNumberCheckBoStr 手机号校验BO字符串
     * @return PhoneNumberCheckBo  汇总手机号校验结果BO
     * @author JichenWang
     * @since 2024/6/29 14:24
     */
    private PhoneNumberCheckBo buildGatherPhoneNumberCheckBo(String phoneNumberCheckBoStr) {
        final List<PhoneNumberCheckBo> phoneNumberCheckBoList = JSONArray.parseArray(phoneNumberCheckBoStr, PhoneNumberCheckBo.class);
        // 获取校验总数
        final long checkTotalNum = phoneNumberCheckBoList.get(0).getTotal();
        // 汇总校验失败数量
        final long checkErrorNum = phoneNumberCheckBoList.stream().mapToLong(PhoneNumberCheckBo::getError).sum();
        // 汇总校验成功数量
        final long checkSuccessNum = phoneNumberCheckBoList.stream().mapToLong(PhoneNumberCheckBo::getSuccess).sum();
        // 汇总错误手机号
        final List<String> errorPhoneNumberList = new ArrayList<>();
        phoneNumberCheckBoList.forEach(item -> errorPhoneNumberList.addAll(item.getCheckErrors()));

        // 汇总手机号校验结果
        return PhoneNumberCheckBo.builder().total(checkTotalNum).error(checkErrorNum).success(checkSuccessNum).checkErrors(errorPhoneNumberList).build();
    }

}
