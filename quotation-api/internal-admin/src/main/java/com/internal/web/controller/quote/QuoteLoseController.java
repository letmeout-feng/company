package com.internal.web.controller.quote;

import cn.hutool.core.util.ObjectUtil;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.enums.VersionStatus;
import com.internal.common.request.PageParams;
import com.internal.quote.dto.*;
import com.internal.quote.service.*;
import com.internal.quote.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报价系统-丢单相关Controller
 *
 * @author internal
 * @date 2024-10-14
 */
@RestController
@AllArgsConstructor
@Tag(name = "报价系统-丢单相关", description = "报价系统-丢单相关")
@RequestMapping("/quote/lose")
public class QuoteLoseController extends BaseController {

    private final IQuoteOpportunitiesService quoteOpportunitiesService;
    private final IQuoteOpportunitiesRoughService roughService;
    private final IQuoteOpportunitiesDetailService detailService;
    private final IQuoteOpportunitiesUnableService unableService;
    private final IQuotePresaleInfoService presaleInfoService;
    private final IQuoteSignInfoService signInfoService;


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
     * 待确认丢单列表
     */
    @Operation(summary = "待确认丢单列表", description = "待确认丢单列表")
    @PreAuthorize("@ss.hasPermi('quote:wait:lose:page')")
    @PostMapping("/pageWaitLose")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageWaitLose(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单审批未通过
     */
    @Operation(summary = "丢单审批未通过", description = "丢单审批未通过")
    @PreAuthorize("@ss.hasPermi('quote:lose:reject:page')")
    @PostMapping("/pageLoseReject")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageLoseReject(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单汇总
     */
    @Operation(summary = "丢单汇总", description = "丢单汇总")
    @PreAuthorize("@ss.hasPermi('quote:summary:lose')")
    @PostMapping("/pageLoseSummary")
    public TableDataInfo<List<QuoteOpportunitiesVO>> pageLoseSummary(@RequestBody PageParams<QuoteOpportunitiesQuery> params) {
        return pageInfoAuth(params);
    }

    /**
     * 丢单处理
     */
    @Operation(summary = "丢单处理", description = "丢单处理")
    @PreAuthorize("@ss.hasPermi('quote:sale:handleLost','quote:rejected:handleLost','quote:rejected:update'" +
            ",'quote:order:handleLost','quote:order:contractRequest','quote:order:update','quote:contract:approval:handleLost','quote:contract:rejected:handleLost'" +
            ",'quote:lose:reject:handleLost')")
    @Log(title = "商机丢单处理", businessType = BusinessType.UPDATE)
    @PostMapping("/lose")
    public R<Boolean> lose(@RequestBody LoseDTO dto) {
        return R.ok(quoteOpportunitiesService.lose(dto));
    }

    /**
     * 丢单审批-通过
     */
    @Operation(summary = "丢单审批-通过", description = "丢单审批-通过")
    @Log(title = "丢单审批-通过", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping("/loseApproval")
    public R<Boolean> loseApproval(@RequestBody ApprovalDTO dto) {
        return R.ok(quoteOpportunitiesService.loseApproval(dto));
    }

    /**
     * 丢单审批-驳回
     */
    @Operation(summary = "丢单审批-驳回", description = "丢单审批-驳回")
    @Log(title = "丢单审批-驳回", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping("/loseReject")
    public R<Boolean> loseReject(@RequestBody ApprovalDTO dto) {
        return R.ok(quoteOpportunitiesService.loseReject(dto));
    }

    /**
     * 丢单审批页-粗略报价
     */
    @Operation(summary = "丢单审批页-粗略报价", description = "丢单审批页-粗略报价")
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping(value = "/rough")
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
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping(value = "/unable")
    public R<QuoteOpportunitiesUnableVO> getUnableInfo(@RequestBody BaseDTO dto)
    {
        return  R.ok(unableService.getByQuoteId(dto.getOpportunitiesId()));
    }

    /**
     * 丢单审批页-详细报价
     */
    @Operation(summary = "丢单审批页-详细报价", description = "丢单审批页-详细报价")
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping(value = "/detail")
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
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping("/presale")
    public R<SignDetailInfoVO> getPresaleInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = presaleInfoService.approvalAndSignInfo(dto.getOpportunitiesId(),false);
        return R.ok(result);
    }

    /**
     * 丢单审批页-签约
     */
    @Operation(summary = "丢单审批页-签约", description = "丢单审批页-签约")
    @PreAuthorize("@ss.hasPermi('quote:lose:approval')")
    @PostMapping("/sign")
    public R<SignDetailInfoVO> getSignInfo(@RequestBody BaseDTO dto)
    {
        SignDetailInfoVO result = signInfoService.getSignInfoByOpportunitiesId(dto);
        return R.ok(result);
    }
}
