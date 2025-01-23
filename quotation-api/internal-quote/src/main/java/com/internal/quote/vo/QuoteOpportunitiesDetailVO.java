package com.internal.quote.vo;

import com.internal.common.annotation.Excel;
import com.internal.quote.domain.QuoteOpportunitiesImplement;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报价系统-商机详细报价对象 quote_opportunities_detail
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesDetailSaveDTO", description = "报价系统-商机详细报价对象")
public class QuoteOpportunitiesDetailVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    @NotNull(message = "商机ID不能为空")
    private Long opportunitiesId;

    /**
     * 评估版本
     */
    @Schema(description = "评估版本")
    @Excel(name = "评估版本")
    private String version;
    /**
     * 评估版本(拼接)
     */
    @Schema(description = "评估版本(拼接)")
    @Excel(name = "评估版本(拼接)")
    private String valuationVersion;

    /**
     * 评估说明
     */
    @Schema(description = "评估说明")
    @Excel(name = "评估说明")
    private String valuationDesc;

    /**
     * 评估时间
     */
    @Schema(description = "评估时间")
    @Excel(name = "评估时间")
    private String time;

    /**
     * 评估人
     */
    @Schema(description = "评估人")
    @Excel(name = "评估人")
    private String person;

    /**
     * 评估金额
     */
    @Schema(description = "评估金额")
    @Excel(name = "评估金额")
    @NotNull(message = "评估金额不能为空")
    private BigDecimal amount;

    /**
     * 售前支持小记
     */
    @Schema(description = "售前支持小记")
    private QuoteOpportunitiesSupportVO quoteOpportunitiesSupportVO;
    /**
     * 定制开发小记
     */
    @Schema(description = "定制开发小记")
    private QuoteOpportunitiesCustomizableVO quoteOpportunitiesCustomizableVo;
    /**
     * 产品平台小记
     */
    @Schema(description = "产品平台小记")
    private QuoteOpportunitiesProductVO quoteOpportunitiesProductVO;
    /**
     * 自研硬件小记
     */
    @Schema(description = "自研硬件小记")
    private QuoteOpportunitiesSelfVO quoteOpportunitiesSelfVO;
    /**
     * 外采硬件小记
     */
    @Schema(description = "外采硬件小记")
    private QuoteOpportunitiesExternalVO quoteOpportunitiesExternalVO;
    /**
     * 实施小记
     */
    @Schema(description = "实施小记")
    private QuoteOpportunitiesImplement quoteOpportunitiesImplement;
    /**
     * 其他小记
     */
    @Schema(description = "其他小记")
    private QuoteOpportunitiesOtherVO quoteOpportunitiesOtherVO;

    /**
     * 售前成本备注
     */
    @Schema(description = "售前成本备注")
    private String supportRemark;

    /** 报价部门占比列表 */
    @Schema(description = "报价部门占比列表")
    private List<QuoteRadioVO> radioList;
}