package com.internal.quote.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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
@Builder
@Schema(name = "QuoteOpportunitiesDetailQuery", description = "报价系统-商机详细报价对象")
public class QuoteOpportunitiesDetailQuery {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 商机ID列表
     */
    @Schema(description = "商机ID列表")
    @Excel(name = "商机ID列表")
    private List<Long> quoteIdList;

    /**
     * 商机详情ID
     */
    @Schema(description = "商机详情ID")
    @Excel(name = "商机详情ID")
    private Long opportunitiesDetailId;

    /**
     * 商机售前支持ID
     */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesParentId;

    /**
     * 评估版本
     */
    @Schema(description = "评估版本")
    @Excel(name = "评估版本")
    private String valuationVersion;

    /**
     * 评估说明
     */
    @Schema(description = "评估说明")
    @Excel(name = "评估说明")
    private String valuationDesc;

    /**
     * 评估金额
     */
    @Schema(description = "评估金额")
    @Excel(name = "评估金额")
    private BigDecimal amount;

    /**
     * 版本状态（0暂存 1启用）
     */
    @Schema(description = "版本状态（0暂存 1启用）")
    @Excel(name = "版本状态", readConverterExp = "0=暂存,1=启用")
    private String status;

    /**
     * 是否获取最新版本
     */
    @Schema(description = "是否获取最新版本")
    private Boolean latest;

    public QuoteOpportunitiesDetailQuery(Long id, Long opportunitiesId,List<Long> quoteIdList, Long opportunitiesDetailId, Long opportunitiesParentId,
                                         String valuationVersion, String valuationDesc, BigDecimal amount, String status, Boolean latest) {
        this.id = id;
        this.opportunitiesId = opportunitiesId;
        this.quoteIdList = quoteIdList;
        this.opportunitiesDetailId = opportunitiesDetailId;
        this.opportunitiesParentId = opportunitiesParentId;
        this.valuationVersion = valuationVersion;
        this.valuationDesc = valuationDesc;
        this.amount = amount;
        this.status = status;
        this.latest = latest;
    }
}