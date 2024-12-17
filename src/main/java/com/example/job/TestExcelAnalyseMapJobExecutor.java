package com.example.job;

import cn.hutool.core.util.ObjectUtil;
import com.aizuda.snailjob.client.job.core.MapHandler;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.annotation.MapExecutor;
import com.aizuda.snailjob.client.job.core.dto.MapArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.alibaba.excel.EasyExcel;
import com.example.bo.PhoneNumberBo;
import com.example.bo.PhoneNumberCheckBo;
import com.example.dao.PhoneNumberDao;
import com.example.listener.PhoneNumberExcelListener;
import com.example.po.PhoneNumberPo;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析excel文件中的手机号码，并将错误的手机号分片入库
 *
 * @author JiChenWang
 * @since 2024/6/30 10:37
 */
@Slf4j
@Component
@JobExecutor(name = "testExcelAnalyseMapJobExecutor")
public class TestExcelAnalyseMapJobExecutor {

    private final Integer BATCH_SIZE = 100;

    @Autowired
    private PhoneNumberDao phoneNumberDao;

    /**
     * 读取手机号文件总行数，并进行分组
     * 比如文档中的手机号总量为307条，每100条一个分组，分组结果为[{0,99}, {100, 199}, {200,299}, {300, 307}]
     *
     * @return ExecuteResult
     * @author JichenWang
     * @since 2024/6/30 10:48
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
     * @param mapArgs
     * @return ExecuteResult
     * @author JichenWang
     * @since 2024/6/30 11:05
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

        // 如果正确手机号不为空，则入库
        if (ObjectUtil.isNotEmpty(phoneNumberCheckBo.getCheckErrors())) {

            List<PhoneNumberPo> numberPos = new ArrayList<>();
            for (String no : phoneNumberCheckBo.getCheckErrors()) {
                PhoneNumberPo numberPo = new PhoneNumberPo();
                numberPo.setPhoneNumber(no);
                numberPos.add(numberPo);
            }
            phoneNumberDao.insertBatch(numberPos);
        }

        return ExecuteResult.success(phoneNumberCheckBo.getError());
    }

}
