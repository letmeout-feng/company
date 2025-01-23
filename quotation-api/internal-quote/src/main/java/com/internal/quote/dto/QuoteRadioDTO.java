package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 报价系统-报价部门占比对象 quote_radio
 *
 * @author internal
 * @date 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteRadioDTO",description = "报价系统-报价部门占比对象DTO")
public class QuoteRadioDTO extends BaseEntity
{
    private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID-不要")
    private Long id;

    /** 商机售前支持ID */
    @Schema(description = "商机售前支持ID-可以没有")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesId;

    /** 成本报价id */
    @Schema(description = "成本报价id-不要")
    @Excel(name = "成本报价id")
    private Long costId;

    /** 部门ID */
    @Schema(description = "部门ID-需要")
    @Excel(name = "部门ID")
    private Long deptId;

    /** 占比 */
    @Schema(description = "占比-需要")
    @Excel(name = "占比")
    private BigDecimal radio;

    /** 对应CRM售前支持ID */
    @Schema(description = "对应CRM售前支持ID-可以没有")
    @Excel(name = "对应CRM售前支持ID")
    private String supportCrmId;

    /**
     * 售前支持人员ID
     */
    @Schema(description = "售前支持人员ID-需要,就是售前的crmUserId,用逗号拼接")
    @Excel(name = "售前支持人员ID")
    private String supportPerson;
}