package com.internal.web.controller.manager;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.AjaxResult;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.domain.ManageHardwareSelf;
import com.internal.manager.service.IManageHardwareSelfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理系统-硬件自研Controller
 * 
 * @author internal
 * @date 2024-12-19
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-硬件自研表", description  = "管理系统-硬件自研表")
@RequestMapping("/manager/self")
public class ManageHardwareSelfController extends BaseController
{

    private final IManageHardwareSelfService manageHardwareSelfService;

    /**
     * 查询管理系统-硬件自研列表
     */
    @Operation(summary = "查询管理系统-硬件自研列表", description  = "查询管理系统-硬件自研列表")
    @PreAuthorize("@ss.hasPermi('quote:self:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManageHardwareSelf>> list(@RequestBody PageParams<ManageHardwareSelf> params)
    {
        startPage(params);
        List<ManageHardwareSelf> list = manageHardwareSelfService.selectManageHardwareSelfList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出管理系统-硬件自研列表
     */
    @Operation(summary = "导出管理系统-硬件自研列表", description  = "导出管理系统-硬件自研列表")
    @PreAuthorize("@ss.hasPermi('quote:self:export')")
    @Log(title = "管理系统-硬件自研", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManageHardwareSelf manageHardwareSelf)
    {
        List<ManageHardwareSelf> list = manageHardwareSelfService.selectManageHardwareSelfList(manageHardwareSelf);
        ExcelUtil<ManageHardwareSelf> util = new ExcelUtil<ManageHardwareSelf>(ManageHardwareSelf.class);
        util.exportExcel(response, list, "管理系统-硬件自研数据");
    }

    /**
     * 获取管理系统-硬件自研详细信息
     */
    @Operation(summary = "获取管理系统-硬件自研详细信息", description  = "获取管理系统-硬件自研详细信息")
    @PreAuthorize("@ss.hasPermi('quote:self:query')")
    @GetMapping(value = "/{id}")
    public R<ManageHardwareSelf> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(manageHardwareSelfService.selectManageHardwareSelfById(id));
    }

    /**
     * 新增管理系统-硬件自研
     */
    @Operation(summary = "新增管理系统-硬件自研", description  = "新增管理系统-硬件自研")
    @PreAuthorize("@ss.hasPermi('quote:self:add')")
    @Log(title = "管理系统-硬件自研", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody ManageHardwareSelf manageHardwareSelf)
    {
        return R.ok(manageHardwareSelfService.insertManageHardwareSelf(manageHardwareSelf));
    }

    /**
     * 修改管理系统-硬件自研
     */
    @Operation(summary = "修改管理系统-硬件自研", description  = "修改管理系统-硬件自研")
    @PreAuthorize("@ss.hasPermi('quote:self:edit')")
    @Log(title = "管理系统-硬件自研", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody ManageHardwareSelf manageHardwareSelf)
    {
        return R.ok(manageHardwareSelfService.updateManageHardwareSelf(manageHardwareSelf));
    }

    /**
     * 删除管理系统-硬件自研
     */
    @Operation(summary = "删除管理系统-硬件自研", description  = "删除管理系统-硬件自研")
    @PreAuthorize("@ss.hasPermi('quote:self:remove')")
    @Log(title = "管理系统-硬件自研", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(manageHardwareSelfService.deleteManageHardwareSelfByIds(ids));
    }

    /**
     * 上架
     */
    @Operation(summary = "上架", description  = "上架(需要id")
    @PreAuthorize("@ss.hasPermi('manager:self:publish')")
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody ManageHardwareSelf self)
    {
        return toAjax(manageHardwareSelfService.publish(self));
    }
    /**
     * 下架
     */
    @Operation(summary = "下架", description  = "下架(需要id)")
    @PreAuthorize("@ss.hasPermi('manager:self:unPublish')")
    @PostMapping("/unPublish")
    public AjaxResult unPublish(@RequestBody ManageHardwareSelf self)
    {
        return toAjax(manageHardwareSelfService.unPublish(self));
    }
}
