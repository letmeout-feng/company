package com.internal.quote.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售报价版本
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(name = "OpportunitiesSalesVersionVO", description = "销售报价版本")
public class OpportunitiesSalesVersionVO {
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
    private Long opportunitiesId;

    /**
     * 售前报价版本
     */
    @Schema(description = "销售报价版本")
    private String preSaleVersion;

    /**
     * 报价金额(不包含外采硬件)
     */
    @Schema(description = "报价金额(不包含外采硬件)")
    private BigDecimal amountExcl;
    /**
     * 报价金额(包含外采硬件)
     */
    @Schema(description = "报价金额(包含外采硬件)")
    private BigDecimal amountIncl;

    /**
     * 报价日期
     */
    @Schema(description = "报价日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 项目利润率(不包含外采硬件)
     */
    @Schema(description = "项目利润率(不包含外采硬件)")
    private BigDecimal projProfitRateExcl = BigDecimal.ZERO;

    /**
     * 项目利润率(含外采硬件)
     */
    @Schema(description = "项目利润率(含外采硬件)")
    private BigDecimal projProfitRateIncl = BigDecimal.ZERO;

    /**
     * 报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]
     */
    @Schema(description = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    @Excel(name = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    private String type;

}