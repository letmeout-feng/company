package com.internal.quote.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-功能清单
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteOpportunitiesCustomizableImportVO", description = "报价系统-功能清单")
public class QuoteOpportunitiesCustomizableImportVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    @ExcelIgnore()
    private Long id;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    @ExcelProperty(value = "模块")
    private String moduleName;

    /**
     * 子模块功能
     */
    @Schema(description = "子模块功能")
    @ExcelProperty(value = "子模块功能")
    private String subModuleName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @ExcelProperty(value = "描述/备注")
    private String description;


}