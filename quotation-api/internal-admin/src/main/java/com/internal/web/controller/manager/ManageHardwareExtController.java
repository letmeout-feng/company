package com.internal.web.controller.manager;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.AjaxResult;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.domain.ManageHardwareExt;
import com.internal.manager.service.IManageHardwareExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理系统-硬件外采Controller
 * 
 * @author internal
 * @date 2024-12-19
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-硬件外采表", description  = "管理系统-硬件外采表")
@RequestMapping("/manager/ext")
public class ManageHardwareExtController extends BaseController
{

    private final IManageHardwareExtService manageHardwareExtService;

    /**
     * 查询管理系统-硬件外采列表
     */
    @Operation(summary = "查询管理系统-硬件外采列表", description  = "查询管理系统-硬件外采列表")
    @PreAuthorize("@ss.hasPermi('quote:ext:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManageHardwareExt>> list(@RequestBody PageParams<ManageHardwareExt> params)
    {
        startPage(params);
        List<ManageHardwareExt> list = manageHardwareExtService.selectManageHardwareExtList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出管理系统-硬件外采列表
     */
    @Operation(summary = "导出管理系统-硬件外采列表", description  = "导出管理系统-硬件外采列表")
    @PreAuthorize("@ss.hasPermi('quote:ext:export')")
    @Log(title = "管理系统-硬件外采", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManageHardwareExt manageHardwareExt)
    {
        List<ManageHardwareExt> list = manageHardwareExtService.selectManageHardwareExtList(manageHardwareExt);
        ExcelUtil<ManageHardwareExt> util = new ExcelUtil<ManageHardwareExt>(ManageHardwareExt.class);
        util.exportExcel(response, list, "管理系统-硬件外采数据");
    }

    /**
     * 获取管理系统-硬件外采详细信息
     */
    @Operation(summary = "获取管理系统-硬件外采详细信息", description  = "获取管理系统-硬件外采详细信息")
    @PreAuthorize("@ss.hasPermi('quote:ext:query')")
    @GetMapping(value = "/{id}")
    public R<ManageHardwareExt> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(manageHardwareExtService.selectManageHardwareExtById(id));
    }

    /**
     * 新增管理系统-硬件外采
     */
    @Operation(summary = "新增管理系统-硬件外采", description  = "新增管理系统-硬件外采")
    @PreAuthorize("@ss.hasPermi('quote:ext:add')")
    @Log(title = "管理系统-硬件外采", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody ManageHardwareExt manageHardwareExt)
    {
        return R.ok(manageHardwareExtService.insertManageHardwareExt(manageHardwareExt));
    }

    /**
     * 修改管理系统-硬件外采
     */
    @Operation(summary = "修改管理系统-硬件外采", description  = "修改管理系统-硬件外采")
    @PreAuthorize("@ss.hasPermi('quote:ext:edit')")
    @Log(title = "管理系统-硬件外采", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody ManageHardwareExt manageHardwareExt)
    {
        return R.ok(manageHardwareExtService.updateManageHardwareExt(manageHardwareExt));
    }

    /**
     * 删除管理系统-硬件外采
     */
    @Operation(summary = "删除管理系统-硬件外采", description  = "删除管理系统-硬件外采")
    @PreAuthorize("@ss.hasPermi('quote:ext:remove')")
    @Log(title = "管理系统-硬件外采", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(manageHardwareExtService.deleteManageHardwareExtByIds(ids));
    }

    /**
     * 上架
     */
    @Operation(summary = "上架", description  = "上架(需要id")
    @PreAuthorize("@ss.hasPermi('manager:ext:publish')")
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody ManageHardwareExt ext)
    {
        return toAjax(manageHardwareExtService.publish(ext));
    }
    /**
     * 下架
     */
    @Operation(summary = "下架", description  = "下架(需要id)")
    @PreAuthorize("@ss.hasPermi('manager:ext:unPublish')")
    @PostMapping("/unPublish")
    public AjaxResult unPublish(@RequestBody ManageHardwareExt ext)
    {
        return toAjax(manageHardwareExtService.unPublish(ext));
    }
}
