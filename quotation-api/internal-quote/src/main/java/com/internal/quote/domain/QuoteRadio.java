package com.internal.quote.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Schema(name = "QuoteRadio",description = "报价系统-报价部门占比对象")
@TableName("quote_radio")
public class QuoteRadio extends BaseEntity
{
    private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 商机售前支持ID */
    @Schema(description = "商机售前支持ID")
    @TableField(value = "opportunities_id")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesId;

    /** 成本报价id */
    @Schema(description = "成本报价id")
    @TableField(value = "cost_id")
    @Excel(name = "成本报价id")
    private Long costId;

    /** 部门ID */
    @Schema(description = "部门ID")
    @TableField(value = "dept_id")
    @Excel(name = "部门ID")
    private Long deptId;

    /** 占比 */
    @Schema(description = "占比")
    @TableField(value = "radio")
    @Excel(name = "占比")
    private BigDecimal radio;

    /** 对应CRM售前支持ID */
    @Schema(description = "对应CRM售前支持ID")
    @TableField(value = "support_crm_id")
    @Excel(name = "对应CRM售前支持ID")
    private String supportCrmId;

    /**
     * 售前支持人员ID
     */
    @Schema(description = "售前支持人员ID")
    @TableField(value = "support_person")
    @Excel(name = "售前支持人员ID")
    private String supportPerson;

}