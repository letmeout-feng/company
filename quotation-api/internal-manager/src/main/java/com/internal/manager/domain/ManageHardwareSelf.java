package com.internal.manager.domain;

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
 * 管理系统-硬件自研对象 manage_hardware_self
 *
 * @author internal
 * @date 2024-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageHardwareSelf",description = "管理系统-硬件自研对象")
@TableName("manage_hardware_self")
public class ManageHardwareSelf extends BaseEntity
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

    /** 单位 */
    @Schema(description = "单位")
    @TableField(value = "unit")
    @Excel(name = "单位")
    private String unit;

    /** 状态[1:可用,2:不可用] */
    @Schema(description = "状态[1:可用,2:不可用]")
    @TableField(value = "status")
    @Excel(name = "状态",readConverterExp = "1=可用,2=不可用")
    private String status;

    /** 单价 */
    @Schema(description = "单价")
    @TableField(value = "unit_price")
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 建议销售价 */
    @Schema(description = "建议销售价")
    @TableField(value = "ext_price")
    private BigDecimal extPrice;

    /** 设备参数 */
    @Schema(description = "设备参数")
    @TableField(value = "device_description")
    @Excel(name = "设备参数")
    private String deviceDescription;

    /** 默认税率 */
    @Schema(description = "默认税率")
    @TableField(value = "rate")
    private BigDecimal rate;

    /** 备注 */
    @Schema(description = "备注")
    @TableField(value = "remark")
    @Excel(name = "备注")
    private String remark;
}