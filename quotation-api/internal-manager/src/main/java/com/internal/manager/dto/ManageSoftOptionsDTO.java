package com.internal.manager.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 管理系统-软著管理对象OptionsDTO manage_soft
 *
 * @author internal
 * @date 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageSoftOptionsDTO",description = "管理系统-软著管理对象OptionsDTO")
public class ManageSoftOptionsDTO extends BaseEntity
        {
private static final long serialVersionUID=1L;


    /** 软著类别[1:欣象软著,2:北光软著] */
    @Schema(description = "软著类别[1:欣象软著,2:北光软著]")
    @NotNull(message = "类别不能为空")
    @NotEmpty(message = "类别不能为空")
    @Excel(name = "软著类别[1:欣象软著,2:北光软著]")
    private String category;

    @Schema(description = "下拉框位置,[1:软著管理，2:产品管理]")
    private String from;

}