package com.internal.web.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.AjaxResult;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.domain.ManageQuote;
import com.internal.manager.dto.ManageQuoteSaveDTO;
import com.internal.manager.service.IManageQuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 管理系统-报价设置Controller
 *
 * @author internal
 * @date 2024-10-09
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-报价设置表", description  = "管理系统-报价设置表")
@RequestMapping("/manager/quote")
public class ManageQuoteController extends BaseController
{

    private final IManageQuoteService manageQuoteService;

    /**
     * 查询管理系统-报价设置列表
     */
    @Operation(summary = "查询管理系统-报价设置列表", description  = "查询管理系统-报价设置列表")
    @PreAuthorize("@ss.hasPermi('manager:quote:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManageQuote>> list(@RequestBody PageParams<ManageQuote> params)
    {
        startPage(params);
        List<ManageQuote> list = manageQuoteService.selectManageQuoteList(params.getModel());
        return getDataTable(list);
    }
    /**
     * 报价列表Map(小数格式)
     */
    @Operation(summary = "报价列表Map(小数格式)", description  = "报价列表Map(小数格式)")
    //@PreAuthorize("@ss.hasPermi('manager:quote:map')")
    @GetMapping("/getMap")
    public R<Map<String,ManageQuote>> getManageQuoteMap()
    {
        Map<String,ManageQuote> map = manageQuoteService.getManageQuoteMap();
        return R.ok(map);
    }

    /**
     * 导出管理系统-报价设置列表
     */
    @Operation(summary = "导出管理系统-报价设置列表", description  = "导出管理系统-报价设置列表")
    @PreAuthorize("@ss.hasPermi('manager:quote:export')")
    @Log(title = "管理系统-报价设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManageQuote manageQuote)
    {
        List<ManageQuote> list = manageQuoteService.selectManageQuoteList(manageQuote);
        ExcelUtil<ManageQuote> util = new ExcelUtil<ManageQuote>(ManageQuote.class);
        util.exportExcel(response, list, "管理系统-报价设置数据");
    }

    /**
     * 获取管理系统-报价设置详细信息
     */
    @Operation(summary = "获取管理系统-报价设置详细信息", description  = "获取管理系统-报价设置详细信息")
    @PreAuthorize("@ss.hasPermi('manager:quote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(manageQuoteService.selectManageQuoteById(id));
    }

    /**
     * 新增管理系统-报价设置
     */
    @Operation(summary = "新增管理系统-报价设置", description  = "新增管理系统-报价设置")
    @PreAuthorize("@ss.hasPermi('manager:quote:add')")
    @Log(title = "管理系统-报价设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManageQuote manageQuote)
    {
        manageQuoteService.remove(new QueryWrapper<ManageQuote>().isNotNull("id"));
        return toAjax(manageQuoteService.insertManageQuote(manageQuote));
    }
    /**
     * 批量新增管理系统-报价设置
     */
    @Operation(summary = "批量新增管理系统-报价设置", description  = "批量新增管理系统-报价设置")
    @PreAuthorize("@ss.hasPermi('manager:quote:add')")
    @Log(title = "管理系统-报价设置", businessType = BusinessType.INSERT)
    @PostMapping("batchAdd")
    public AjaxResult batchAdd(@RequestBody ManageQuoteSaveDTO manageQuoteSaveDTO)
    {
        manageQuoteService.remove(new QueryWrapper<ManageQuote>().isNotNull("id"));
        manageQuoteSaveDTO.getManagedQuoteList().forEach(item -> {
            item.setId(null);
            item.setDutyRate(item.getDutyRate().divide(BigDecimal.valueOf(100)));
            item.setProfitability(item.getProfitability().divide(BigDecimal.valueOf(100)));
        });
        return toAjax(manageQuoteService.saveBatch(manageQuoteSaveDTO.getManagedQuoteList(),50));
    }

    /**
     * 修改管理系统-报价设置
     */
    @Operation(summary = "修改管理系统-报价设置", description  = "修改管理系统-报价设置")
    @PreAuthorize("@ss.hasPermi('manager:quote:edit')")
    @Log(title = "管理系统-报价设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManageQuote manageQuote)
    {
        return toAjax(manageQuoteService.updateManageQuote(manageQuote));
    }

    /**
     * 删除管理系统-报价设置
     */
    @Operation(summary = "删除管理系统-报价设置", description  = "删除管理系统-报价设置")
    @PreAuthorize("@ss.hasPermi('manager:quote:remove')")
    @Log(title = "管理系统-报价设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        return toAjax(manageQuoteService.deleteManageQuoteByIds(ids));
    }
}
