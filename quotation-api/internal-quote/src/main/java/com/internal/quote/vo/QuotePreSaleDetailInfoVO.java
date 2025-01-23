package com.internal.quote.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 报价系统-商机售前报价详细信息对象 QuotePreSaleDetailInfo
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "QuotePreSaleDetailInfoVO", description = "报价系统-商机售前报价详细信息对象")
public class QuotePreSaleDetailInfoVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;
    /**
     * 商机名称
     */
    @Schema(description = "商机名称")
    @Excel(name = "商机名称")
    private String name;
    /**
     * 产品类别
     */
    @Schema(description = "产品类别")
    @Excel(name = "产品类别")
    private String category;
    /**
     * 销售名称
     */
    @Schema(description = "销售名称")
    @Excel(name = "销售名称")
    private String saleName;
    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @Excel(name = "客户名称")
    private String customersName;
    /**
     * 售前名称
     */
    @Schema(description = "售前名称")
    @Excel(name = "售前名称")
    private String preSaleName;
    /**
     * 报价版本
     */
    @Schema(description = "报价版本")
    @Excel(name = "报价版本")
    private String quoteVersion;
    /**
     * 粗略报价金额
     */
    @Schema(description = "报价金额")
    @Excel(name = "报价金额")
    private BigDecimal quoteAmount;
    /**
     * 粗略报价说明
     */
    @Schema(description = "报价说明")
    @Excel(name = "报价说明")
    private String quoteDesc;
    /**
     * 销售报价详细信息
     */
    @Schema(description = "销售报价详细信息")
    @TableField(exist = false)
    private QuotePresaleInfoVO quotesPresaleInfo;

    public QuotePreSaleDetailInfoVO(Long id, String name, String category, String saleName, String customersName, String preSaleName,
                                    String quoteVersion, BigDecimal quoteAmount, String quoteDesc, QuotePresaleInfoVO quotesPresaleInfo) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.saleName = saleName;
        this.customersName = customersName;
        this.preSaleName = preSaleName;
        this.quoteVersion = quoteVersion;
        this.quoteAmount = quoteAmount;
        this.quoteDesc = quoteDesc;
        this.quotesPresaleInfo = quotesPresaleInfo;
    }
}
