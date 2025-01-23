package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesCustomizable;
import com.internal.quote.dto.QuoteOpportunitiesDetailQuery;
import com.internal.quote.service.IQuoteOpportunitiesCustomizableService;
import com.internal.quote.vo.QuoteOpportunitiesCustomizableVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 详细报价表-商机定制开发小记Controller
 * 
 * @author internal
 * @date 2024-10-15
 */
@RestController
@AllArgsConstructor
@Tag(name = "详细报价表-商机定制开发小记表", description  = "详细报价表-商机定制开发小记表")
@RequestMapping("/quote/customizable")
public class QuoteOpportunitiesCustomizableController extends BaseController
{

    private final IQuoteOpportunitiesCustomizableService quoteOpportunitiesCustomizableService;

    /**
     * 查询报价系统-商机定制开发小记列表
     */
    @Operation(summary = "查询报价系统-商机定制开发小记列表", description  = "查询报价系统-商机定制开发小记列表")
    @PreAuthorize("@ss.hasPermi('quote:customizable:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesCustomizable>> list(@RequestBody PageParams<QuoteOpportunitiesCustomizable> params)
    {
        startPage(params);
        List<QuoteOpportunitiesCustomizable> list = quoteOpportunitiesCustomizableService.selectQuoteOpportunitiesCustomizableList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机定制开发小记列表
     */
    @Operation(summary = "导出报价系统-商机定制开发小记列表", description  = "导出报价系统-商机定制开发小记列表")
    @PreAuthorize("@ss.hasPermi('quote:customizable:export')")
    @Log(title = "报价系统-商机定制开发小记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesCustomizable quoteOpportunitiesCustomizable)
    {
        List<QuoteOpportunitiesCustomizable> list = quoteOpportunitiesCustomizableService.selectQuoteOpportunitiesCustomizableList(quoteOpportunitiesCustomizable);
        ExcelUtil<QuoteOpportunitiesCustomizable> util = new ExcelUtil<QuoteOpportunitiesCustomizable>(QuoteOpportunitiesCustomizable.class);
        util.exportExcel(response, list, "报价系统-商机定制开发小记数据");
    }

    /**
     * 获取报价系统-商机定制开发小记详细信息
     */
    @Operation(summary = "获取报价系统-商机定制开发小记详细信息", description  = "获取报价系统-商机定制开发小记详细信息")
    @PreAuthorize("@ss.hasPermi('quote:customizable:query')")
    @PostMapping(value = "/getCustomizableInfo")
    public R<QuoteOpportunitiesCustomizableVO> getCustomizableInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(quoteOpportunitiesCustomizableService.getCustomizableInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 新增报价系统-商机定制开发小记
     */
    @Operation(summary = "新增报价系统-商机定制开发小记", description  = "新增报价系统-商机定制开发小记")
    @PreAuthorize("@ss.hasPermi('quote:customizable:add')")
    @Log(title = "报价系统-商机定制开发小记", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesCustomizable quoteOpportunitiesCustomizable)
    {
        return R.ok(quoteOpportunitiesCustomizableService.insertQuoteOpportunitiesCustomizable(quoteOpportunitiesCustomizable));
    }

    /**
     * 修改报价系统-商机定制开发小记
     */
    @Operation(summary = "修改报价系统-商机定制开发小记", description  = "修改报价系统-商机定制开发小记")
    @PreAuthorize("@ss.hasPermi('quote:customizable:edit')")
    @Log(title = "报价系统-商机定制开发小记", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesCustomizable quoteOpportunitiesCustomizable)
    {
        return R.ok(quoteOpportunitiesCustomizableService.updateQuoteOpportunitiesCustomizable(quoteOpportunitiesCustomizable));
    }

    /**
     * 删除报价系统-商机定制开发小记
     */
    @Operation(summary = "删除报价系统-商机定制开发小记", description  = "删除报价系统-商机定制开发小记")
    @PreAuthorize("@ss.hasPermi('quote:customizable:remove')")
    @Log(title = "报价系统-商机定制开发小记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesCustomizableService.deleteQuoteOpportunitiesCustomizableByIds(ids));
    }
}
