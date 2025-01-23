package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 重新报价DTO
 *
 * @author internal
 * @date 2024-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ReQuoteDTO", description = "重新报价DTO")
public class ReQuoteDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 重新报价说明
     */
    @Schema(description = "重新报价说明")
    private String quoteDesc = "";

    /**
     * 原因说明
     */
    @Schema(description = "丢单说明")
    @Excel(name = "丢单说明")
    private String reasonDesc;

    /**
     * 售前ID列表(字符串)
     */
    @Schema(description = "售前ID列表(字符串)")
    private List<String> preSaleIdList;
    
}