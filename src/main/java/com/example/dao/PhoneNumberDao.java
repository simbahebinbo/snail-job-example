package com.example.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.po.PhoneNumberPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author JiChenWang
 * @since 2024/6/30 11:58
 */
@Service
public class PhoneNumberDao extends ServiceImpl<PhoneNumberBaseMapper, PhoneNumberPo> {

    @Autowired
    private PhoneNumberBaseMapper phoneNumberBaseMapper;

    /**
     * 批量保存手机号信息
     *
     * @param phoneNumberPoList 手机号po列表
     * @return Boolean  保存成功标识：true-成功、false-失败
     * @author JichenWang
     * @since 2024/6/30 12:03
     */
    public Boolean insertBatch(List<PhoneNumberPo> phoneNumberPoList) {
        return this.saveBatch(phoneNumberPoList);
    }

}
