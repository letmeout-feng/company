package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传文件类
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "UpLoadDTO", description = "上传文件类")
public class UpLoadDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 文件列表
     */
    @Schema(description = "文件列表")
    private List<MultipartFile> files;

    public UpLoadDTO(Long opportunitiesId, List<MultipartFile> files) {
        this.opportunitiesId = opportunitiesId;
        this.files = files;
    }
}