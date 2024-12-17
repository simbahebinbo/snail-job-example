package com.example.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机号检测BO
 *
 * @author JiChenWang
 * @since 2024/6/27 20:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberCheckBo {

    @Schema(description = "检测总条数", accessMode = Schema.AccessMode.READ_WRITE)
    private Long total = 0L;

    @Schema(description = "检测失败条数", accessMode = Schema.AccessMode.READ_WRITE)
    private Long error = 0L;

    @Schema(description = "检测成功条数", accessMode = Schema.AccessMode.READ_WRITE)
    private Long success = 0L;

    @Schema(description = "检测失败临时的数据", accessMode = Schema.AccessMode.READ_WRITE)
    private List<String> checkErrors = new ArrayList<>();

//    @Schema(description = "检测成功临时的数据", accessMode = Schema.AccessMode.READ_WRITE)
//    private List<PhoneNumberPo> checkSuccessPhoneNumberList = new ArrayList<>();

}
