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
 * 报价系统-商机自研硬件小记对象 quote_opportunities_self
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesSelf", description = "报价系统-商机自研硬件小记对象")
@TableName("quote_opportunities_self")
public class QuoteOpportunitiesSelf extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @TableField(value = "opportunities_id")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @TableField(value = "opportunities_detail_id")
    @Excel(name = "商机ID")
    private Long opportunitiesDetailId;

    /**
     * 序号
     */
    @Schema(description = "序号")
    @TableField(value = "serial_number")
    @Excel(name = "序号")
    private String serialNumber;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @TableField(value = "name")
    @Excel(name = "名称")
    private String name;

    /**
     * 单位
     */
    @Schema(description = "单位")
    @TableField(value = "unit")
    @Excel(name = "单位")
    private String unit;

    /**
     * 单价
     */
    @Schema(description = "单价")
    @TableField(value = "unit_price")
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @Schema(description = "数量")
    @TableField(value = "number")
    @Excel(name = "数量")
    private BigDecimal number;

    /**
     * 总价
     */
    @Schema(description = "总价")
    @TableField(value = "amount")
    @Excel(name = "总价")
    private BigDecimal amount;

    /**
     * 设置参数
     */
    @Schema(description = "设置参数")
    @TableField(value = "setting_param")
    @Excel(name = "设置参数")
    private String settingParam;

    /**
     * 硬件id
     */
    @Schema(description = "硬件id")
    @TableField(value = "hardware_id")
    @Excel(name = "硬件id")
    private Long hardwareId;

}