package com.internal.web.controller.manager;

import cn.hutool.core.util.ObjectUtil;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.domain.entity.SysUser;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.enums.VersionStatus;
import com.internal.common.request.PageParams;
import com.internal.common.utils.SecurityUtils;
import com.internal.quote.dto.*;
import com.internal.quote.service.*;
import com.internal.quote.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 管理系统-报表Controller
 *
 * @author internal
 * @date 2024-10-09
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-报表", description  = "管理系统-报表")
@RequestMapping("/manager/report")
public class ManageReportController extends BaseController
{
    private final IQuoteOpportunitiesService quoteOpportunitiesService;
    private final IQuoteOpportunitiesDetailService detailService;
    private final IQuoteOpportunitiesRoughService roughService;
    private final IQuoteOpportunitiesUnableService unableService;
    private final IQuotePresaleInfoService presaleInfoService;
    private final IQuoteSignInfoService signInfoService;


    /**
     * 待成本报价列表
     */
    @Operation(summary = "待成本报价列表", description  = "待成本报价列表")
    @PreAuthorize("@ss.hasPermi('manage:await:page')")
    @PostMapping("/pageWithCost")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithCost(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        //筛选当前售前
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        if(ObjectUtil.isEmpty(currentUser)){
            throw new RuntimeException("无效的用户");
        }
        // 处理权限相关信息
        quoteOpportunitiesService.fillAuthInfo(currentUser,params);
        return pageInfo(params);
    }

