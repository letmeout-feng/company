package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesSupport;
import com.internal.quote.dto.QuoteOpportunitiesDetailQuery;
import com.internal.quote.service.IQuoteOpportunitiesSupportService;
import com.internal.quote.vo.QuoteOpportunitiesSupportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 详细报价表-商机售前支持详情Controller
 * 
 * @author internal
 * @date 2024-10-15
 */
@RestController
@AllArgsConstructor
@Tag(name = "详细报价表-商机售前支持详情表", description  = "详细报价表-商机售前支持详情表")
@RequestMapping("/quote/support/detail")
public class QuoteOpportunitiesSupportController extends BaseController
{

    private final IQuoteOpportunitiesSupportService quoteOpportunitiesSupportService;

    /**
     * 查询报价系统-商机售前支持详情列表
     */
    @Operation(summary = "查询报价系统-商机售前支持详情列表", description  = "查询报价系统-商机售前支持详情列表")
    @PreAuthorize("@ss.hasPermi('quote:detail:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesSupport>> list(@RequestBody PageParams<QuoteOpportunitiesSupport> params)
    {
        startPage(params);
        List<QuoteOpportunitiesSupport> list = quoteOpportunitiesSupportService.selectQuoteOpportunitiesSupportList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机售前支持详情列表
     */
    @Operation(summary = "导出报价系统-商机售前支持详情列表", description  = "导出报价系统-商机售前支持详情列表")
    @PreAuthorize("@ss.hasPermi('quote:detail:export')")
    @Log(title = "报价系统-商机售前支持详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesSupport quoteOpportunitiesSupportDetail)
    {
        List<QuoteOpportunitiesSupport> list = quoteOpportunitiesSupportService.selectQuoteOpportunitiesSupportList(quoteOpportunitiesSupportDetail);
        ExcelUtil<QuoteOpportunitiesSupport> util = new ExcelUtil<QuoteOpportunitiesSupport>(QuoteOpportunitiesSupport.class);
        util.exportExcel(response, list, "报价系统-商机售前支持详情数据");
    }

    /**
     * 获取报价系统-商机售前支持详情详细信息
     */
    @Operation(summary = "获取报价系统-商机售前支持详情详细信息", description  = "获取报价系统-商机售前支持详情详细信息")
    @PreAuthorize("@ss.hasPermi('quote:detail:query')")
    @PostMapping(value = "/getSupportInfo")
    public R<QuoteOpportunitiesSupportVO> getSupportInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(quoteOpportunitiesSupportService.getSupportInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 新增报价系统-商机售前支持详情
     */
    @Operation(summary = "新增报价系统-商机售前支持详情", description  = "新增报价系统-商机售前支持详情")
    @PreAuthorize("@ss.hasPermi('quote:detail:add')")
    @Log(title = "报价系统-商机售前支持详情", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesSupport quoteOpportunitiesSupportDetail)
    {
        return R.ok(quoteOpportunitiesSupportService.insertQuoteOpportunitiesSupport(quoteOpportunitiesSupportDetail));
    }

    /**
     * 修改报价系统-商机售前支持详情
     */
    @Operation(summary = "修改报价系统-商机售前支持详情", description  = "修改报价系统-商机售前支持详情")
    @PreAuthorize("@ss.hasPermi('quote:detail:edit')")
    @Log(title = "报价系统-商机售前支持详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesSupport quoteOpportunitiesSupportDetail)
    {
        return R.ok(quoteOpportunitiesSupportService.updateQuoteOpportunitiesSupport(quoteOpportunitiesSupportDetail));
    }

    /**
     * 删除报价系统-商机售前支持详情
     */
    @Operation(summary = "删除报价系统-商机售前支持详情", description  = "删除报价系统-商机售前支持详情")
    @PreAuthorize("@ss.hasPermi('quote:detail:remove')")
    @Log(title = "报价系统-商机售前支持详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesSupportService.deleteQuoteOpportunitiesSupportByIds(ids));
    }
}
