package com.internal.quote.dto;

import com.internal.common.annotation.Excel;
import com.internal.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 报价系统-商机报价信息对象 quote_opportunities
 *
 * @author internal
 * @date 2024-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@Schema(name = "QuoteOpportunitiesQuery", description = "报价系统-商机报价信息对象")
public class QuoteOpportunitiesQuery extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商机名称
     */
    @Schema(description = "商机名称")
    @Excel(name = "商机名称")
    private String name;

    /**
     * 报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]
     */
    @Schema(description = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    @Excel(name = "报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]")
    private List<String> typeList;

    /**
     * 报价状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）
     */
    @Schema(description = "报价状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）")
    @Excel(name = "报价状态（报价申请:0,待成本报价:1,待销售报价:2,销售报价待审批:3,销售已报价:4,待签约:5,已签约:6,丢单:7,销售报价审批未通过:8,签约审批未通过:9）")
    private List<String> statusList;

    /**
     * 丢单类型
     */
    @Schema(description = "丢单类型")
    private String loseType;

    /**
     * 丢单类型
     */
    @Schema(description = "丢单类型列表")
    private List<String> loseTypeList;

    /**
     * 商机状态列表
     */
    @Schema(description = "商机状态列表")
    @Excel(name = "商机状态列表", readConverterExp = "商机状态列表")
    private List<String> parentStatusList;

    /**
     * 用于查询
     */
    @Schema(description = "用于查询")
    @Excel(name = "用于查询")
    private String query;

    /**
     * 报价方式[0:默认,1:自己报价,2:其他人报价]
     */
    @Schema(description = "报价方式[0:默认,1:自己报价,2:其他人报价]")
    @Excel(name = "报价方式[0:默认,1:自己报价,2:其他人报价]")
    private String quoteMethod;

    /**
     * 售前支持人员ID
     */
    @Schema(description = "售前支持人员ID")
    @Excel(name = "售前支持人员ID")
    private List<String> supportPerson = new ArrayList<>();

    /**
     * 所属销售
     */
    @Schema(description = "所属销售")
    @Excel(name = "所属销售")
    private List<String> salePerson = new ArrayList<>();

    /**
     * 启动权限认证
     */
    @Schema(description = "启动权限认证")
    @Excel(name = "启动权限认证")
    private Boolean auth;


    /**
     * 丢单日期-开始
     */
    @Schema(description = "丢单日期-开始")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String loseDateStart;

    /**
     * 丢单日期-结束
     */
    @Schema(description = "丢单日期-结束")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String loseDateEnd;

    /**
     * 本周丢单汇总-排序规则
     */
    @Schema(description = "本周丢单汇总-排序规则")
    private Boolean weekLose = false;

    public QuoteOpportunitiesQuery(String name, List<String> typeList, List<String> statusList, String loseType, List<String> loseTypeList,
                                   List<String> parentStatusList, String query, String quoteMethod, List<String> supportPerson, List<String> salePerson,
                                   Boolean auth, String loseDateStart, String loseDateEnd, Boolean weekLose) {
        this.name = name;
        this.typeList = typeList;
        this.statusList = statusList;
        this.loseType = loseType;
        this.loseTypeList = loseTypeList;
        this.parentStatusList = parentStatusList;
        this.query = query;
        this.quoteMethod = quoteMethod;
        this.supportPerson = supportPerson;
        this.salePerson = salePerson;
        this.auth = auth;
        this.loseDateStart = loseDateStart;
        this.loseDateEnd = loseDateEnd;
        this.weekLose = weekLose;
    }
}