package com.example.easy.retry.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {
    @ApiModelProperty(value = "订单ID,用于唯一标识订单的编号", required = true)
    private String orderId; // 订单ID,用于唯一标识订单的编号
    @ApiModelProperty(value = "订单来源信息,1-手机端下单 2-PC端下单",required = true)
    private Integer source; // 订单来源信息,1-手机端下单 2-PC端下单
}
