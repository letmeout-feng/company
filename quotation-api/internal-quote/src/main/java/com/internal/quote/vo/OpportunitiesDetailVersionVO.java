package com.internal.quote.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.internal.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价系统-版本对象
 *
 * @author internal
 * @date 2024-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@Schema(name = "OpportunitiesDetailVersionVO", description = "报价系统-版本对象")
public class OpportunitiesDetailVersionVO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;

    /**
     * 版本
     */
    @Schema(description = "版本")
    @Excel(name = "版本")
    private String version;
    /**
     * 评估版本
     */
    @Schema(description = "评估版本")
    @Excel(name = "评估版本")
    private String valuationVersion;

    /**
     * 版本类型
     */
    @Schema(description = "版本类型")
    @Excel(name = "版本类型")
    private String versionType;

    /**
     * 评估金额
     */
    @Schema(description = "评估金额")
    @TableField(value = "amount")
    @Excel(name = "评估金额")
    private BigDecimal amount;

    /**
     * 评估日期
     */
    @Schema(description = "评估日期")
    @TableField(value = "date")
    @Excel(name = "评估日期")
    private String date;

    /** 创建时间 */
    @Schema(description = "创建时间")
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 评估人
     */
    @Schema(description = "评估人")
    @Excel(name = "评估人")
    private String preSaleName;

    /** 创建者 */
    @Schema(description = "创建者")
    @Excel(name = "创建者")
    private String createBy;


    public OpportunitiesDetailVersionVO(Long id, Long opportunitiesId,String version, String valuationVersion, String versionType,BigDecimal amount, String date,Date createTime,String preSaleName,String createBy) {
        this.id = id;
        this.opportunitiesId = opportunitiesId;
        this.version = version;
        this.valuationVersion = valuationVersion;
        this.versionType = versionType;
        this.amount = amount;
        this.date = date;
        this.createTime = createTime;
        this.preSaleName = preSaleName;
        this.createBy = createBy;
    }
}