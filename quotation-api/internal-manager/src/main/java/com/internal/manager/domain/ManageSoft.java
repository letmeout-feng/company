package com.internal.manager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 管理系统-软著管理对象 manage_soft
 *
 * @author internal
 * @date 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageSoft", description = "管理系统-软著管理对象")
@TableName("manage_soft")
public class ManageSoft extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 软著类别[1:欣象软著,2:北光软著]
     */
    @Schema(description = "软著类别[1:欣象软著,2:北光软著]")
    @TableField(value = "category")
    @Excel(name = "软著类别[1:欣象软著,2:北光软著]")
    private String category;

    /**
     * 软著简称
     */
    @Schema(description = "软著简称")
    @TableField(value = "short_name")
    @Excel(name = "软著简称")
    private String shortName;

    /**
     * 软著名称
     */
    @Schema(description = "软著名称")
    @TableField(value = "name")
    @Excel(name = "软著名称")
    private String name;

    /**
     * 对应软著id
     */
    @Schema(description = "对应软著id")
    @TableField(value = "link_id")
    @Excel(name = "对应软著id")
    private Long linkId;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @TableField(value = "version_number")
    @Excel(name = "版本号")
    private String versionNumber;

    /**
     * 登记号
     */
    @Schema(description = "登记号")
    @TableField(value = "registration_mark")
    @Excel(name = "登记号")
    private String registrationMark;

    /**
     * 登记批准日期
     */
    @Schema(description = "登记批准日期")
    @TableField(value = "reg_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记批准日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regTime;

    /**
     * 首次发表日期
     */
    @Schema(description = "首次发表日期")
    @TableField(value = "push_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次发表日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pushTime;

    /**
     * 状态[1:可用,2:不可用]
     */
    @Schema(description = "状态[1:可用,2:不可用]")
    @TableField(value = "status")
    @Excel(name = "状态[1:可用,2:不可用]")
    private String status;

    /**
     * 对应主产品id
     */
    @Schema(description = "对应主产品id")
    @TableField(value = "product_id")
    @Excel(name = "对应主产品id")
    private Long productId;

    /**
     * 文件id
     */
    @Schema(description = "文件id")
    @TableField(value = "files")
    private String files;


}