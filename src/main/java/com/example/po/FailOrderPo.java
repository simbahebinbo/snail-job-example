package com.example.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 处理失败的订单信息表
 */
@TableName("fail_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FailOrderPo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 订单ID
    private String orderId;
    // 来源ID
    private Integer sourceId;
    // 场景名称
    private String sceneName;
    // 执行器名称
    private String executorName;
    // 参数信息
    private String args;
    // 创建时间
    private LocalDateTime createDate;
    // 更新时间
    private LocalDateTime updateDate;

}
