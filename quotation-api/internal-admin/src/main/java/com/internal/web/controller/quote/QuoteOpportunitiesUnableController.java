package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesUnable;
import com.internal.quote.service.IQuoteOpportunitiesUnableService;
import com.internal.quote.vo.QuoteOpportunitiesUnableVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报价系统-无法报价Controller
 * 
 * @author internal
 * @date 2024-11-21
 */
@RestController
@AllArgsConstructor
@Tag(name = "报价系统-无法报价表", description  = "报价系统-无法报价表")
@RequestMapping("/quote/unable")
public class QuoteOpportunitiesUnableController extends BaseController
{

    private final IQuoteOpportunitiesUnableService quoteOpportunitiesUnableService;

    /**
     * 查询报价系统-无法报价列表
     */
    @Operation(summary = "查询报价系统-无法报价列表", description  = "查询报价系统-无法报价列表")
    @PreAuthorize("@ss.hasPermi('quote:unable:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesUnable>> list(@RequestBody PageParams<QuoteOpportunitiesUnable> params)
    {
        startPage(params);
        List<QuoteOpportunitiesUnable> list = quoteOpportunitiesUnableService.selectQuoteOpportunitiesUnableList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-无法报价列表
     */
    @Operation(summary = "导出报价系统-无法报价列表", description  = "导出报价系统-无法报价列表")
    @PreAuthorize("@ss.hasPermi('quote:unable:export')")
    @Log(title = "报价系统-无法报价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesUnable quoteOpportunitiesUnable)
    {
        List<QuoteOpportunitiesUnable> list = quoteOpportunitiesUnableService.selectQuoteOpportunitiesUnableList(quoteOpportunitiesUnable);
        ExcelUtil<QuoteOpportunitiesUnable> util = new ExcelUtil<QuoteOpportunitiesUnable>(QuoteOpportunitiesUnable.class);
        util.exportExcel(response, list, "报价系统-无法报价数据");
    }

    /**
     * 获取报价系统-无法报价详细信息
     */
    @Operation(summary = "获取报价系统-无法报价详细信息", description  = "获取报价系统-无法报价详细信息")
    /*@PreAuthorize("@ss.hasPermi('quote:unable:query')")*/
    @GetMapping(value = "/{id}")
    public R<QuoteOpportunitiesUnableVO> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(quoteOpportunitiesUnableService.selectQuoteOpportunitiesUnableById(id));
    }

    /**
     * 新增报价系统-无法报价
     */
    @Operation(summary = "新增报价系统-无法报价", description  = "新增报价系统-无法报价")
    @PreAuthorize("@ss.hasPermi('quote:await:unable:quote','quote:finish:update','quote:cannot:update','quote:await:update:update')")
    @Log(title = "报价系统-无法报价", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesUnable quoteOpportunitiesUnable)
    {
        return R.ok(quoteOpportunitiesUnableService.insertQuoteOpportunitiesUnable(quoteOpportunitiesUnable));
    }

    /**
     * 修改报价系统-无法报价
     */
    @Operation(summary = "修改报价系统-无法报价", description  = "修改报价系统-无法报价")
    @PreAuthorize("@ss.hasPermi('quote:unable:edit')")
    @Log(title = "报价系统-无法报价", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesUnable quoteOpportunitiesUnable)
    {
        return R.ok(quoteOpportunitiesUnableService.updateQuoteOpportunitiesUnable(quoteOpportunitiesUnable));
    }

    /**
     * 删除报价系统-无法报价
     */
    @Operation(summary = "删除报价系统-无法报价", description  = "删除报价系统-无法报价")
    @PreAuthorize("@ss.hasPermi('quote:unable:remove')")
    @Log(title = "报价系统-无法报价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesUnableService.deleteQuoteOpportunitiesUnableByIds(ids));
    }
}
