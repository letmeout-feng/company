package com.internal.quote.dto;

import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商机同步结构
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "BusinessOpportunityDTO", description = "商机同步结构")
public class BusinessOpportunityDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    @Schema(description = "商机ID")
    private String id;

    @Schema(description = "商机名称")
    private String name;

    @Schema(description = "商机编码")
    private String code;

    @Schema(description = "商机介绍")
    private String infoDesc;

    @Schema(description = "商机阶段")
    private String stage;

    @Schema(description = "商机阶段名称")
    private String stageName;

    @Schema(description = "客户ID")
    private String customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "归属人员ID")
    private String userId;

    @Schema(description = "归属人员名称")
    private String userName;

    @Schema(description = "售前支持ID")
    private String supportId;

    @Schema(description = "售前支持名称")
    private String supportType;

    @Schema(description = "产品大类名称")
    private String productName;

    @Schema(description = "报备状态ID")
    private String reportStatus;

    @Schema(description = "商机状态")
    private String currentState;

    @Schema(description = "售前ID")
    private String vdef2;

    @Schema(description = "成本售前支持区分")
    private String vdef3;

    @Schema(description = "售前支持创建人")
    private String supportCreateBy;

    @Schema(description = "售前支持创建时间")
    private Date supportCreateTime;

    @Schema(description = "售前支持更新人")
    private String supportUpdateBy;

    @Schema(description = "售前支持更新时间")
    private Date supportUpdateTime;

    @Schema(description = "申请支持目标及内容")
    private String applyDetail;

    public BusinessOpportunityDTO(String id, String name, String code, String infoDesc, String stage, String stageName, String customerId,
                                  String customerName, String userId, String userName, String supportId, String supportType, String productName,
                                  String reportStatus, String currentState, String vdef2, String vdef3, String supportCreateBy, Date supportCreateTime,
                                  String supportUpdateBy, Date supportUpdateTime, String applyDetail) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.infoDesc = infoDesc;
        this.stage = stage;
        this.stageName = stageName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.supportId = supportId;
        this.supportType = supportType;
        this.productName = productName;
        this.reportStatus = reportStatus;
        this.currentState = currentState;
        this.vdef2 = vdef2;
        this.vdef3 = vdef3;
        this.supportCreateBy = supportCreateBy;
        this.supportCreateTime = supportCreateTime;
        this.supportUpdateBy = supportUpdateBy;
        this.supportUpdateTime = supportUpdateTime;
        this.applyDetail = applyDetail;
    }
}