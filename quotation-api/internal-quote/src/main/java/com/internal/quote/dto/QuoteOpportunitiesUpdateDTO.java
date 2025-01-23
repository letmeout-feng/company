package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 报价系统-商机报价信息对象 quote_opportunities
 *
 * @author internal
 * @date 2024-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesUpdateDTO", description = "报价系统-商机报价信息对象")
public class QuoteOpportunitiesUpdateDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    @NotNull(message = "商机ID不能为空")
    private Long opportunitiesId;

    /**
     * 商机售前支持ID
     */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesParentId;

    /**
     * 商机介绍
     */
    @Schema(description = "商机介绍")
    @Excel(name = "商机介绍")
    private String introduce;

    /**
     * 报价说明
     */
    @Schema(description = "报价说明")
    private String quoteDesc;

    /**
     * 原因说明
     */
    @Schema(description = "丢单说明")
    @Excel(name = "丢单说明")
    private String reasonDesc;

    /**
     * 附件信息
     */
    @Schema(description = "附件信息")
    @Excel(name = "附件信息")
    private List<QuoteOpportunitiesFileSaveDTO> quoteOpportunitiesFileSaveDTOList;
    
}