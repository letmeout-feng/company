package com.internal.quote.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * CRM商机更新参数
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "BusinessOpportunityUpdateDTO", description = "CRM商机更新参数")
public class BusinessOpportunityUpdateDTO{
    private static final long serialVersionUID = 1L;


    /**
     * CRM系统商机ID
     */
    @Schema(description = "CRM系统商机ID")
    private String id;

    /**
     * CRM商机阶段
     */
    @Schema(description = "CRM商机阶段")
    private String stage;

    /**
     * CRM商机状态
     */
    @Schema(description = "CRM商机状态")
    private String currentState;

    public BusinessOpportunityUpdateDTO(String id, String stage, String currentState) {
        this.id = id;
        this.stage = stage;
        this.currentState = currentState;
    }
}