package com.internal.manager.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import com.internal.system.domain.SysFiles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 管理系统-软著管理对象VO manage_soft
 *
 * @author internal
 * @date 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(name = "ManageSoftVO",description = "管理系统-软著管理对象VO")
public class ManageSoftVO extends BaseEntity {
private static final long serialVersionUID=1L;

    /** ID */
    @Schema(description = "ID")
    private Long id;

    /** 软著类别[1:欣象软著,2:北光软著] */
    @Schema(description = "软著类别[1:欣象软著,2:北光软著]")
    @Excel(name = "软著类别",readConverterExp = "1=欣象软著,2=北光软著")
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
    private Long linkId;

    /** 对应软著名 */
    @Schema(description = "对应软著名(如果本数据是北光，就对应欣象)")
    @Excel(name = "对应软著名")
    private String linkName;

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
    @Excel(name = "状态",readConverterExp = "1=可用,2=不可用")
    private String status;

    /** 对应主产品名 */
    @Schema(description = "对应主产品名")
    @Excel(name = "对应主产品名")
    private String productName;
    /** 产品名+状态 */
    @Schema(description = "产品名+状态")
    private String productNameAndStatus;
    /**
     * 对应主产品id
     */
    @Schema(description = "对应主产品id")
    private Long productId;

    /**
     * 文件id
     */
    @Schema(description = "文件id")
    @TableField(value = "files")
    private String files;

    /**
     * 文件列表
     */
    @Schema(description = "文件列表")
    private List<SysFiles> fileList = new LinkedList<>();

}