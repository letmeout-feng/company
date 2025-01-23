package com.internal.system.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 项目文件对象 sys_files
 *
 * @author internal
 * @date 2025-01-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "SysFiles",description = "项目文件对象")
@TableName("sys_files")
public class SysFiles extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** 主键 */
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 文件类型 */
    @Schema(description = "文件类型")
    @TableField(value = "file_type")
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件相对地址 */
    @Schema(description = "文件相对地址")
    @TableField(value = "path")
    @Excel(name = "文件相对地址")
    private String path;

    /** 文件访问地址 */
    @Schema(description = "文件访问地址")
    @TableField(value = "url")
    @Excel(name = "文件访问地址")
    private String url;

    /** 唯一文件名 */
    @Schema(description = "唯一文件名")
    @TableField(value = "unique_file_name")
    @Excel(name = "唯一文件名")
    private String uniqueFileName;

    /** 原始文件名 */
    @Schema(description = "原始文件名")
    @TableField(value = "original_file_name")
    @Excel(name = "原始文件名")
    private String originalFileName;

    /** 后缀 */
    @Schema(description = "后缀")
    @TableField(value = "suffix")
    @Excel(name = "后缀")
    private String suffix;


    public SysFiles (Long id,String fileType,String path,String url,String uniqueFileName,String originalFileName,String suffix){
                this.id = id;
                this.fileType = fileType;
                this.path = path;
                this.url = url;
                this.uniqueFileName = uniqueFileName;
                this.originalFileName = originalFileName;
                this.suffix = suffix;
    }
}