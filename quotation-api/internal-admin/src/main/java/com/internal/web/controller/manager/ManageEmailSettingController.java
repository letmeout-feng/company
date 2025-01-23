package com.internal.web.controller.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.manager.dto.ManageEmailCategoryDTO;
import com.internal.quote.domain.QuoteEmailSetting;
import com.internal.quote.service.IQuoteEmailSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 报价系统-工作流邮箱设置Controller
 *
 * @author internal
 * @date 2024-11-25
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-工作流邮箱设置表", description  = "管理系统-工作流邮箱设置表")
@RequestMapping("/manager/setting")
public class ManageEmailSettingController extends BaseController {

    private final IQuoteEmailSettingService quoteEmailSettingService;

    /**
     * 查询报价系统-工作流邮箱设置列表
     */
    @Operation(summary = "查询报价系统-工作流邮箱设置列表", description  = "查询报价系统-工作流邮箱设置列表")
    @PreAuthorize("@ss.hasPermi('manager:setting:list')")
    @PostMapping("/page")
    public TableDataInfo<List<QuoteEmailSetting>> list(@RequestBody PageParams<QuoteEmailSetting> params) {
        startPage(params.getCurrent(),params.getSize());
        List<QuoteEmailSetting> list = quoteEmailSettingService.selectQuoteEmailSettingList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出报价系统-工作流邮箱设置列表
     */
    @Operation(summary = "导出报价系统-工作流邮箱设置列表", description  = "导出报价系统-工作流邮箱设置列表")
    @PreAuthorize("@ss.hasPermi('manager:setting:export')")
    @Log(title = "报价系统-工作流邮箱设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuoteEmailSetting quoteEmailSetting) {
        List<QuoteEmailSetting> list = quoteEmailSettingService.selectQuoteEmailSettingList(quoteEmailSetting);
        ExcelUtil<QuoteEmailSetting> util = new ExcelUtil<QuoteEmailSetting>(QuoteEmailSetting.class);
        util.exportExcel(response, list, "报价系统-工作流邮箱设置数据");
    }

    /**
     * 获取报价系统-工作流邮箱设置详细信息
     */
    @Operation(summary = "获取报价系统-工作流邮箱设置详细信息", description  = "获取报价系统-工作流邮箱设置详细信息")
    @PreAuthorize("@ss.hasPermi('manager:setting:query')")
    @GetMapping(value = "/{id}")
    public R<QuoteEmailSetting> getInfo(@PathVariable("id") Long id) {
        return R.ok(quoteEmailSettingService.selectQuoteEmailSettingById(id));
    }

    /**
     * 新增报价系统-工作流邮箱设置
     */
    @Operation(summary = "新增报价系统-工作流邮箱设置", description  = "新增报价系统-工作流邮箱设置")
    @PreAuthorize("@ss.hasPermi('manager:setting:add')")
    @Log(title = "报价系统-工作流邮箱设置", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody QuoteEmailSetting quoteEmailSetting) {
        return R.ok(quoteEmailSettingService.insertQuoteEmailSetting(quoteEmailSetting));
    }

    /**
     * 批量新价系统-工作流邮箱设置
     */
    @Operation(summary = "批量新价系统-工作流邮箱设置", description  = "批量新价系统-工作流邮箱设置")
    @PreAuthorize("@ss.hasPermi('manager:setting:add')")
    @Log(title = "报价系统-工作流邮箱设置", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public R<Boolean> addBatch(@RequestBody List<QuoteEmailSetting> quoteEmailSetting) {
        quoteEmailSettingService.remove(new QueryWrapper<QuoteEmailSetting>().isNotNull("id"));
        return R.ok(quoteEmailSettingService.saveBatch(quoteEmailSetting));
    }


    /**
     * 修改报价系统-工作流邮箱设置
     */
    @Operation(summary = "修改报价系统-工作流邮箱设置", description  = "修改报价系统-工作流邮箱设置")
    @PreAuthorize("@ss.hasPermi('manager:setting:edit')")
    @Log(title = "报价系统-工作流邮箱设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody QuoteEmailSetting quoteEmailSetting) {
        return R.ok(quoteEmailSettingService.updateQuoteEmailSetting(quoteEmailSetting));
    }

    /**
     * 删除报价系统-工作流邮箱设置
     */
    @Operation(summary = "删除报价系统-工作流邮箱设置", description  = "删除报价系统-工作流邮箱设置")
    @PreAuthorize("@ss.hasPermi('manager:setting:remove')")
    @Log(title = "报价系统-工作流邮箱设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids) {
        return R.ok(quoteEmailSettingService.deleteQuoteEmailSettingByIds(ids));
    }

    /**
     * 获取邮件模版分类
     */
    @Operation(summary = "获取邮件模版分类", description  = "获取邮件模版分类")
    @GetMapping("/getEmailTemplateCategory")
    public R<List<ManageEmailCategoryDTO>> getEmailTemplateCategory() {
        List<ManageEmailCategoryDTO> uniqueCategories = quoteEmailSettingService.list().stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.getCategory()))
                .collect(Collectors.toMap(
                        QuoteEmailSetting::getCategory, // 以 category 作为 key
                        item -> {
                            ManageEmailCategoryDTO manageEmailCategoryDTO = new ManageEmailCategoryDTO();
                            manageEmailCategoryDTO.setId(item.getId());
                            manageEmailCategoryDTO.setLabel(item.getCategory());
                            return manageEmailCategoryDTO;
                        },
                        (existing, replacement) -> existing // 如果遇到重复的 category，保留已存在的
                ))
                .values() // 获取去重后的值
                .stream()
                .toList();
        return R.ok(uniqueCategories);
    }
}
