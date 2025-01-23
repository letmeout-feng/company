package com.internal.quote.vo;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-无法报价对象VO quote_opportunities_unable
 *
 * @author internal
 * @date 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesUnableVO",description = "报价系统-无法报价对象VO")
public class QuoteOpportunitiesUnableVO extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    private Long id;

    /** 商机售前支持ID */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
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

    /** 报价说明 */
    @Schema(description = "报价说明")
    @Excel(name = "报价说明")
    private String valuationDesc;

    /** 版本状态[0暂存;1未生效;2生效中] */
    @Schema(description = "版本状态[0暂存;1未生效;2生效中]")
    @Excel(name = "版本状态[0暂存;1未生效;2生效中]")
    private String status;


}