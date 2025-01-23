package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 报价系统-商机售前支持详情对象 quote_opportunities_support_detail
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "QuoteOpportunitiesSupportSaveDTO", description = "报价系统-商机售前支持对象")
public class QuoteOpportunitiesSupportSaveDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 商机详情ID
     */
    @Schema(description = "商机详情ID")
    @Excel(name = "商机详情ID")
    private Long opportunitiesDetailId = 0L;

    /**
     * 售前支持类型;[已投入:INVESTED,预计后期投入:LATE_INPUTS]
     */
    @Schema(description = "售前支持类型;[已投入:INVESTED,预计后期投入:LATE_INPUTS] ")
    @NotEmpty(message = "售前支持类型不能为空")
    @Excel(name = "售前支持类型;[已投入:INVESTED,预计后期投入:LATE_INPUTS] ")
    private String type;

    /**
     * 已投入内容（人月）
     */
    @Schema(description = "内容（人月）")
    @NotEmpty(message = "内容（人月）不能为空")
    @Excel(name = "内容", readConverterExp = "人=月")
    private String content;

    /**
     * 已投入公司内部
     */
    @Schema(description = "公司内部")
    @NotNull(message = "公司内部不能为空")
    @Excel(name = "公司内部")
    private BigDecimal within = BigDecimal.ZERO;

    /**
     * 已投入本地外派
     */
    @Schema(description = "本地外派")
    @NotNull(message = "本地外派不能为空")
    @Excel(name = "本地外派")
    private BigDecimal local = BigDecimal.ZERO;

    /**
     * 单位类型[人天:HUMAN_NATURE,人月:HUMAN_MONTH]
     */
    @Schema(description = "单位类型[人天:HUMAN_NATURE,人月:HUMAN_MONTH]")
    @NotEmpty(message = "单位类型不能为空")
    @Excel(name = "单位")
    private String unit;

    /**
     * 已投入外地外派
     */
    @Schema(description = "外地外派")
    @NotNull(message = "外地外派不能为空")
    @Excel(name = "外地外派")
    private BigDecimal remote = BigDecimal.ZERO;

    public QuoteOpportunitiesSupportSaveDTO(Long opportunitiesDetailId, String type, String content, BigDecimal within, BigDecimal local, String unit, BigDecimal remote) {
        this.opportunitiesDetailId = opportunitiesDetailId;
        this.type = type;
        this.content = content;
        this.within = within;
        this.local = local;
        this.unit = unit;
        this.remote = remote;
    }
}