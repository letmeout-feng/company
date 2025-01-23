package com.internal.common.core.domain.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.annotation.TemplateField;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 邮箱占位符转换信息
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "EmailModelInfoConvertDTO", description = "邮箱模版占位符转换信息")
public class EmailModelInfoConvertDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商机主题
     */
    @Schema(description = "商机主题")
    @TemplateField(value = "商机主题")
    private String businessSubject;


    /**
     * 报价系统链接
     */
    @Schema(description = "报价系统链接")
    @TemplateField(value = "报价系统链接")
    private String quoteSystemLink;

    /**
     * 售前部门
     */
    @Schema(description = "售前部门")
    @TemplateField(value = "售前部门")
    private String preSaleDepartment;

    /**
     * 成本总额
     */
    @Schema(description = "成本总额")
    @TemplateField(value = "成本总额",formatAsCurrency = true)
    private String costTotal = "无";

    /**
     * 所属销售
     */
    @Schema(description = "所属销售")
    @TemplateField(value = "所属销售")
    private String preSaleUser;

    /**
     * 所属售前
     */
    @Schema(description = "所属售前")
    @TemplateField(value = "所属售前")
    private String preSaleUserName;

    /**
     * 销售对外报价
     */
    @Schema(description = "销售对外报价")
    @TemplateField(value = "销售对外报价",formatAsCurrency = true)
    private String saleQuote = "无";

    /**
     * 项目报价利润率
     */
    @Schema(description = "项目报价利润率")
    @TemplateField(value = "项目报价利润率",suffix = "%")
    private String profitRate = "无";

    /**
     * 继续跟进原因
     */
    @Schema(description = "继续跟进原因")
    @TemplateField(value = "继续跟进原因")
    private String continueFollowReason;

    /**
     * 销售报价审批结果
     */
    @Schema(description = "销售报价审批结果")
    @TemplateField(value = "销售报价审批结果")
    private String approvalResult = "无";

    /**
     * 北光合同总金额
     */
    @Schema(description = "北光合同总金额")
    @TemplateField(value = "北光合同总金额",formatAsCurrency = true)
    private String northTotalAmount;

    /**
     * 北光合同金额不含硬件部分
     */
    @Schema(description = "北光合同金额不含硬件部分")
    @TemplateField(value = "北光合同金额不含硬件部分")
    private String northQuoteAmount;

    /**
     * 成本利润率
     */
    @Schema(description = "成本利润率")
    @TemplateField(value = "成本利润率",suffix = "%")
    private String costProfitRate;

    /**
     * 总成本利润率
     */
    @Schema(description = "总成本利润率")
    @TemplateField(value = "总成本利润率",suffix = "%")
    private String totalCostProfitRate;

    /**
     * 继续签约原因
     */
    @Schema(description = "继续签约原因")
    @TemplateField(value = "继续签约原因")
    private String continueSignReason = "无";

    /**
     * 签约申请审批结果
     */
    @Schema(description = "签约申请审批结果")
    @TemplateField(value = "签约申请审批结果")
    private String signApprovalResult = "无";

    /**
     * 报价申请结果
     */
    @Schema(description = "报价申请结果")
    @TemplateField(value = "报价申请结果")
    private String quoteApprovalResult = "无";

    /**
     * 申请签约金额
     */
    @Schema(description = "申请签约金额")
    @TemplateField(value = "申请签约金额",formatAsCurrency = true)
    private String applySignAmount = "无";

    /**
     * 项目签约利润率
     */
    @Schema(description = "项目签约利润率")
    @TemplateField(value = "项目签约利润率",suffix = "%")
    private String signProfitRate = "无";
    /**
     * 原因说明
     */
    @Schema(description = "丢单原因")
    @TemplateField(value = "丢单原因")
    @Excel(name = "丢单原因")
    private String loseReasonDesc = "无";

    /**
     * 签约申请详情图片
     */
    @Schema(description = "签约申请详情图片")
    @TemplateField(value = "签约申请详情图片")
    @Excel(name = "签约申请详情图片")
    private String signApprovalDetailPic;

    /**
     * 重新报价说明
     */
    @Schema(description = "重新报价说明")
    @TemplateField(value = "重新报价说明")
    @Excel(name = "重新报价说明")
    private String reQuoteDesc = "无";

    /**
     * 成本评估类型
     */
    @Schema(description = "成本评估类型")
    @TemplateField(value = "成本评估类型")
    @Excel(name = "成本评估类型")
    private String costAssessmentType;

    /**
     * 丢单理由
     */
    @Schema(description = "丢单理由")
    @TemplateField(value = "丢单理由")
    @Excel(name = "丢单理由")
    private String loseReason = "无";


    public EmailModelInfoConvertDTO(String businessSubject, String quoteSystemLink, String preSaleDepartment, String costTotal, String preSaleUser,
                                    String preSaleUserName, String saleQuote, String profitRate, String continueFollowReason, String approvalResult,
                                    String northTotalAmount, String northQuoteAmount, String costProfitRate, String totalCostProfitRate, String continueSignReason,
                                    String signApprovalResult, String quoteApprovalResult, String applySignAmount, String signProfitRate,String loseReasonDesc,
                                    String signApprovalDetailPic, String reQuoteDesc, String costAssessmentType, String loseReason) {
        this.businessSubject = businessSubject;
        this.quoteSystemLink = quoteSystemLink;
        this.preSaleDepartment = preSaleDepartment;
        this.costTotal = costTotal;
        this.preSaleUser = preSaleUser;
        this.preSaleUserName = preSaleUserName;
        this.saleQuote = saleQuote;
        this.profitRate = profitRate;
        this.continueFollowReason = continueFollowReason;
        this.approvalResult = approvalResult;
        this.northTotalAmount = northTotalAmount;
        this.northQuoteAmount = northQuoteAmount;
        this.costProfitRate = costProfitRate;
        this.totalCostProfitRate = totalCostProfitRate;
        this.continueSignReason = continueSignReason;
        this.signApprovalResult = signApprovalResult;
        this.quoteApprovalResult = quoteApprovalResult;
        this.applySignAmount = applySignAmount;
        this.signProfitRate = signProfitRate;
        this.loseReasonDesc = loseReasonDesc;
        this.signApprovalDetailPic = signApprovalDetailPic;
        this.reQuoteDesc = reQuoteDesc;
        this.costAssessmentType = costAssessmentType;
        this.loseReason = loseReason;
    }
}