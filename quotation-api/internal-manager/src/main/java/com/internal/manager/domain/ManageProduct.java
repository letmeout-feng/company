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
 * 管理系统-产品管理对象 manage_product
 *
 * @author internal
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageProduct",description = "管理系统-产品管理对象")
@TableName("manage_product")
public class ManageProduct extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 产品类别 */
    @Schema(description = "产品类别")
    @TableField(value = "category")
    @Excel(name = "产品类别")
    private String category;

    /** 产品名称 */
    @Schema(description = "产品名称")
    @TableField(value = "name")
    @Excel(name = "产品名称")
    private String name;

    /** 产品单位 */
    @Schema(description = "产品单位")
    @TableField(value = "unit")
    @Excel(name = "产品单位")
    private String unit;

    /** 产品状态[1:可用,2:不可用] */
    @Schema(description = "产品状态[1:可用,2:不可用]")
    @TableField(value = "status")
    @Excel(name = "产品状态[1:可用,2:不可用]")
    private String status;

    /** 主产品id */
    @Schema(description = "主产品id")
    @TableField(value = "parent_id")
    @Excel(name = "主产品id")
    private Long parentId;

    /** 成本价 */
    @Schema(description = "成本价")
    @TableField(value = "cost_price")
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /** 对外报价 */
    @Schema(description = "对外报价")
    @TableField(value = "ext_price")
    @Excel(name = "对外报价")
    private BigDecimal extPrice;

    /** 描述 */
    @Schema(description = "描述")
    @TableField(value = "description")
    @Excel(name = "描述")
    private String description;

    /** 北光软著id */
    @Schema(description = "北光软著id")
    @TableField(value = "nl_soft_id")
    @Excel(name = "北光软著id")
    private Long nlSoftId;

    /** 欣象软著id */
    @Schema(description = "欣象软著id")
    @TableField(value = "xx_soft_id")
    @Excel(name = "欣象软著id")
    private Long xxSoftId;

    /**
     * 产品昵称
     */
    @Schema(description = "产品昵称")
    @TableField(value = "short_name")
    @Excel(name = "产品昵称")
    private String shortName;
}