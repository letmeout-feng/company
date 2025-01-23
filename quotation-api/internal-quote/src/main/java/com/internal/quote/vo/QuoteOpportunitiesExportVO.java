package com.internal.quote.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价系统-主表resultVO
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ExcelIgnoreUnannotated
@HeadRowHeight(25) // 设置表头行高
@ContentRowHeight(20) // 设置内容行高
@Schema(name = "QuoteOpportunitiesVO", description = "报价系统-主表resultVO")
public class QuoteOpportunitiesExportVO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @ExcelProperty(value = "序号")
    @ColumnWidth(15) // 设置列宽
    private String id;

    /**
     * 商机名称
     */
    @Schema(description = "商机名称")
    @ExcelProperty(value = "商机名称")
    @ColumnWidth(25)
    private String name = "-";

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @ExcelProperty(value = "客户名称")
    @ColumnWidth(20)
    private String customersName = "-";

    /**
     * 销售名称
     */
    @Schema(description = "销售名称")
    @ExcelProperty(value = "所属销售")
    @ColumnWidth(20)
    private String saleName = "-";


    /**
     * 售前名称(显示用)
     */
    @Schema(description = "售前名称(显示用)")
    @ExcelProperty(value = "所属售前")
    @ColumnWidth(20)
    private String preSaleName = "-";

    /**
     * 成本报价金额
     */
    @Schema(description = "成本报价金额")
    @ExcelProperty(value = "成本报价")
    @ColumnWidth(20)
    @NumberFormat("#,##0.00")
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 销售报价金额
     */
    @Schema(description = "销售报价金额")
    @ExcelProperty(value = "销售对外报价")
    @ColumnWidth(20)
    @NumberFormat("#,##0.00")
    private BigDecimal projExtQuoteTotal = BigDecimal.ZERO;

    /**
     * 签约金额
     */
    @Schema(description = "签约金额")
    @ExcelProperty(value = "合同报价")
    @ColumnWidth(20)
    @NumberFormat("#,##0.00")
    private BigDecimal signQuoteTotal = BigDecimal.ZERO;

    /**
     * 销售报价利润率(不包含外采硬件)
     */
    @Schema(description = "销售报价利润率(不包含外采硬件)")
    @ExcelProperty(value = "整体利润率(不含外采)")
    @ColumnWidth(30)
    @NumberFormat("#,##0.00")
    private BigDecimal projProfitRateExcl = BigDecimal.ZERO;

    /**
     * 原因说明
     */
    @Schema(description = "丢单说明")
    @ExcelProperty(value = "丢单理由")
    @ColumnWidth(30)
    private String reasonDesc;

    /**
     * 丢单时间
     */
    @Schema(description = "丢单时间")
    @ExcelProperty(value = "申请丢单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(25)
    private Date loseTime;

    /**
     * 商机状态
     */
    @Schema(description = "商机状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）")
    @ExcelProperty(value = "当前状态")
    @ColumnWidth(15)
    private String status;

    /**
     * 丢单审批理由
     */
    @Schema(description = "丢单审批理由")
    @ExcelProperty(value = "丢单审批说明")
    @ColumnWidth(30)
    private String loseAuditDesc = "-";

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(25)
    private Date updateTime;
}