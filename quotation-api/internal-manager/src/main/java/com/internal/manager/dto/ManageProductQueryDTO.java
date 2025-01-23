package com.internal.manager.dto;

import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 管理系统-产品管理对象查询用DTO
 *
 * @author internal
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageProductQueryDTO",description = "管理系统-产品管理对象查询用DTO")
public class ManageProductQueryDTO extends BaseEntity
{
    /** 产品类别 */
    @Schema(description = "产品类别")
    private String category;

    /** 产品类别List */
    @Schema(description = "产品类别List")
    private List<String> categoryList;

    /** 产品名称 */
    @Schema(description = "产品名称")
    private String name;

    /** 主产品名 */
    @Schema(description = "主产品名")
    private String parentName;

    /** 产品状态[1:可用,2:不可用] */
    @Schema(description = "产品状态[1:可用,2:不可用]")
    private String status;

    /** 产品状态列表 */
    @Schema(description = "产品状态列表")
    private List<String> statusList;

    /** 主产品id */
    @Schema(description = "主产品id")
    private Long parentId;

    /**
     * 是否使用权限
     */
    @Schema(description = "是否使用权限")
    private Boolean isAuth;


}