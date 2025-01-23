package com.internal.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理系统-目标管理子对象 manager_bu_goals
 *
 * @author internal
 * @date 2024-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "ManagerBuGoalsQueryDTO", description = "目标管理查询子对象")
public class ManagerBuGoalsQueryDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 项目主表ID
     */
    @Schema(description = "项目主表ID")
    @Excel(name = "项目主表ID")
    private Long parentId;

    /**
     * 目标年度
     */
    @Schema(description = "目标年度")
    @Excel(name = "目标年度")
    private String targetYear;

    /**
     * CRM主键ID
     */
    @Schema(description = "CRM主键ID")
    @Excel(name = "CRM主键ID")
    private String crmId;

    /**
     * 评估类型
     */
    @Schema(description = "评估类型")
    @Excel(name = "评估类型")
    private String assessment;

    /**
     * 目标责任人
     */
    @Schema(description = "目标责任人")
    @Excel(name = "目标责任人")
    private String responsePerson;

    /**
     * 开始日期
     */
    @Schema(description = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 结束日期
     */
    @Schema(description = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 比例
     */
    @Schema(description = "比例")
    @Excel(name = "比例")
    private Long proportion;

    /**
     * 基准值
     */
    @Schema(description = "基准值")
    @Excel(name = "基准值")
    private BigDecimal baseValue;

    /**
     * 冲刺比例
     */
    @Schema(description = "冲刺比例")
    @Excel(name = "冲刺比例")
    private Long sprintFproportion;

    /**
     * 冲刺值
     */
    @Schema(description = "冲刺值")
    @Excel(name = "冲刺值")
    private BigDecimal sprintValue;

    /**
     * CRM父级ID
     */
    @Schema(description = "CRM父级ID")
    @Excel(name = "CRM父级ID")
    private String crmParentId;

    public ManagerBuGoalsQueryDTO(Long id, Long parentId, String targetYear, String crmId, String assessment, String responsePerson,
                                  Date beginDate, Date endDate, Long proportion, BigDecimal baseValue, Long sprintFproportion, BigDecimal sprintValue, String crmParentId) {
        this.id = id;
        this.parentId = parentId;
        this.targetYear = targetYear;
        this.crmId = crmId;
        this.assessment = assessment;
        this.responsePerson = responsePerson;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.proportion = proportion;
        this.baseValue = baseValue;
        this.sprintFproportion = sprintFproportion;
        this.sprintValue = sprintValue;
        this.crmParentId = crmParentId;
    }
}