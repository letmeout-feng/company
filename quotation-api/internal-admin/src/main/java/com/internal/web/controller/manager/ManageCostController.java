package com.internal.web.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.enums.CostType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.domain.ManageCost;
import com.internal.manager.dto.ManageCostSaveDTO;
import com.internal.manager.service.IManageCostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理系统-成本设置Controller
 *
 * @author internal
 * @date 2024-10-09
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-成本设置表", description  = "管理系统-成本设置表")
@RequestMapping("/manager/cost")
public class ManageCostController extends BaseController
{

    private final IManageCostService manageCostService;

    /**
     * 查询管理系统-成本设置列表
     */
    @Operation(summary = "查询管理系统-成本设置列表", description  = "查询管理系统-成本设置列表")
    @PreAuthorize("@ss.hasPermi('manager:cost:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManageCost>> list(
            @Parameter(schema = @Schema(implementation = ManageCost.class))
            @RequestBody PageParams<ManageCost> params)
    {
        startPage(params);
        List<ManageCost> list = manageCostService.selectManageCostList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 查询管理系统-成本设置map
     */
    @Operation(summary = "查询管理系统-成本设置map", description  = "查询管理系统-成本设置map")
    //@PreAuthorize("@ss.hasPermi('manager:cost:map')")
    @PostMapping("/getCostMap")
    public R<Map<CostType,ManageCost>> getCostMap()
    {
        Map<CostType,ManageCost> map = manageCostService.getCostMap();
        return R.ok(map);
    }

    /**
     * 导出管理系统-成本设置列表
     */
    @Operation(summary = "导出管理系统-成本设置列表", description  = "导出管理系统-成本设置列表")
    @PreAuthorize("@ss.hasPermi('manager:cost:export')")
    @Log(title = "管理系统-成本设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManageCost manageCost)
    {
        List<ManageCost> list = manageCostService.selectManageCostList(manageCost);
        ExcelUtil<ManageCost> util = new ExcelUtil<ManageCost>(ManageCost.class);
        util.exportExcel(response, list, "管理系统-成本设置数据");
    }

    /**
     * 获取管理系统-成本设置详细信息
     */
    @Operation(summary = "获取管理系统-成本设置详细信息", description  = "获取管理系统-成本设置详细信息")
    @PreAuthorize("@ss.hasPermi('manager:cost:query')")
    @GetMapping(value = "/{id}")
    public R<ManageCost> getInfo(@PathVariable("id") Long id)
    {
        return R.ok(manageCostService.selectManageCostById(id));
    }

    /**
     * 新增管理系统-成本设置
     */
    @Operation(summary = "新增管理系统-成本设置", description  = "新增管理系统-成本设置")
    @PreAuthorize("@ss.hasPermi('manager:cost:add')")
    @Log(title = "管理系统-成本设置", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody ManageCost manageCost)
    {
        manageCostService.remove(new QueryWrapper<ManageCost>().isNotNull("id"));
        return R.ok(manageCostService.insertManageCost(manageCost));
    }

    /**
     * 新增管理系统-成本设置
     */
    @Operation(summary = "批量新增管理系统-成本设置", description  = "批量新增管理系统-成本设置")
    @PreAuthorize("@ss.hasPermi('manager:cost:add')")
    @Log(title = "管理系统-成本设置", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public R<Boolean> batchAdd(@RequestBody ManageCostSaveDTO manageCostSaveDTO)
    {
        manageCostService.remove(new QueryWrapper<ManageCost>().isNotNull("id"));
        manageCostSaveDTO.getManageCostList().forEach(item -> item.setId(null));
        return R.ok(manageCostService.saveBatch(manageCostSaveDTO.getManageCostList(),50));
    }

    /**
     * 修改管理系统-成本设置
     */
    @Operation(summary = "修改管理系统-成本设置", description  = "修改管理系统-成本设置")
    @PreAuthorize("@ss.hasPermi('manager:cost:edit')")
    @Log(title = "管理系统-成本设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody ManageCost manageCost)
    {
        return R.ok(manageCostService.updateManageCost(manageCost));
    }

    /**
     * 删除管理系统-成本设置
     */
    @Operation(summary = "删除管理系统-成本设置", description  = "删除管理系统-成本设置")
    @PreAuthorize("@ss.hasPermi('manager:cost:remove')")
    @Log(title = "管理系统-成本设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(manageCostService.deleteManageCostByIds(ids));
    }
}
