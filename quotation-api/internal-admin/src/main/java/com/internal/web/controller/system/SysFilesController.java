package com.internal.web.controller.system;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.internal.common.config.InternalConfig;
import com.internal.common.core.domain.R;
import com.internal.common.utils.file.FileUploadUtils;
import com.internal.common.utils.file.FileUtils;
import com.internal.common.utils.file.ZipUtils;
import com.internal.framework.config.ServerConfig;
import com.internal.quote.domain.QuoteOpportunities;
import com.internal.quote.domain.QuoteOpportunitiesFile;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.internal.common.request.PageParams;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.AjaxResult;
import com.internal.common.enums.BusinessType;
import com.internal.system.domain.SysFiles;
import com.internal.system.service.ISysFilesService;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目文件Controller
 * 
 * @author internal
 * @date 2025-01-20
 */
@RestController
@AllArgsConstructor
@Tag(name = "SysFilesController", description  = "项目文件表")
@RequestMapping("/quote/files")
public class SysFilesController extends BaseController
{

    private final ISysFilesService sysFilesService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询项目文件列表
     */
    @Operation(summary = "查询项目文件列表", description  = "查询项目文件列表")
    @PreAuthorize("@ss.hasPermi('quote:files:list')")
    @PostMapping("/page")
    public TableDataInfo<List<SysFiles>> list(@RequestBody PageParams<SysFiles> params)
    {
        startPage(params);
        List<SysFiles> list = sysFilesService.selectSysFilesList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 导出项目文件列表
     */
    @Operation(summary = "导出项目文件列表", description  = "导出项目文件列表")
    @PreAuthorize("@ss.hasPermi('quote:files:export')")
    @Log(title = "项目文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFiles sysFiles)
    {
        List<SysFiles> list = sysFilesService.selectSysFilesList(sysFiles);
        ExcelUtil<SysFiles> util = new ExcelUtil<SysFiles>(SysFiles.class);
        util.exportExcel(response, list, "项目文件数据");
    }

    /**
     * 获取项目文件详细信息
     */
    @Operation(summary = "获取项目文件详细信息", description  = "获取项目文件详细信息")
    @PreAuthorize("@ss.hasPermi('quote:files:query')")
    @GetMapping(value = "/{id}")
    public R<SysFiles> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(sysFilesService.selectSysFilesById(id));
    }

    /**
     * 新增项目文件
     */
    @Operation(summary = "新增项目文件", description  = "新增项目文件")
    @PreAuthorize("@ss.hasPermi('quote:files:add')")
    @Log(title = "项目文件", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody SysFiles sysFiles)
    {
        return R.ok(sysFilesService.insertSysFiles(sysFiles));
    }

    /**
     * 修改项目文件
     */
    @Operation(summary = "修改项目文件", description  = "修改项目文件")
    @PreAuthorize("@ss.hasPermi('quote:files:edit')")
    @Log(title = "项目文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody SysFiles sysFiles)
    {
        return R.ok(sysFilesService.updateSysFiles(sysFiles));
    }

    /**
     * 删除项目文件
     */
    @Operation(summary = "删除项目文件", description  = "删除项目文件")
    @PreAuthorize("@ss.hasPermi('quote:files:remove')")
    @Log(title = "项目文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(sysFilesService.deleteSysFilesByIds(ids));
    }

    /**
     * 下载附件
     */
    @Operation(summary = "下载附件", description  = "下载附件(单个)")
    @Log(title = "下载附件", businessType = BusinessType.OTHER)
    @GetMapping("/downLoadFile")
    public void downLoadFile(@RequestParam("fileId") Long fileId, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        SysFiles file = sysFilesService.selectSysFilesById(fileId);
        if(ObjectUtil.isNotEmpty(file)){
            // 上传文件路径
            String filePath = file.getUrl();

            byte[] data = Files.readAllBytes(Path.of(filePath));

            response.reset();
            response.addHeader("access-control-allow-credentials","true");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
        }
    }
}
