package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 签约申请DTO
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "SignApplicationSaveDTO", description = "签约申请DTO")
public class SignApplicationSaveDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 商机ID */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 商机售前支持ID
     */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesParentId;

    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;


    /** 预计开始时间(暂时不用) */
    /*@Schema(description = "预计开始时间(暂时不用)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计开始时间(暂时不用)", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartTime;*/

    /** 预计结束时间(暂时不用) */
    /*@Schema(description = "预计结束时间(暂时不用)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计结束时间(暂时不用)", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndTime;*/

    /** 申请签约说明 */
    @Schema(description = "申请签约说明")
    @Excel(name = "申请签约说明")
    private String signDesc;

    /**
     * 签约图片（Base64）
     */
    @Schema(description = "签约图片（Base64）")
    @Excel(name = "签约图片（Base64）")
    private String signImg;


    /**
     * 北光报价总金额
     */
    @Schema(description = "北光报价总金额")
    @Excel(name = "北光报价总金额")
    private BigDecimal northAmount;

    /**
     * 北光报价金额
     */
    @Schema(description = "北光报价金额")
    @Excel(name = "北光报价金额")
    private BigDecimal northQuoteAmount;

    /**
     * 软件对外报价
     */
    @Schema(description = "软件对外报价")
    @Excel(name = "软件对外报价")
    private BigDecimal softWareExtQuote;
    /**
     * 软件预计税后收入
     */
    @Schema(description = "软件预计税后收入")
    @Excel(name = "软件预计税后收入")
    private BigDecimal softWareExtIncome;
    /**
     * 产品平台对外报价
     */
    @Schema(description = "产品平台对外报价")
    @Excel(name = "产品平台对外报价")
    private BigDecimal prodExtQuote;
    /**
     * 产品平台预计税后收入
     */
    @Schema(description = "产品平台预计税后收入")
    @Excel(name = "产品平台预计税后收入")
    private BigDecimal prodExtIncome;
    /**
     * 自研对外报价
     */
    @Schema(description = "自研对外报价")
    @Excel(name = "自研对外报价")
    private BigDecimal selfExtQuote;
    /**
     * 自研预计税后收入
     */
    @Schema(description = "自研预计税后收入")
    @Excel(name = "自研预计税后收入")
    private BigDecimal selfExtIncome;
    /**
     * 实施对外报价
     */
    @Schema(description = "实施对外报价")
    @Excel(name = "实施对外报价")
    private BigDecimal impExtQuote;
    /**
     * 实施预计税后收入
     */
    @Schema(description = "实施预计税后收入")
    @Excel(name = "实施预计税后收入")
    private BigDecimal impExtIncome;
    /**
     * 其他对外报价
     */
    @Schema(description = "其他对外报价")
    @Excel(name = "其他对外报价")
    private BigDecimal otherExtQuote;
    /**
     * 其他预计税后收入
     */
    @Schema(description = "其他预计税后收入")
    @Excel(name = "其他预计税后收入")
    private BigDecimal otherExtIncome;
    /**
     * 外采对外报价
     */
    @Schema(description = "外采对外报价（北光报价金额）")
    @Excel(name = "外采对外报价")
    private BigDecimal extExtQuote;
    /**
     * 外采预计税后收入
     */
    @Schema(description = "外采预计税后收入(税后总收入)")
    @Excel(name = "外采预计税后收入")
    private BigDecimal extExtIncome;

    /**
     * 预计税后总收入
     */
    @Schema(description = "预计税后总收入")
    @Excel(name = "预计税后总收入")
    private BigDecimal totalExtIncome;

    /**
     * 人工成本
     */
    @Schema(description = "人工成本")
    @Excel(name = "人工成本")
    private BigDecimal laborPrice;

    /**
     * 产品成本价
     */
    @Schema(description = "产品成本价")
    @Excel(name = "产品成本价")
    private BigDecimal prodPrice;

    /**
     * 自研成本价
     */
    @Schema(description = "自研成本价")
    @Excel(name = "自研成本价")
    private BigDecimal selfPrice;
    /**
     * 实施成本价
     */
    @Schema(description = "实施成本价")
    @Excel(name = "实施成本价")
    private BigDecimal impPrice;
    /**
     * 其他成本价
     */
    @Schema(description = "其他成本价")
    @Excel(name = "其他成本价")
    private BigDecimal otherPrice;
    /**
     * 硬件外采成本价
     */
    @Schema(description = "硬件外采成本价（预计总成本）")
    @Excel(name = "硬件外采成本价")
    private BigDecimal extPrice;

    /**
     * 预计总成本
     */
    @Schema(description = "预计总成本")
    @Excel(name = "预计总成本")
    private BigDecimal totalCost;
    /**
     * 预计利润
     */
    @Schema(description = "预计利润")
    @Excel(name = "预计利润")
    private BigDecimal totalProfit;
    /**
     * 外采预计利润
     */
    @Schema(description = "外采预计利润")
    @Excel(name = "外采预计利润")
    private BigDecimal extProfit;
    /**
     * 成本利润率
     */
    @Schema(description = "成本利润率")
    @Excel(name = "成本利润率")
    private BigDecimal costProfitRate;
    /**
     * 外采利润率
     */
    @Schema(description = "外采利润率")
    @Excel(name = "外采利润率")
    private BigDecimal extProfitRate;
    /**
     * 总成本利润率
     */
    @Schema(description = "总成本利润率")
    @Excel(name = "总成本利润率")
    private BigDecimal totalCostProfitRate;

}
