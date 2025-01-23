package com.internal.manager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理系统-硬件外采对象 manage_hardware_ext
 *
 * @author internal
 * @date 2024-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageHardwareExt",description = "管理系统-硬件外采对象")
@TableName("manage_hardware_ext")
public class ManageHardwareExt extends BaseEntity
{
    private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 名称 */
    @Schema(description = "名称")
    @TableField(value = "name")
    @Excel(name = "名称")
    private String name;

    /** 类型 */
    @Schema(description = "类型")
    @TableField(value = "category")
    @Excel(name = "类型")
    private String category;

    /** 状态[1:可用,2:不可用] */
    @Schema(description = "状态[1:可用,2:不可用]")
    @TableField(value = "status")
    @Excel(name = "状态",readConverterExp = "1=可用,2=不可用")
    private String status;

    /** 品牌 */
    @Schema(description = "品牌")
    @TableField(value = "brand")
    @Excel(name = "品牌")
    private String brand;

    /** 规格型号 */
    @Schema(description = "规格型号")
    @TableField(value = "spec")
    @Excel(name = "规格型号")
    private String spec;

    /** 单位 */
    @Schema(description = "单位")
    @TableField(value = "unit")
    @Excel(name = "单位")
    private String unit;

    /** 发票类型;[普通发票:ORDINARY,专用发票:CATEGORICAL,不确定:INCONCLUSIVE] */
    @Schema(description = "发票类型;[普通发票:ORDINARY,专用发票:CATEGORICAL,不确定:INCONCLUSIVE]")
    @TableField(value = "type")
    @Excel(name = "发票类型;[普通发票:ORDINARY,专用发票:CATEGORICAL,不确定:INCONCLUSIVE]")
    private String type;

    /** 税率 */
    @Schema(description = "税率")
    @TableField(value = "rate")
    @Excel(name = "税率")
    private BigDecimal rate;

    /** 报价日期 */
    @Schema(description = "报价日期")
    @TableField(value = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报价日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 设备参数 */
    @Schema(description = "设备参数")
    @TableField(value = "device_description")
    @Excel(name = "设备参数")
    private String deviceDescription;

    /** 单价 */
    @Schema(description = "单价")
    @TableField(value = "unit_price")
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 预估成本价 */
    @Schema(description = "预估成本价")
    @TableField(value = "estimated_cost")
    @Excel(name = "预估成本价")
    private BigDecimal estimatedCost;

    /** 建议对外报价 */
    @Schema(description = "建议对外报价")
    @TableField(value = "external_quote")
    @Excel(name = "建议对外报价")
    private BigDecimal externalQuote;

    /** 备注 */
    @Schema(description = "备注")
    @TableField(value = "remark")
    @Excel(name = "备注")
    private String remark;
}