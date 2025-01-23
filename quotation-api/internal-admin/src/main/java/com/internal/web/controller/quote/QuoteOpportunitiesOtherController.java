package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesOther;
import com.internal.quote.dto.QuoteOpportunitiesDetailQuery;
import com.internal.quote.service.IQuoteOpportunitiesOtherService;
import com.internal.quote.vo.QuoteOpportunitiesOtherVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 详细报价表-商机其他小记Controller
 * 
 * @author internal
 * @date 2024-10-15
 */
@RestController
@AllArgsConstructor
@Tag(name = "详细报价表-商机其他小记表", description  = "详细报价表-商机其他小记表")
@RequestMapping("/quote/other")
public class QuoteOpportunitiesOtherController extends BaseController
{

    private final IQuoteOpportunitiesOtherService quoteOpportunitiesOtherService;

    /**
     * 查询报价系统-商机其他小记列表
     */
    @Operation(summary = "查询报价系统-商机其他小记列表", description  = "查询报价系统-商机其他小记列表")
    @PreAuthorize("@ss.hasPermi('quote:other:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesOther>> list(@RequestBody PageParams<QuoteOpportunitiesOther> params)
    {
        startPage(params);
        List<QuoteOpportunitiesOther> list = quoteOpportunitiesOtherService.selectQuoteOpportunitiesOtherList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机其他小记列表
     */
    @Operation(summary = "导出报价系统-商机其他小记列表", description  = "导出报价系统-商机其他小记列表")
    @PreAuthorize("@ss.hasPermi('quote:other:export')")
    @Log(title = "报价系统-商机其他小记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesOther quoteOpportunitiesOther)
    {
        List<QuoteOpportunitiesOther> list = quoteOpportunitiesOtherService.selectQuoteOpportunitiesOtherList(quoteOpportunitiesOther);
        ExcelUtil<QuoteOpportunitiesOther> util = new ExcelUtil<QuoteOpportunitiesOther>(QuoteOpportunitiesOther.class);
        util.exportExcel(response, list, "报价系统-商机其他小记数据");
    }

    /**
     * 获取报价系统-商机其他小记详细信息
     */
    @Operation(summary = "获取报价系统-商机其他小记详细信息", description  = "获取报价系统-商机其他小记详细信息")
    @PreAuthorize("@ss.hasPermi('quote:other:query')")
    @GetMapping(value = "/getOtherInfo")
    public R<QuoteOpportunitiesOtherVO> getOtherInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(quoteOpportunitiesOtherService.getOtherInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 新增报价系统-商机其他小记
     */
    @Operation(summary = "新增报价系统-商机其他小记", description  = "新增报价系统-商机其他小记")
    @PreAuthorize("@ss.hasPermi('quote:other:add')")
    @Log(title = "报价系统-商机其他小记", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesOther quoteOpportunitiesOther)
    {
        return R.ok(quoteOpportunitiesOtherService.insertQuoteOpportunitiesOther(quoteOpportunitiesOther));
    }

    /**
     * 修改报价系统-商机其他小记
     */
    @Operation(summary = "修改报价系统-商机其他小记", description  = "修改报价系统-商机其他小记")
    @PreAuthorize("@ss.hasPermi('quote:other:edit')")
    @Log(title = "报价系统-商机其他小记", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesOther quoteOpportunitiesOther)
    {
        return R.ok(quoteOpportunitiesOtherService.updateQuoteOpportunitiesOther(quoteOpportunitiesOther));
    }

    /**
     * 删除报价系统-商机其他小记
     */
    @Operation(summary = "删除报价系统-商机其他小记", description  = "删除报价系统-商机其他小记")
    @PreAuthorize("@ss.hasPermi('quote:other:remove')")
    @Log(title = "报价系统-商机其他小记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesOtherService.deleteQuoteOpportunitiesOtherByIds(ids));
    }
}