    /**
     * 待销售报价列表
     */
    @Operation(summary = "待销售报价列表", description  = "待销售报价列表")
    @PreAuthorize("@ss.hasPermi('manage:await:sale:page')")
    @PostMapping("/pageWithSales")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithSales(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 待报价审批列表
     */
    @Operation(summary = "待报价审批列表", description  = "待报价审批列表")
    @PreAuthorize("@ss.hasPermi('manage:approval:page')")
    @PostMapping("/pageWithApproval")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithApproval(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 待签约审批列表
     */
    @Operation(summary = "待签约审批列表", description  = "待签约审批列表")
    @PreAuthorize("@ss.hasPermi('manage:contract:approval:page')")
    @PostMapping("/pageWithSign")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithSign(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 待签合同列表
     */
    @Operation(summary = "待签合同列表", description  = "待签合同列表")
    @PreAuthorize("@ss.hasPermi('manage:contract:pending:page')")
    @PostMapping("/pageWithContract")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithContract(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 已签约
     */
    @Operation(summary = "已签约", description  = "已签约")
    @PreAuthorize("@ss.hasPermi('manage:signed:page')")
    @PostMapping("/listWithSigned")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithSigned(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单(全部)
     */
    @Operation(summary = "丢单(全部)", description  = "丢单(全部)")
    @PreAuthorize("@ss.hasPermi('manage:lose:page')")
    @PostMapping("/listWithLose")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithLose(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单(销售审批未通过)
     */
    @Operation(summary = "丢单(销售审批未通过)", description  = "丢单(销售审批未通过)")
    @PreAuthorize("@ss.hasPermi('manage:lose:sale:page')")
    @PostMapping("/listWithLoseSale")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithLoseSale(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        params.getModel().setLoseTypeList(Arrays.asList("3"));
        return pageInfoAuth(params);
    }

    /**
     * 丢单(签约审批未通过)
     */
    @Operation(summary = "丢单(签约审批未通过)", description  = "丢单(签约审批未通过)")
    @PreAuthorize("@ss.hasPermi('manage:lose:sign:page')")
    @PostMapping("/listWithLoseSign")
    public TableDataInfo<List<QuoteOpportunitiesVO>> listWithLoseSign(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        params.getModel().setLoseTypeList(Arrays.asList("6"));
        return pageInfoAuth(params);
    }

    /**
     * 待确认丢单列表
     */
    @Operation(summary = "待确认丢单列表", description = "待确认丢单列表")
    @PreAuthorize("@ss.hasPermi('manage:wait:lose:page')")
    @PostMapping("/pageWaitLose")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageWaitLose(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单审批未通过
     */
    @Operation(summary = "丢单审批未通过", description = "丢单审批未通过")
    @PreAuthorize("@ss.hasPermi('manage:lose:reject:page')")
    @PostMapping("/pageLoseReject")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageLoseReject(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 本周丢单汇总
     */
    @Operation(summary = "本周丢单汇总", description = "本周丢单汇总")
    @PreAuthorize("@ss.hasPermi('manage:week:lose:page')")
    @PostMapping("/pageWeekLose")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageWeekLose(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        params.getModel().setWeekLose(true);
        return pageInfoAuth(params);
    }

    /**
     * 组装分页信息
     *
     * @param params params
     * @return TableDataInfo<List < QuoteOpportunitiesVO>>
     */
    private TableDataInfo<List<QuoteOpportunitiesVO>> pageInfo(PageParams<QuoteOpportunitiesQuery> params) {
        startPage(params);
        List<QuoteOpportunitiesVO> list = quoteOpportunitiesService.getQuoteOpportunitiesList(params.getModel(),true);
        TableDataInfo<List<QuoteOpportunitiesVO>> tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 组装分页信息
     *
     * @param params params
     * @return TableDataInfo<List < QuoteOpportunitiesVO>>
     */
    private TableDataInfo<List<QuoteOpportunitiesVO>> pageInfoAuth(PageParams<QuoteOpportunitiesQuery> params) {
        params.getModel().setAuth(Boolean.TRUE);
        return pageInfo(params);
    }

    /**
     * 获取所有成本报价版本
     */
    @Operation(summary = "获取所有成本报价版本", description  = "获取所有成本报价版本")
    @PostMapping("/version")
    public R<List<OpportunitiesDetailVersionVO>> getVersionList(@RequestBody QuoteOpportunitiesRoughQuery quoteOpportunitiesRoughQuery) {
        List<OpportunitiesDetailVersionVO> resultList = quoteOpportunitiesService.getVersionList(quoteOpportunitiesRoughQuery);
        return R.ok(resultList);
    }

    /**
     * 获取所有销售报价版本
     */
    @Operation(summary = "获取所有销售报价版本", description  = "获取所有销售报价版本")
    @PostMapping("/salesVersion")
    public R<List<OpportunitiesSalesVersionVO>> getSalesVersionList(@RequestBody QuoteOpportunitiesRoughQuery quoteOpportunitiesRoughQuery) {
        List<OpportunitiesSalesVersionVO> resultList = quoteOpportunitiesService.getSalesVersionList(quoteOpportunitiesRoughQuery);
        return R.ok(resultList);
    }

    /**
     * 获取所有签约申请版本
     */
    @Operation(summary = "获取所有签约申请版本", description  = "获取所有签约申请版本")
    @PostMapping("/signVersion")
    public R<List<OpportunitiesSignVersionVO>> getSignVersionList(@RequestBody QuoteOpportunitiesRoughQuery quoteOpportunitiesRoughQuery) {
        List<OpportunitiesSignVersionVO> resultList = quoteOpportunitiesService.getSignVersionList(quoteOpportunitiesRoughQuery);
        return R.ok(resultList);
    }

    /**
     * 详细报价详情
     */
    @Operation(summary = "详细报价详情", description  = "详细报价详情")
    @PostMapping(value = "/detailInfo")
    public R<QuoteOpportunitiesDetailVO> getDetailInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return R.ok(detailService.getDetailInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 粗略报价详情
     */
    @Operation(summary = "粗略报价详情", description  = "粗略报价详情")
    @PostMapping(value = "/roughInfo")
    public R<QuoteOpportunitiesRoughVO> getRoughInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(roughService.getRoughInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 无法报价详情
     */
    @Operation(summary = "无法报价详情", description  = "无法报价详情")
    @GetMapping(value = "/unable/{id}")
    public R<QuoteOpportunitiesUnableVO> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(unableService.selectQuoteOpportunitiesUnableById(id));
    }

    /**
     * 销售报价详情
     */
    @Operation(summary = "销售报价详情", description  = "销售报价详情")
    @PostMapping("/presaleInfo")
    public R<QuotePresaleInfoVO> getPresaleInfo(@RequestBody QuotePresaleInfoQuery query)
    {
        QuotePresaleInfoVO result = presaleInfoService.getDetailInfoById(query);
        return R.ok(result);
    }

    /**
     * 签约详情页(需要id)
     */
    @Operation(summary = "签约详情页(需要id)", description  = "签约详情页(需要id)")
    @PostMapping("/signInfo")
    public R<SignDetailInfoVO> getSignInfoById(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = signInfoService.getSignInfoById(dto);
        return R.ok(result);
    }

    /**
     * 丢单处理
     */
    @Operation(summary = "丢单处理", description = "丢单处理")
    @PreAuthorize("@ss.hasPermi('manage:sale:handleLost')")
    @Log(title = "商机丢单处理", businessType = BusinessType.UPDATE)
    @PostMapping("/lose")
    public R<Boolean> lose(@RequestBody LoseDTO dto) {
        Boolean result = false;
        result = quoteOpportunitiesService.lose(dto);

        return R.ok(result);
    }

    /**
     * 丢单审批-通过
     */
    @Operation(summary = "丢单审批-通过", description = "丢单审批-通过")
    @Log(title = "丢单审批-通过", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping("/loseApproval")
    public R<Boolean> loseApproval(@RequestBody ApprovalDTO dto) {
        return R.ok(quoteOpportunitiesService.loseApproval(dto));
    }

    /**
     * 丢单审批-驳回
     */
    @Operation(summary = "丢单审批-驳回", description = "丢单审批-驳回")
    @Log(title = "丢单审批-驳回", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping("/loseReject")
    public R<Boolean> loseReject(@RequestBody ApprovalDTO dto) {
        return R.ok(quoteOpportunitiesService.loseReject(dto));
    }

    /**
     * 丢单审批页-粗略报价
     */
    @Operation(summary = "丢单审批页-粗略报价", description = "丢单审批页-粗略报价")
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping(value = "/lose/rough")
    public R<QuoteOpportunitiesRoughVO> getRoughInfo(@RequestBody BaseDTO dto)
    {
        QuoteOpportunitiesDetailQuery query = new QuoteOpportunitiesDetailQuery();
        query.setOpportunitiesId(dto.getOpportunitiesId());
        query.setStatus(VersionStatus.ACTIVE.getCode());
        return  R.ok(roughService.getRoughInfo(query));
    }

    /**
     * 丢单审批页-无法报价
     */
    @Operation(summary = "丢单审批页-无法报价", description = "丢单审批页-无法报价")
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping(value = "/lose/unable")
    public R<QuoteOpportunitiesUnableVO> getUnableInfo(@RequestBody BaseDTO dto)
    {
        return  R.ok(unableService.getByQuoteId(dto.getOpportunitiesId()));
    }

    /**
     * 丢单审批页-详细报价
     */
    @Operation(summary = "丢单审批页-详细报价", description = "丢单审批页-详细报价")
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping(value = "/lose/detail")
    public R<QuoteOpportunitiesDetailVO> getDetailInfo(@RequestBody BaseDTO dto)
    {
        QuoteOpportunitiesDetailQuery query = new QuoteOpportunitiesDetailQuery();
        query.setOpportunitiesId(dto.getOpportunitiesId());
        query.setStatus(VersionStatus.ACTIVE.getCode());
        return  R.ok(detailService.getDetailInfo(query));
    }

    /**
     * 丢单审批页-销售
     */
    @Operation(summary = "丢单审批页-销售", description = "丢单审批页-销售")
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping("/lose/presale")
    public R<SignDetailInfoVO> getPresaleInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = presaleInfoService.approvalAndSignInfo(dto.getOpportunitiesId(),false);
        return R.ok(result);
    }

    /**
     * 丢单审批页-签约
     */
    @Operation(summary = "丢单审批页-签约", description = "丢单审批页-签约")
    @PreAuthorize("@ss.hasPermi('manage:lose:approval')")
    @PostMapping("/lose/sign")
    public R<SignDetailInfoVO> getSignInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = signInfoService.getSignInfoByOpportunitiesId(dto);
        return R.ok(result);
    }
}
