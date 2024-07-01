package com.example.snailjob.listener;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.snailjob.bo.PhoneNumberBo;
import com.example.snailjob.bo.PhoneNumberCheckBo;
import com.example.snailjob.po.PhoneNumberPo;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 手机号excel解析 Listener
 *
 * @author JiChenWang
 * @since 2024/6/27 20:38
 */
@Slf4j
public class PhoneNumberExcelListener extends AnalysisEventListener<PhoneNumberBo> {

    /** 手机号校验BO **/
    private PhoneNumberCheckBo phoneNumberCheckBo;

    /** 是否第一次读取Excel **/
    private Boolean firstReadStatus = false;

    /** 读取批次大小 **/
    private Integer batchSize = 100;

    /** 已读取的数据数量 **/
    private Integer cacheSize = 0;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.phoneNumberCheckBo.setCheckTotalNum(Long.parseLong(String.valueOf(context.readSheetHolder().getApproximateTotalRowNumber() - 1)));
    }

    @Override
    public void invoke(PhoneNumberBo phoneNumberBo, AnalysisContext context) {
        // 如果是第一次读该文件，已读取的数量已超过读取批次大小，直接返回
        if (firstReadStatus || cacheSize >= batchSize) {
            return;
        }

        cacheSize++;

        if (ObjectUtil.isEmpty(phoneNumberBo.getPhoneNumber())) {
            return;
        }

        // 校验手机号
        log.info("本次校验的手机号为: {}", phoneNumberBo.getPhoneNumber());
        Boolean validateStatus = Validator.isMobile(phoneNumberBo.getPhoneNumber());
        if (validateStatus) {
            this.phoneNumberCheckBo.setCheckSuccessNum(this.phoneNumberCheckBo.getCheckSuccessNum() + 1);
            final PhoneNumberPo phoneNumberPo = PhoneNumberPo.builder().phoneNumber(phoneNumberBo.getPhoneNumber()).createTime(LocalDateTime.now()).build();
            this.phoneNumberCheckBo.getCheckSuccessPhoneNumberList().add(phoneNumberPo);
        } else {
            this.phoneNumberCheckBo.setCheckErrorNum(this.phoneNumberCheckBo.getCheckErrorNum() + 1);
            this.phoneNumberCheckBo.getCheckErrorPhoneNumberList().add(phoneNumberBo.getPhoneNumber());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public PhoneNumberExcelListener(PhoneNumberCheckBo phoneNumberCheckBo, Boolean firstReadStatus, Integer batchSize) {
        this.phoneNumberCheckBo = phoneNumberCheckBo;
        this.firstReadStatus = firstReadStatus;
        this.batchSize = batchSize;
    }

}
