package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesImplement;
import com.internal.quote.dto.QuoteOpportunitiesDetailQuery;
import com.internal.quote.service.IQuoteOpportunitiesImplementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 详细报价表-商机实施小记Controller
 * 
 * @author internal
 * @date 2024-10-15
 */
@RestController
@AllArgsConstructor
@Tag(name = "详细报价表-商机实施小记表", description  = "详细报价表-商机实施小记表")
@RequestMapping("/quote/implement")
public class QuoteOpportunitiesImplementController extends BaseController
{

    private final IQuoteOpportunitiesImplementService quoteOpportunitiesImplementService;

    /**
     * 查询报价系统-商机实施小记列表
     */
    @Operation(summary = "查询报价系统-商机实施小记列表", description  = "查询报价系统-商机实施小记列表")
    @PreAuthorize("@ss.hasPermi('quote:implement:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesImplement>> list(@RequestBody PageParams<QuoteOpportunitiesImplement> params)
    {
        startPage(params);
        List<QuoteOpportunitiesImplement> list = quoteOpportunitiesImplementService.selectQuoteOpportunitiesImplementList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机实施小记列表
     */
    @Operation(summary = "导出报价系统-商机实施小记列表", description  = "导出报价系统-商机实施小记列表")
    @PreAuthorize("@ss.hasPermi('quote:implement:export')")
    @Log(title = "报价系统-商机实施小记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesImplement quoteOpportunitiesImplement)
    {
        List<QuoteOpportunitiesImplement> list = quoteOpportunitiesImplementService.selectQuoteOpportunitiesImplementList(quoteOpportunitiesImplement);
        ExcelUtil<QuoteOpportunitiesImplement> util = new ExcelUtil<QuoteOpportunitiesImplement>(QuoteOpportunitiesImplement.class);
        util.exportExcel(response, list, "报价系统-商机实施小记数据");
    }

    /**
     * 获取报价系统-商机实施小记详细信息
     */
    @Operation(summary = "获取报价系统-商机实施小记详细信息", description  = "获取报价系统-商机实施小记详细信息")
    @PreAuthorize("@ss.hasPermi('quote:implement:query')")
    @PostMapping(value = "/getImplementInfo")
    public R<QuoteOpportunitiesImplement> getInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(quoteOpportunitiesImplementService.selectQuoteOpportunitiesImplementById(quoteOpportunitiesDetailQuery.getOpportunitiesDetailId()));
    }

    /**
     * 新增报价系统-商机实施小记
     */
    @Operation(summary = "新增报价系统-商机实施小记", description  = "新增报价系统-商机实施小记")
    @PreAuthorize("@ss.hasPermi('quote:implement:add')")
    @Log(title = "报价系统-商机实施小记", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesImplement quoteOpportunitiesImplement)
    {
        return R.ok(quoteOpportunitiesImplementService.insertQuoteOpportunitiesImplement(quoteOpportunitiesImplement));
    }

    /**
     * 修改报价系统-商机实施小记
     */
    @Operation(summary = "修改报价系统-商机实施小记", description  = "修改报价系统-商机实施小记")
    @PreAuthorize("@ss.hasPermi('quote:implement:edit')")
    @Log(title = "报价系统-商机实施小记", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesImplement quoteOpportunitiesImplement)
    {
        return R.ok(quoteOpportunitiesImplementService.updateQuoteOpportunitiesImplement(quoteOpportunitiesImplement));
    }

    /**
     * 删除报价系统-商机实施小记
     */
    @Operation(summary = "删除报价系统-商机实施小记", description  = "删除报价系统-商机实施小记")
    @PreAuthorize("@ss.hasPermi('quote:implement:remove')")
    @Log(title = "报价系统-商机实施小记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesImplementService.deleteQuoteOpportunitiesImplementByIds(ids));
    }
}
