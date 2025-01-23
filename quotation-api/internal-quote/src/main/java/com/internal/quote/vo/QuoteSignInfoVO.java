package com.internal.quote.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-待签合同VO
 *
 * @author internal
 * @date 2024-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "QuoteSignInfoVO",description = "报价系统-待签合同VO")
@TableName("quote_sign_info")
public class QuoteSignInfoVO extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** 主键ID */
    @Schema(description = "主键ID")
    private Long id;

    /** 商机售前支持ID */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesId;

    /** 商机主表ID */
    @Schema(description = "商机主表ID")
    @Excel(name = "商机主表ID")
    private Long opportunitiesParentId;

    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;

    /** 状态[0暂存;未生效;2生效中] */
    @Schema(description = "状态[0暂存;未生效;2生效中]")
    @Excel(name = "状态[0暂存;未生效;2生效中]")
    private String status;

    /** 申请签约说明 */
    @Schema(description = "申请签约说明")
    @Excel(name = "申请签约说明")
    private String signDesc;

    /** 销售报价id */
    /*@Schema(description = "销售报价id")
    @Excel(name = "销售报价id")
    private Long presaleId;*/

    /** 签约状态-欣象vs客户 */
    @Schema(description = "签约状态-欣象vs客户")
    @Excel(name = "签约状态-欣象vs客户")
    private Boolean xxVsCust;

    /** 签约状态-欣象vs北光 */
    @Schema(description = "签约状态-欣象vs北光")
    @Excel(name = "签约状态-欣象vs北光")
    private Boolean xxVsNl;

    /** 签约状态-北光vs客户 */
    @Schema(description = "签约状态-北光vs客户")
    @Excel(name = "签约状态-北光vs客户")
    private Boolean nlVsCust;


}