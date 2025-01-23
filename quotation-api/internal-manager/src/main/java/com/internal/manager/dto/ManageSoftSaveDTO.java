package com.internal.manager.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import com.internal.system.domain.SysFiles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 管理系统-软著管理对象SaveVO manage_soft
 *
 * @author internal
 * @date 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageSoftSaveVO",description = "管理系统-软著管理对象SaveVO")
public class ManageSoftSaveDTO extends BaseEntity
        {
private static final long serialVersionUID=1L;


    /** 软著类别[1:欣象软著,2:北光软著] */
    @Schema(description = "软著类别[1:欣象软著,2:北光软著]")
    @NotNull(message = "类别不能为空")
    @NotEmpty(message = "类别不能为空")
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

    /** 对应软著id */
    @Schema(description = "对应软著id")
    @Excel(name = "对应软著id")
    private Long linkId;

    /** 版本号 */
    @Schema(description = "版本号")
    @Excel(name = "版本号")
    private String versionNumber;

    /** 登记号 */
    @Schema(description = "登记号")
    @Excel(name = "登记号")
    private String registrationMark;

    /** 登记批准日期 */
    @Schema(description = "登记批准日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记批准日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regTime;

    /** 首次发表日期 */
    @Schema(description = "首次发表日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次发表日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pushTime;

    /** 状态[1:可用,2:不可用] */
    @Schema(description = "状态[1:可用,2:不可用]")
    @Excel(name = "状态[1:可用,2:不可用]")
    private String status;

    /** 对应主产品id */
    @Schema(description = "对应主产品id")
    @Excel(name = "对应主产品id")
    private Long productId;

    /** 对应主产品id */
    @Schema(description = "文件列表")
    private List<SysFiles> fileList = new LinkedList<>();

}