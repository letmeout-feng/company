package com.internal.quote.dto;

import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商机状态同步结构
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "BusinessOpportunityStatusDTO", description = "商机状态同步结构")
public class BusinessOpportunityStatusDTO extends BaseEntity {
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

    @Schema(description = "客户ID")
    private String customerId;

    @Schema(description = "报备状态ID")
    private String reportStatus;

    @Schema(description = "商机状态")
    private String currentState;

    @Schema(description = "失败原因")
    private String failureCause;

    public BusinessOpportunityStatusDTO(String id, String name, String code, String infoDesc, String stage, String customerId,
                                        String reportStatus, String currentState, String failureCause) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.infoDesc = infoDesc;
        this.stage = stage;
        this.customerId = customerId;
        this.reportStatus = reportStatus;
        this.currentState = currentState;
        this.failureCause = failureCause;
    }
}