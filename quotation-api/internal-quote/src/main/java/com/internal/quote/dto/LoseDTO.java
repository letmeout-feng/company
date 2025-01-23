package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 丢单用DTO
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "LoseDTO", description = "丢单用DTO")
public class LoseDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 原因说明
     */
    @Schema(description = "丢单说明")
    @Excel(name = "丢单说明")
    private String reasonDesc;

    /**
     * 丢单类型
     */
    @Schema(description = "丢单类型")
    private String loseType;
}