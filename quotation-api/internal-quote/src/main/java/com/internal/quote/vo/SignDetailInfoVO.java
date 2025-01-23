package com.internal.quote.vo;

import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-确认报价对象 ConfirmQuoteInfoVO
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(name = "ConfirmQuoteInfoVO", description = "报价系统-确认报价对象")
public class SignDetailInfoVO extends ApprovalAndSignInfoVO {

    /** 申请签约说明 */
    @Schema(description = "申请签约说明")
    @Excel(name = "申请签约说明")
    private String signDesc;
    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;

}
