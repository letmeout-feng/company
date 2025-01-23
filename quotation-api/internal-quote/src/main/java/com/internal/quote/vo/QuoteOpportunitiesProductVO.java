package com.internal.quote.vo;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import com.internal.quote.domain.QuoteOpportunitiesProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报价系统-商机产品平台小记对象 quote_opportunities_product
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "QuoteOpportunitiesProductVO", description = "报价系统-商机产品平台小记")
public class QuoteOpportunitiesProductVO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 详情列表
     */
    @Schema(description = "详情列表")
    private List<QuoteOpportunitiesProduct> quoteOpportunitiesProductList;

    /**
     * 总价
     */
    @Schema(description = "总价")
    @Excel(name = "总价")
    private BigDecimal totalCost;


    public QuoteOpportunitiesProductVO(List<QuoteOpportunitiesProduct> quoteOpportunitiesProductList, BigDecimal totalCost) {
        this.quoteOpportunitiesProductList = quoteOpportunitiesProductList;
        this.totalCost = totalCost;
    }
}