package com.example.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {
    @Schema(description = "订单ID,用于唯一标识订单的编号", accessMode = Schema.AccessMode.READ_WRITE)
    private String orderId; // 订单ID,用于唯一标识订单的编号
    @Schema(description = "订单来源信息,1-手机端下单 2-PC端下单", accessMode = Schema.AccessMode.READ_WRITE)
    private Integer source; // 订单来源信息,1-手机端下单 2-PC端下单
}
