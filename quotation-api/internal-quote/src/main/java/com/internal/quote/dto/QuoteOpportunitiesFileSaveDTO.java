package com.internal.quote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商机附件对象 quote_opportunities_file
 *
 * @author internal
 * @date 2024-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "QuoteOpportunitiesFileSaveDTO", description = "商机附件对象")
public class QuoteOpportunitiesFileSaveDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @TableField(value = "opportunities_id")
    @Excel(name = "商机ID")
    private Long opportunitiesId;


    /**
     * 文件相对地址
     */
    @Schema(description = "文件相对地址")
    @TableField(value = "path")
    @Excel(name = "文件相对地址")
    private String path;

    /**
     * 文件访问地址
     */
    @Schema(description = "文件访问地址")
    @TableField(value = "url")
    @Excel(name = "文件访问地址")
    private String url;

    /**
     * 唯一文件名
     */
    @Schema(description = "唯一文件名")
    @TableField(value = "unique_file_name")
    @Excel(name = "唯一文件名")
    private String uniqueFileName;

    /**
     * 原始文件名
     */
    @Schema(description = "原始文件名")
    @TableField(value = "original_file_name")
    @Excel(name = "原始文件名")
    private String originalFileName;

    /**
     * 后缀
     */
    @Schema(description = "后缀")
    @TableField(value = "suffix")
    @Excel(name = "后缀")
    private String suffix;


    public QuoteOpportunitiesFileSaveDTO(Long opportunitiesId, String path, String url, String uniqueFileName, String originalFileName, String suffix) {
        this.opportunitiesId = opportunitiesId;
        this.path = path;
        this.url = url;
        this.uniqueFileName = uniqueFileName;
        this.originalFileName = originalFileName;
        this.suffix = suffix;
    }
}