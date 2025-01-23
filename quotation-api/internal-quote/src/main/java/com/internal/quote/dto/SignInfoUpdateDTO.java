package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 签约状态更新DTO
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "SignInfoUpdateDTO", description = "签约状态更新DTO")
public class SignInfoUpdateDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID")
    private Long id;

    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;

    /** 签约状态-欣象vs客户 */
    @Schema(description = "签约状态-欣象vs客户")
    private Boolean xxVsCust;

    /** 签约状态-欣象vs北光 */
    @Schema(description = "签约状态-欣象vs北光")
    private Boolean xxVsNl;

    /** 签约状态-北光vs客户 */
    @Schema(description = "签约状态-北光vs客户")
    private Boolean nlVsCust;

}