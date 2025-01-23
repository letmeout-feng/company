package com.internal.quote.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
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
@Schema(name = "QuoteOpportunitiesVO", description = "报价系统-主表resultVO")
public class QuoteOpportunitiesVO extends BaseEntity {
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
    @Excel(name = "商机ID")
    private Long opportunitiesParentId;

    /**
     * 商机名称
     */
    @Schema(description = "商机名称")
    @Excel(name = "商机名称")
    private String name;

    /**
     * 报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]
     */
    @Schema(description = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    @Excel(name = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    private String type;

    /**
     * 类别[1:正常流程,2:需更新成本报价]
     */
    @Schema(description = "类别[1:正常流程,2:需更新成本报价]")
    @Excel(name = "类别[1:正常流程,2:需更新成本报价]")
    private String category;

    /**
     * 所属销售ID
     */
    @Schema(description = "所属销售ID")
    @Excel(name = "所属销售ID")
    private Long saleId;

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
     * 附件
     */
    @Schema(description = "附件")
    @Excel(name = "附件")
    private String appendix;

    /**
     * 商机介绍
     */
    @Schema(description = "商机介绍")
    @Excel(name = "商机介绍")
    private String introduce;

    /**
     * 报价说明[无法报价类型时使用]
     */
    @Schema(description = "报价说明[无法报价类型时使用]")
    @Excel(name = "报价说明[无法报价类型时使用]")
    private String quoteDesc;
    /**
     * 原因说明
     */
    @Schema(description = "丢单说明")
    @Excel(name = "丢单说明")
    private String reasonDesc;

    /**
     * 丢单时间
     */
    @Schema(description = "丢单时间")
    @Excel(name = "丢单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loseTime;

    /**
     * 销售试报价说明
     */
    @Schema(description = "销售试报价说明")
    @Excel(name = "销售试报价说明")
    private String trialQuoteDesc;

    /**
     * 成本报价金额
     */
    @Schema(description = "成本报价金额")
    @Excel(name = "成本报价金额")
    private BigDecimal amount;
    /**
     * 销售报价金额
     */
    @Schema(description = "销售报价金额")
    @Excel(name = "销售报价金额")
    private BigDecimal projExtQuoteTotal;
    /**
     * 签约金额
     */
    @Schema(description = "签约金额")
    @Excel(name = "签约金额")
    private BigDecimal signQuoteTotal;

    /**
     * 售前ID(不用)
     */
    /*@Schema(description = "售前ID(不用)")
    @Excel(name = "售前ID(不用)")
    private Long preSaleId;*/

    /**
     * 售前名称(显示用)
     */
    @Schema(description = "售前名称(显示用)")
    @Excel(name = "售前名称(显示用)")
    private String preSaleName;

    /**
     * 商机状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）
     */
    @Schema(description = "商机状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）")
    @Excel(name = "商机状态", readConverterExp = "商机状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）")
    private String status;

    /**
     * 是否存在暂存信息
     */
    @Schema(description = "是否存在暂存信息")
    private Boolean isExistDraft;

    /**
     * 成本报价版本(报价类型+报价版本号)
     */
    @Schema(description = "成本报价版本(报价类型+报价版本号)")
    private String currentVersion;
    /**
     * 成本报价版本
     */
    @Schema(description = "成本报价版本")
    private String costQuoteVersion;

    /**
     * 审核理由(销售报价)
     */
    @Schema(description = "审核理由(销售报价)")
    private String saleAuditDesc;
    /**
     * 审核理由(签约)
     */
    @Schema(description = "审核理由(签约)")
    private String signAuditDesc;

    /** 销售审核时间 */
    @Schema(description = "销售审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleAuditTime;


    /** 签约审核时间 */
    @Schema(description = "签约审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signAuditTime;
    /**
     * 销售审核记录
     */
    @Schema(description = "销售审核记录")
    @TableField(value = "sale_audit_log")
    private String saleAuditLog;

    /**
     * 签约审核记录
     */
    @Schema(description = "签约审核记录")
    @TableField(value = "sign_audit_log")
    private String signAuditLog;

    /**
     * 销售报价利润率(不包含外采硬件)
     */
    @Schema(description = "销售报价利润率(不包含外采硬件)")
    @Excel(name = "销售报价利润率(不包含外采硬件)")
    private BigDecimal projProfitRateExcl;
    /**
     * 签约利润率(不包含外采硬件)
     */
    @Schema(description = "签约利润率(不包含外采硬件)")
    @Excel(name = "签约利润率(不包含外采硬件)")
    private BigDecimal signProjProfitRateExcl;

    /**
     * 关单或跟进说明
     */
    @Schema(description = "关单或跟进说明")
    @Excel(name = "关单或跟进说明")
    private String closeNote;
    /**
     * 情况说明(无法报价)
     */
    @Schema(description = "情况说明(无法报价)")
    @Excel(name = "情况说明(无法报价)")
    private String description;

    /**
     * 报价内容
     */
    @Schema(description = "报价内容")
    @Excel(name = "报价内容")
    private String quoteContent;

    /**
     * 对应CRM售前支持ID
     */
    @Schema(description = "对应CRM售前支持ID")
    @Excel(name = "对应CRM售前支持ID")
    private String supportCrmId;
    /**
     * 售前支持类型
     */
    @Schema(description = "售前支持类型")
    @Excel(name = "售前支持类型")
    private String supportType;

    /**
     * 售前支持人员ID
     */
    @Schema(description = "售前支持人员ID")
    @Excel(name = "售前支持人员ID")
    private String supportPerson;


    /** 合同类型(欣象代理:1,北光直签:2) */
    @Schema(description = "合同类型(欣象代理:1,北光直签:2)")
    @Excel(name = "合同类型(欣象代理:1,北光直签:2)")
    private String contractType;

    /**
     * 流程类别[1:正常流程,2:需更新成本报价]
     */
    @Schema(description = "流程类别[1:正常流程,2:需更新成本报价]")
    @Excel(name = "流程类别[1:正常流程,2:需更新成本报价]")
    private String processCategory;

    /**
     * 报价方式[0:默认,1:自己报价,2:其他人报价]
     */
    @Schema(description = "报价方式[0:默认,1:自己报价,2:其他人报价]")
    @Excel(name = "报价方式[0:默认,1:自己报价,2:其他人报价]")
    private String quoteMethod;

    /**
     * 报价人列表
     */
    @Schema(description = "报价人列表")
    @Excel(name = "报价人列表")
    private String quoters;

    /**
     * 不报价人列表
     */
    @Schema(description = "不报价人列表")
    @Excel(name = "不报价人列表")
    private String nonQuoters;

    /**
     * 商机状态;[丢单:00001304,进行中:00001301,暂停:00001304,终止:00001305,成单:00001302]
     */
    @Schema(description = "商机状态;[丢单:00001304,进行中:00001301,暂停:00001304,终止:00001305,成单:00001302]")
    @TableField(value = "status")
    @Excel(name = "商机状态", readConverterExp = "商机状态;[丢单:00001304,进行中:00001301,暂停:00001304,终止:00001305,成单:00001302]")
    private String parentStatus;

    /**
     * 由其他人报价
     */
    @Schema(description = "由其他人报价")
    @Excel(name = "由其他人报价")
    private Boolean quoteByOthers = false;

    /**
     * 已选择报价方式
     */
    @Schema(description = "已选择报价方式")
    @Excel(name = "已选择报价方式")
    private Boolean methodSelected = false;

    /** 签约申请时间 */
    @Schema(description = "签约申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 是多部门
     */
    @Schema(description = "是多部门")
    @Excel(name = "是多部门")
    private Boolean isMultiDept;

    /**
     * 废弃原因
     */
    @Schema(description = "废弃原因")
    @Excel(name = "废弃原因")
    private String abandonDesc;

    /**
     * 丢单类型
     */
    @Schema(description = "丢单类型")
    @Excel(name = "丢单类型")
    private String loseType;

    /**
     * 丢单审核时间
     */
    @Schema(description = "丢单审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loseAuditTime;

    /**
     * 丢单审核记录
     */
    @Schema(description = "丢单审核记录")
    private String loseAuditLog;

    /**
     * 丢单审批理由
     */
    @Schema(description = "丢单审批理由")
    private String loseAuditDesc;

    /**
     * 丢单审批人
     */
    @Schema(description = "丢单审批人")
    private Long loseAuditPerson;

    /**
     * 销售审批人
     */
    @Schema(description = "销售审批人")
    private Long saleAuditPerson;

    /**
     * 签约审批人
     */
    @Schema(description = "签约审批人")
    private Long signAuditPerson;

    /**
     * 丢单审批人名
     */
    @Schema(description = "丢单审批人名")
    private String loseAuditPersonName;

    /**
     * 销售审批人名
     */
    @Schema(description = "销售审批人名")
    private String saleAuditPersonName;

    /**
     * 签约审批人名
     */
    @Schema(description = "签约审批人名")
    private String signAuditPersonName;

    /**
     * 丢单前状态
     */
    @Schema(description = "丢单前状态")
    private String statusBeforeLose;

    /**
     * 重新申请报价说明
     */
    @Schema(description = "重新申请报价说明")
    @Excel(name = "重新申请报价说明")
    private String reQuoteDesc;

    /** 申请签约说明 */
    @Schema(description = "申请签约说明")
    private String signDesc;


    /**
     * 申请支持内容
     */
    @Schema(description = "申请支持内容")
    private String applyDetail;
}