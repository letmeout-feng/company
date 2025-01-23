package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 只有ID等基础字段的基础DTO
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "BaseDTO", description = "只有ID等基础字段的基础DTO")
public class BaseDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 报价id
     */
    @Schema(description = "报价id")
    @Excel(name = "报价id")
    private Long opportunitiesId;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesParentId;

    /**
     * 对应CRM售前支持ID
     */
    @Schema(description = "对应CRM售前支持ID")
    @Excel(name = "对应CRM售前支持ID")
    private String supportCrmId;

    /**
     * 成本报价Id(只有详细用到了)
     */
    @Schema(description = "成本报价Id(粗略或详细)")
    private Long costId = null;


}