package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-商机粗略报价对象 quote_opportunities_rough
 *
 * @author internal
 * @date 2024-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesRoughQuery", description = "报价系统-商机粗略查询")
public class QuoteOpportunitiesRoughQuery extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 是否获取最新版本
     */
    @Schema(description = "是否获取最新版本")
    private Boolean latest;

    /**
     * 数据隔离
     */
    @Schema(description = "数据隔离")
    private Boolean isolation = false;

}