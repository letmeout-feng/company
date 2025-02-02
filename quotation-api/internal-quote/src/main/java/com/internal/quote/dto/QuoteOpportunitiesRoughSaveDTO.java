package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import com.internal.quote.domain.QuoteOpportunitiesRoughDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报价系统-商机粗略报价对象 quote_opportunities_rough
 *
 * @author internal
 * @date 2024-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesRoughSaveDTO", description = "报价系统-商机粗略报价对象")
public class QuoteOpportunitiesRoughSaveDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;
    /**
     * 商机售前支持ID
     */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesParentId;

    /**
     * 报价说明
     */
    @Schema(description = "报价说明")
    @Excel(name = "报价说明")
    private String valuationDesc;

    /**
     * 报价金额
     */
    @Schema(description = "报价金额")
    @Excel(name = "报价金额")
    private BigDecimal amount;

    /**
     * 版本状态[0暂存;1启用;2生效中]
     */
    @Schema(description = "版本状态[0暂存;1启用;2生效中]")
    @Excel(name = "版本状态[0暂存;1启用;2生效中]")
    private String status;

    /**
     * 详细报价信息
     */
    @Schema(description = "详细报价信息")
    @Excel(name = "详细报价信息")
    private List<QuoteOpportunitiesRoughDetail> quoteOpportunitiesRoughDetails;

    /** 报价部门占比列表 */
    @Schema(description = "报价部门占比列表")
    private List<QuoteRadioDTO> radioList;

}