package com.internal.quote.vo;

import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表 sys_dept
 * 
 * @author every
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    @Schema(description = "部门ID")
    private Long deptId;

    /** crm部门ID */
    @Schema(description = "crm部门ID")
    private String crmDeptId;

    /** 部门名称 */
    @Schema(description = "部门名称")
    private String deptName;

    /** 用户名称 */
    @Schema(description = "用户名称")
    private String userNames;

    /** 用户crmId */
    @Schema(description = "用户crmId")
    private String crmUserIds;


}
