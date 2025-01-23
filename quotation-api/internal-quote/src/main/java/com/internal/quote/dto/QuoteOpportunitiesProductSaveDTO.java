package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 报价系统-商机产品平台小记对象 quote_opportunities_product
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesProductSaveDTO", description = "报价系统-商机产品平台小记对象")
public class QuoteOpportunitiesProductSaveDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesDetailId;

    /**
     * 序号
     */
    @Schema(description = "序号")
    @NotEmpty(message = "序号不能为空")
    @Excel(name = "序号")
    private String serialNumber;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @NotEmpty(message = "名称不能为空")
    @Excel(name = "名称")
    private String name;

    /**
     * 单位
     */
    @Schema(description = "单位")
    @NotEmpty(message = "单位不能为空")
    @Excel(name = "单位")
    private String unit;

    /**
     * 单价
     */
    @Schema(description = "单价")
    @NotNull(message = "单价不能为空")
    @Excel(name = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    /**
     * 数量
     */
    @Schema(description = "数量")
    @NotNull(message = "数量不能为空")
    @Excel(name = "数量")
    private BigDecimal number;

    /**
     * 总价
     */
    @Schema(description = "总价")
    @NotNull(message = "总价不能为空")
    @Excel(name = "总价")
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 产品id(子产品)
     */
    @Schema(description = "产品id(子产品)")
    @Excel(name = "产品id(子产品)")
    private Long productId;
}