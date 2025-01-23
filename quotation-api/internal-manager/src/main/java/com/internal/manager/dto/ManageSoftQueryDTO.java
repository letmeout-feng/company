package com.internal.manager.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 管理系统-软著管理对象QueryDTO manage_soft
 *
 * @author internal
 * @date 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageSoftQueryDTO",description = "管理系统-软著管理对象QueryDTO")
public class ManageSoftQueryDTO extends BaseEntity {
private static final long serialVersionUID=1L;

    /** 软著类别[1:欣象软著,2:北光软著] */
    @Schema(description = "软著类别[1:欣象软著,2:北光软著]")
    @Excel(name = "软著类别[1:欣象软著,2:北光软著]")
    private String category;

    /** 软著简称 */
    @Schema(description = "软著简称")
    @Excel(name = "软著简称")
    private String shortName;

    /** 软著名称 */
    @Schema(description = "软著名称")
    @Excel(name = "软著名称")
    private String name;

    /** 版本号 */
    @Schema(description = "版本号")
    @Excel(name = "版本号")
    private String versionNumber;

    /** 登记号 */
    @Schema(description = "登记号")
    @Excel(name = "登记号")
    private String registrationMark;

    /** 状态[1:可用,2:不可用] */
    @Schema(description = "状态[1:可用,2:不可用]")
    @Excel(name = "状态[1:可用,2:不可用]")
    private String status;
}