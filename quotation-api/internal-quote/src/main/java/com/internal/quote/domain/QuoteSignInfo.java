package com.internal.quote.domain;

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
 * 报价系统-待签合同对象 quote_sign_info
 *
 * @author internal
 * @date 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteSignInfo",description = "报价系统-待签合同对象")
@TableName("quote_sign_info")
public class QuoteSignInfo extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** 主键ID */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 商机售前支持ID */
    @Schema(description = "商机售前支持ID")
    @TableField(value = "opportunities_id")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesId;

    /** 商机主表ID */
    @Schema(description = "商机主表ID")
    @TableField(value = "opportunities_parent_id")
    @Excel(name = "商机主表ID")
    private Long opportunitiesParentId;

    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @TableField(value = "contract_type")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;

    /** 状态[0暂存;未生效;2生效中] */
    @Schema(description = "状态[0暂存;未生效;2生效中]")
    @TableField(value = "status")
    @Excel(name = "状态[0暂存;未生效;2生效中]")
    private String status;

    /** 预计开始时间 */
    @Schema(description = "预计开始时间")
    @TableField(value = "plan_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartTime;

    /** 预计结束时间 */
    @Schema(description = "预计结束时间")
    @TableField(value = "plan_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndTime;

    /** 申请签约说明 */
    @Schema(description = "申请签约说明")
    @TableField(value = "sign_desc")
    @Excel(name = "申请签约说明")
    private String signDesc;

    /** 签约状态-欣象vs客户 */
    @Schema(description = "签约状态-欣象vs客户")
    @TableField(value = "xx_vs_cust")
    @Excel(name = "签约状态-欣象vs客户")
    private Integer xxVsCust;

    /** 签约状态-欣象vs北光 */
    @Schema(description = "签约状态-欣象vs北光")
    @TableField(value = "xx_vs_nl")
    @Excel(name = "签约状态-欣象vs北光")
    private Integer xxVsNl;

    /** 签约状态-北光vs客户 */
    @Schema(description = "签约状态-北光vs客户")
    @TableField(value = "nl_vs_cust")
    @Excel(name = "签约状态-北光vs客户")
    private Integer nlVsCust;

    /** 审批理由 */
    @Schema(description = "审批理由")
    @TableField(value = "sign_audit_desc")
    @Excel(name = "审批理由")
    private String signAuditDesc;

    /** 北光报价总金额 */
    @Schema(description = "北光报价总金额")
    @TableField(value = "north_amount")
    @Excel(name = "北光报价总金额")
    private BigDecimal northAmount;

    /** 北光报价金额 */
    @Schema(description = "北光报价金额")
    @TableField(value = "north_quote_amount")
    @Excel(name = "北光报价金额")
    private BigDecimal northQuoteAmount;

    /** 软件对外报价 */
    @Schema(description = "软件对外报价")
    @TableField(value = "soft_ware_ext_quote")
    @Excel(name = "软件对外报价")
    private BigDecimal softWareExtQuote;

    /** 软件预计税后收入 */
    @Schema(description = "软件预计税后收入")
    @TableField(value = "soft_ware_ext_income")
    @Excel(name = "软件预计税后收入")
    private BigDecimal softWareExtIncome;

    /** 产品平台对外报价 */
    @Schema(description = "产品平台对外报价")
    @TableField(value = "prod_ext_quote")
    @Excel(name = "产品平台对外报价")
    private BigDecimal prodExtQuote;

    /** 产品平台预计税后收入 */
    @Schema(description = "产品平台预计税后收入")
    @TableField(value = "prod_ext_income")
    @Excel(name = "产品平台预计税后收入")
    private BigDecimal prodExtIncome;

    /** 自研对外报价 */
    @Schema(description = "自研对外报价")
    @TableField(value = "self_ext_quote")
    @Excel(name = "自研对外报价")
    private BigDecimal selfExtQuote;

    /** 自研预计税后收入 */
    @Schema(description = "自研预计税后收入")
    @TableField(value = "self_ext_income")
    @Excel(name = "自研预计税后收入")
    private BigDecimal selfExtIncome;

    /** 实施对外报价 */
    @Schema(description = "实施对外报价")
    @TableField(value = "imp_ext_quote")
    @Excel(name = "实施对外报价")
    private BigDecimal impExtQuote;

    /** 实施预计税后收入 */
    @Schema(description = "实施预计税后收入")
    @TableField(value = "imp_ext_income")
    @Excel(name = "实施预计税后收入")
    private BigDecimal impExtIncome;

    /** 其他对外报价 */
    @Schema(description = "其他对外报价")
    @TableField(value = "other_ext_quote")
    @Excel(name = "其他对外报价")
    private BigDecimal otherExtQuote;

    /** 其他预计税后收入 */
    @Schema(description = "其他预计税后收入")
    @TableField(value = "other_ext_income")
    @Excel(name = "其他预计税后收入")
    private BigDecimal otherExtIncome;

    /** 外采对外报价（北光报价金额） */
    @Schema(description = "外采对外报价（北光报价金额）")
    @TableField(value = "ext_ext_quote")
    @Excel(name = "外采对外报价")
    private BigDecimal extExtQuote;

    /** 外采预计税后收入(税后总收入) */
    @Schema(description = "外采预计税后收入(税后总收入)")
    @TableField(value = "ext_ext_income")
    @Excel(name = "外采预计税后收入(税后总收入)")
    private BigDecimal extExtIncome;

    /** 预计税后总收入 */
    @Schema(description = "预计税后总收入")
    @TableField(value = "total_ext_income")
    @Excel(name = "预计税后总收入")
    private BigDecimal totalExtIncome;

    /** 人工成本 */
    @Schema(description = "人工成本")
    @TableField(value = "labor_price")
    @Excel(name = "人工成本")
    private BigDecimal laborPrice;

    /** 产品成本价 */
    @Schema(description = "产品成本价")
    @TableField(value = "prod_price")
    @Excel(name = "产品成本价")
    private BigDecimal prodPrice;

    /** 自研成本价 */
    @Schema(description = "自研成本价")
    @TableField(value = "self_price")
    @Excel(name = "自研成本价")
    private BigDecimal selfPrice;

    /** 实施成本价 */
    @Schema(description = "实施成本价")
    @TableField(value = "imp_price")
    @Excel(name = "实施成本价")
    private BigDecimal impPrice;

    /** 其他成本价 */
    @Schema(description = "其他成本价")
    @TableField(value = "other_price")
    @Excel(name = "其他成本价")
    private BigDecimal otherPrice;

    /** 硬件外采成本价（预计总成本） */
    @Schema(description = "硬件外采成本价（预计总成本）")
    @TableField(value = "ext_price")
    @Excel(name = "硬件外采成本价", readConverterExp = "预=计总成本")
    private BigDecimal extPrice;

    /** 预计总成本 */
    @Schema(description = "预计总成本")
    @TableField(value = "total_cost")
    @Excel(name = "预计总成本")
    private BigDecimal totalCost;

    /** 预计利润 */
    @Schema(description = "预计利润")
    @TableField(value = "total_profit")
    @Excel(name = "预计利润")
    private BigDecimal totalProfit;

    /** 外采预计利润 */
    @Schema(description = "外采预计利润")
    @TableField(value = "ext_profit")
    @Excel(name = "外采预计利润")
    private BigDecimal extProfit;

    /** 成本利润率 */
    @Schema(description = "成本利润率")
    @TableField(value = "cost_profit_rate")
    @Excel(name = "成本利润率")
    private BigDecimal costProfitRate;

    /** 外采利润率 */
    @Schema(description = "外采利润率")
    @TableField(value = "ext_profit_rate")
    @Excel(name = "外采利润率")
    private BigDecimal extProfitRate;

    /** 总成本利润率 */
    @Schema(description = "总成本利润率")
    @TableField(value = "total_cost_profit_rate")
    @Excel(name = "总成本利润率")
    private BigDecimal totalCostProfitRate;
}