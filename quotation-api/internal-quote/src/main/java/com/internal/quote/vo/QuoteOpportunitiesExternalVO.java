package com.internal.quote.vo;

import com.internal.common.core.domain.BaseEntity;
import com.internal.quote.domain.QuoteOpportunitiesExternal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报价系统-商机外采硬件小记对象 quote_opportunities_external
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "QuoteOpportunitiesExternalVO", description = "报价系统-商机外采硬件小记")
public class QuoteOpportunitiesExternalVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 详细信息
     */
   @Schema(description = "详细信息")
    private List<QuoteOpportunitiesExternal> quoteOpportunitiesExternalList;

    /**
     * 采购询价总额
     */
    @Schema(description = "采购询价总额")
    private BigDecimal totalCost;

    /**
     * 预估成本价总额
     */
    @Schema(description = "预估成本价总额")
    private BigDecimal totalEstimatedlCost;

    /**
     * 建议对外报价总额
     */
    @Schema(description = "建议对外报价总额")
    private BigDecimal totalExternalquotation;


    public QuoteOpportunitiesExternalVO(List<QuoteOpportunitiesExternal> quoteOpportunitiesExternalList, BigDecimal totalCost, BigDecimal totalEstimatedlCost, BigDecimal totalExternalquotation) {
        this.quoteOpportunitiesExternalList = quoteOpportunitiesExternalList;
        this.totalCost = totalCost;
        this.totalEstimatedlCost = totalEstimatedlCost;
        this.totalExternalquotation = totalExternalquotation;
    }
}