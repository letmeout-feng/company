package com.internal.manager.vo;

import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 管理系统-子产品对象VO
 *
 * @author internal
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageProductChildVO",description = "管理系统-子产品对象VO")
public class ManageProductChildVO extends ManageProductVO
{
    private static final long serialVersionUID=1L;

    /** 主产品状态 */
    @Schema(description = "主产品状态")
    @Excel(name = "主产品状态")
    private String parentStatus;

    /** 主产品名 */
    @Schema(description = "主产品名")
    @Excel(name = "主产品名")
    private String parentName;

    /** 主产品昵称 */
    @Schema(description = "主产品昵称")
    @Excel(name = "主产品昵称")
    private String parentShortName;

    /** 主产品名+状态 */
    @Schema(description = "主产品名+状态")
    private String parentNameAndStatus;

}