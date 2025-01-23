package com.internal.manager.vo;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 管理系统-产品管理对象VO
 *
 * @author internal
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageProductVO",description = "管理系统-产品管理对象VO")
public class ManageProductVO extends BaseEntity
{
    private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    private Long id;

    /** 产品类别 */
    @Schema(description = "产品类别")
    @Excel(name = "产品类别",readConverterExp = "1=主产品,2=子产品")
    private String category;

    /** 产品名称 */
    @Schema(description = "产品名称")
    @Excel(name = "产品名称")
    private String name;

    /** 产品单位 */
    @Schema(description = "产品单位")
    @Excel(name = "产品单位")
    private String unit;

    /** 产品状态[1:可用,2:不可用] */
    @Schema(description = "产品状态[1:可用,2:不可用]")
    @Excel(name = "产品状态",readConverterExp = "1=可用,2=不可用")
    private String status;

    /** 成本价 */
    @Schema(description = "成本价")
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /** 对外报价 */
    @Schema(description = "对外报价")
    @Excel(name = "对外报价")
    private BigDecimal extPrice;

    /** 描述 */
    @Schema(description = "描述")
    @Excel(name = "描述")
    private String description;
    /** 主产品id */
    @Schema(description = "主产品id")
    private Long parentId;

    /** 北光软著id */
    @Schema(description = "北光软著id")
    private Long nlSoftId;
    /** 北光软著名 */
    @Schema(description = "北光软著名")
    @Excel(name = "北光软著名")
    private String nlSoftName;
    /** 欣象软著id */
    @Schema(description = "欣象软著id")
    private Long xxSoftId;
    /** 欣象软著名 */
    @Schema(description = "欣象软著名")
    @Excel(name = "欣象软著名")
    private String xxSoftName;


    /** 产品昵称 */
    @Schema(description = "产品昵称")
    @Excel(name = "产品昵称")
    private String shortName;

    /** 产品名+状态 */
    @Schema(description = "产品名+昵称+状态")
    private String nameAndStatus;
}