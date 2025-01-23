package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.quote.domain.QuoteOpportunitiesSelf;
import com.internal.quote.dto.QuoteOpportunitiesDetailQuery;
import com.internal.quote.service.IQuoteOpportunitiesSelfService;
import com.internal.quote.vo.QuoteOpportunitiesSelfVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 详细报价表-商机自研硬件小记Controller
 * 
 * @author internal
 * @date 2024-10-15
 */
@RestController
@AllArgsConstructor
@Tag(name = "详细报价表-商机自研硬件小记表", description  = "详细报价表-商机自研硬件小记表")
@RequestMapping("/quote/self")
public class QuoteOpportunitiesSelfController extends BaseController
{

    private final IQuoteOpportunitiesSelfService quoteOpportunitiesSelfService;

    /**
     * 查询报价系统-商机自研硬件小记列表
     */
    @Operation(summary = "查询报价系统-商机自研硬件小记列表", description  = "查询报价系统-商机自研硬件小记列表")
    @PreAuthorize("@ss.hasPermi('quote:self:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteOpportunitiesSelf>> list(@RequestBody PageParams<QuoteOpportunitiesSelf> params)
    {
        startPage(params);
        List<QuoteOpportunitiesSelf> list = quoteOpportunitiesSelfService.selectQuoteOpportunitiesSelfList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-商机自研硬件小记列表
     */
    @Operation(summary = "导出报价系统-商机自研硬件小记列表", description  = "导出报价系统-商机自研硬件小记列表")
    @PreAuthorize("@ss.hasPermi('quote:self:export')")
    @Log(title = "报价系统-商机自研硬件小记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteOpportunitiesSelf quoteOpportunitiesSelf)
    {
        List<QuoteOpportunitiesSelf> list = quoteOpportunitiesSelfService.selectQuoteOpportunitiesSelfList(quoteOpportunitiesSelf);
        ExcelUtil<QuoteOpportunitiesSelf> util = new ExcelUtil<QuoteOpportunitiesSelf>(QuoteOpportunitiesSelf.class);
        util.exportExcel(response, list, "报价系统-商机自研硬件小记数据");
    }

    /**
     * 获取报价系统-商机自研硬件小记详细信息
     */
    @Operation(summary = "获取报价系统-商机自研硬件小记详细信息", description  = "获取报价系统-商机自研硬件小记详细信息")
    @PreAuthorize("@ss.hasPermi('quote:self:query')")
    @PostMapping(value = "/getSelfInfo")
    public R<QuoteOpportunitiesSelfVO> getSelfInfo(@RequestBody QuoteOpportunitiesDetailQuery quoteOpportunitiesDetailQuery)
    {
        return  R.ok(quoteOpportunitiesSelfService.getSelfInfo(quoteOpportunitiesDetailQuery));
    }

    /**
     * 新增报价系统-商机自研硬件小记
     */
    @Operation(summary = "新增报价系统-商机自研硬件小记", description  = "新增报价系统-商机自研硬件小记")
    @PreAuthorize("@ss.hasPermi('quote:self:add')")
    @Log(title = "报价系统-商机自研硬件小记", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteOpportunitiesSelf quoteOpportunitiesSelf)
    {
        return R.ok(quoteOpportunitiesSelfService.insertQuoteOpportunitiesSelf(quoteOpportunitiesSelf));
    }

    /**
     * 修改报价系统-商机自研硬件小记
     */
    @Operation(summary = "修改报价系统-商机自研硬件小记", description  = "修改报价系统-商机自研硬件小记")
    @PreAuthorize("@ss.hasPermi('quote:self:edit')")
    @Log(title = "报价系统-商机自研硬件小记", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteOpportunitiesSelf quoteOpportunitiesSelf)
    {
        return R.ok(quoteOpportunitiesSelfService.updateQuoteOpportunitiesSelf(quoteOpportunitiesSelf));
    }

    /**
     * 删除报价系统-商机自研硬件小记
     */
    @Operation(summary = "删除报价系统-商机自研硬件小记", description  = "删除报价系统-商机自研硬件小记")
    @PreAuthorize("@ss.hasPermi('quote:self:remove')")
    @Log(title = "报价系统-商机自研硬件小记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(quoteOpportunitiesSelfService.deleteQuoteOpportunitiesSelfByIds(ids));
    }
}
