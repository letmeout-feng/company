package com.internal.web.controller.manager;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.domain.ManagerBuGoal;
import com.internal.manager.service.IManagerBuGoalService;
import com.internal.quartz.task.CrmGoalInfoTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.List;


/**
 * 管理系统-目标管理Controller
 *
 * @author internal
 * @date 2024-12-30
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-目标管理表", description  = "管理系统-目标管理表")
@RequestMapping("/manager/goal")
public class ManagerBuGoalController extends BaseController {

    private final IManagerBuGoalService managerBuGoalService;
    private final CrmGoalInfoTask crmGoalInfoTask;

    /**
     * 查询管理系统-目标管理列表
     */
    @Operation(summary = "查询管理系统-目标管理列表", description  = "查询管理系统-目标管理列表")
    @PreAuthorize("@ss.hasPermi('manager:goal:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManagerBuGoal>> list(@RequestBody PageParams<ManagerBuGoal> params) {
        startPage(params);
        List<ManagerBuGoal> list = managerBuGoalService.selectManagerBuGoalList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出管理系统-目标管理列表
     */
    @Operation(summary = "导出管理系统-目标管理列表", description  = "导出管理系统-目标管理列表")
    @PreAuthorize("@ss.hasPermi('manager:goal:export')")
    @Log(title = "管理系统-目标管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManagerBuGoal managerBuGoal) {
        List<ManagerBuGoal> list = managerBuGoalService.selectManagerBuGoalList(managerBuGoal);
        ExcelUtil<ManagerBuGoal> util = new ExcelUtil<ManagerBuGoal>(ManagerBuGoal.class);
        util.exportExcel(response, list, "管理系统-目标管理数据");
    }

    /**
     * 获取管理系统-目标管理详细信息
     */
    @Operation(summary = "获取管理系统-目标管理详细信息", description  = "获取管理系统-目标管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:goal:query')")
    @GetMapping(value = "/{id}")
    public R<ManagerBuGoal> getInfo(@PathVariable("id") Long id) {
        return R.ok(managerBuGoalService.selectManagerBuGoalById(id));
    }

    /**
     * 新增管理系统-目标管理
     */
    @Operation(summary = "新增管理系统-目标管理", description  = "新增管理系统-目标管理")
    @PreAuthorize("@ss.hasPermi('manager:goal:add')")
    @Log(title = "管理系统-目标管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody ManagerBuGoal managerBuGoal) {
        return R.ok(managerBuGoalService.insertManagerBuGoal(managerBuGoal));
    }

    /**
     * 修改管理系统-目标管理
     */
    @Operation(summary = "修改管理系统-目标管理", description  = "修改管理系统-目标管理")
    @PreAuthorize("@ss.hasPermi('manager:goal:edit')")
    @Log(title = "管理系统-目标管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody ManagerBuGoal managerBuGoal) {
        return R.ok(managerBuGoalService.updateManagerBuGoal(managerBuGoal));
    }

    /**
     * 删除管理系统-目标管理
     */
    @Operation(summary = "删除管理系统-目标管理", description  = "删除管理系统-目标管理")
    @PreAuthorize("@ss.hasPermi('manager:goal:remove')")
    @Log(title = "管理系统-目标管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids) {
        return R.ok(managerBuGoalService.deleteManagerBuGoalByIds(ids));
    }


    /**
     * 一键同步
     */
    @Operation(summary = "一键同步", description  = "一键同步")
    @PreAuthorize("@ss.hasPermi('manager:goal:sync')")
    @Log(title = "管理系统-目标管理")
    @PostMapping("/sync")
    public R sync() throws MessagingException, GeneralSecurityException {
        crmGoalInfoTask.sync();
        return R.ok();
    }
}
