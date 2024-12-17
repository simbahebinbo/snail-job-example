package com.example.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * excel表格手机号BO
 *
 * @author JiChenWang
 * @since 2024/6/27 20:28
 */
@Data
public class PhoneNumberBo {

    @ExcelProperty(value = "手机号码", index = 0)
    private String phoneNumber;

}
