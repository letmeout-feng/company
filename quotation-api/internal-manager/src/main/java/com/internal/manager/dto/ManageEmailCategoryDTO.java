package com.internal.manager.dto;

import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 管理系统-成本设置对象 manage_cost
 *
 * @author internal
 * @date 2024-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageEmailCategoryDTO",description = "管理系统-成本设置对象")
public class ManageEmailCategoryDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String label;

}
