package com.internal.quote.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import com.internal.common.enums.ContractType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报价系统-商机售前报价信息对象 quote_presale_info
 *
 * @author internal
 * @date 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("QuotePresaleInfoQuery")
public class QuotePresaleInfoQuery extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    @Excel(name = "商机ID")
    private Long opportunitiesId;
    /**
     * 商机售前支持ID
     */
    @Schema(description = "商机售前支持ID")
    @Excel(name = "商机售前支持ID")
    private Long opportunitiesParentId;

    /**
     * 售前报价版本
     */
    @Schema(description = "售前报价版本")
    @Excel(name = "售前报价版本")
    private String preSaleVersion;

    /**
     * 版本状态[0暂存;未生效;2生效中]
     */
    @Schema(description = "版本状态[0暂存;未生效;2生效中]")
    @Excel(name = "版本状态[0暂存;未生效;2生效中]")
    private String status;

    /**
     * 合同类型
     */
    @Schema(description = "合同类型")
    @Excel(name = "合同类型")
    private String quoteType = ContractType.XX.getCode();

}