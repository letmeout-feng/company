package com.internal.quote.vo;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-首页-resultVO
 *
 * @author longernal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(name = "QuoteIndexVO", description = "报价系统-首页-resultVO")
public class QuoteIndexVO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 待成本报价
     */
    @Schema(description = "待成本报价")
    @Excel(name = "待成本报价")
    private long listWithCostCount = 0;


    /**
     * 待更新成本报价
     */
    @Schema(description = "待更新成本报价")
    @Excel(name = "待更新成本报价")
    private long listAwaitUpdateCount = 0;


    /**
     * 已成本报价
     */
    @Schema(description = "已成本报价")
    @Excel(name = "已成本报价")
    private long listWithCostedCount = 0;


    /**
     * 无法报价
     */
    @Schema(description = "无法报价")
    @Excel(name = "无法报价")
    private long listWithIncapableCount = 0;

    /**
     * 待销售报价
     */
    @Schema(description = "待销售报价")
    @Excel(name = "待销售报价")
    private long listWithSalesCount = 0;

    /**
     * 待报价审批
     */
    @Schema(description = "待报价审批")
    @Excel(name = "待报价审批")
    private long listWithApprovalCount = 0;

    /**
     * 报价审批未通过
     */
    @Schema(description = "报价审批未通过")
    @Excel(name = "报价审批未通过")
    private long listWithApprovalFailCount = 0;

    /**
     * 报价单
     */
    @Schema(description = "报价单")
    @Excel(name = "报价单")
    private long listWithQuoteCount = 0;

    /**
     * 待签约审批
     */
    @Schema(description = "待签约审批")
    @Excel(name = "待签约审批")
    private long listWithSignCount = 0;

    /**
     * 签约审批未通过
     */
    @Schema(description = "签约审批未通过")
    @Excel(name = "签约审批未通过")
    private long listWithSignFailCount = 0;

    /**
     * 待签合同
     */
    @Schema(description = "待签合同")
    @Excel(name = "待签合同")
    private long listWithContractCount = 0;



}