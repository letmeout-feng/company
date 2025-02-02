package com.internal.manager.domain;

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

import java.math.BigDecimal;

/**
 * 管理系统-预警设置对象 manage_warning
 *
 * @author internal
 * @date 2024-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "ManageWarning",description = "管理系统-预警设置对象")
@TableName("manage_warning")
public class ManageWarning extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 对外报价最低利润率 */
    @Schema(description = "对外报价最低利润率")
    @TableField(value = "external_profit")
    @Excel(name = "对外报价最低利润率")
    private BigDecimal externalProfit;

    /** 合同签约最低利润率 */
    @Schema(description = "合同签约最低利润率")
    @TableField(value = "contract_profit")
    @Excel(name = "合同签约最低利润率")
    private BigDecimal contractProfit;


    public ManageWarning (Long id, BigDecimal externalProfit, BigDecimal contractProfit) {
            this.id = id;
            this.externalProfit = externalProfit;
            this.contractProfit = contractProfit;
        }
}
