package com.internal.web.controller.manager;

import cn.hutool.core.bean.BeanUtil;
import com.internal.common.annotation.Log;
import com.internal.common.config.InternalConfig;
import com.internal.common.core.controller.BaseController;
import com.internal.common.core.domain.R;
import com.internal.common.core.page.TableDataInfo;
import com.internal.common.enums.BusinessType;
import com.internal.common.request.PageParams;
import com.internal.common.utils.file.FileUploadUtils;
import com.internal.common.utils.file.FileUtils;
import com.internal.common.utils.poi.ExcelUtil;
import com.internal.framework.config.ServerConfig;
import com.internal.manager.domain.ManageSoft;
import com.internal.manager.dto.ManageSoftOptionsDTO;
import com.internal.manager.dto.ManageSoftQueryDTO;
import com.internal.manager.dto.ManageSoftSaveDTO;
import com.internal.manager.dto.ManageSoftUpdateDTO;
import com.internal.manager.service.IManageSoftService;
import com.internal.manager.vo.ManageSoftVO;
import com.internal.system.domain.SysFiles;
import com.internal.system.service.ISysFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理系统-软著管理Controller
 * 
 * @author internal
 * @date 2024-12-11
 */
@RestController
@AllArgsConstructor
@Tag(name = "管理系统-软著管理表", description  = "管理系统-软著管理表")
@RequestMapping("/manager/soft")
public class ManageSoftController extends BaseController
{

    private final IManageSoftService manageSoftService;
    private final ISysFilesService sysFilesService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询管理系统-软著管理列表
     */
    @Operation(summary = "查询管理系统-软著管理列表", description  = "查询管理系统-软著管理列表")
    @PreAuthorize("@ss.hasPermi('manager:soft:list')")
    @PostMapping("/page")
    public TableDataInfo<List<ManageSoftVO>> list(@RequestBody PageParams<ManageSoftQueryDTO> params)
    {
        startPage(params);
        List<ManageSoftVO> list = manageSoftService.selectManageSoftList(params.getModel());
        return getDataTable(list);
    }

    /**
     * 查询未对应的软著列表
     */
    @Operation(summary = "查询未对应的软著列表", description  = "查询未对应的软著列表-需要category和from")
    @PreAuthorize("@ss.hasPermi('manager:soft:getOptions')")
    @PostMapping("/getOptions")
    public List<ManageSoftVO> getOptions(@RequestBody ManageSoftOptionsDTO dto)
    {
        List<ManageSoftVO> list = manageSoftService.getOptions(dto);
        return list;
    }

    /**
     * 导出管理系统-软著管理列表
     */
    @Operation(summary = "导出管理系统-软著管理列表", description  = "导出管理系统-软著管理列表")
    @PreAuthorize("@ss.hasPermi('manager:soft:export')")
    @Log(title = "管理系统-软著管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManageSoftQueryDTO manageSoft)
    {
        List<ManageSoftVO> list = manageSoftService.selectManageSoftList(manageSoft);
        ExcelUtil<ManageSoftVO> util = new ExcelUtil<ManageSoftVO>(ManageSoftVO.class);
        util.exportExcel(response, list, "管理系统-软著管理数据");
    }

    /**
     * 获取管理系统-软著管理详细信息
     */
    @Operation(summary = "获取管理系统-软著管理详细信息", description  = "获取管理系统-软著管理详细信息")
    @PreAuthorize("@ss.hasPermi('manager:soft:query')")
    @GetMapping(value = "/{id}")
    public R<ManageSoft> getInfo(@PathVariable("id") Long id)
    {
        return  R.ok(manageSoftService.selectManageSoftById(id));
    }

    /**
     * 新增管理系统-软著管理
     */
    @Operation(summary = "新增管理系统-软著管理", description  = "新增管理系统-软著管理")
    @PreAuthorize("@ss.hasPermi('manager:soft:add')")
    @Log(title = "管理系统-软著管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Boolean> add(@RequestBody ManageSoftSaveDTO manageSoft)
    {
        manageSoft.getFileList().forEach(f ->{
            f.setId(null);
        });
        return R.ok(manageSoftService.insertManageSoft(manageSoft));
    }

    /**
     * 修改管理系统-软著管理
     */
    @Operation(summary = "修改管理系统-软著管理", description  = "修改管理系统-软著管理")
    @PreAuthorize("@ss.hasPermi('manager:soft:edit')")
    @Log(title = "管理系统-软著管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Boolean> edit(@RequestBody ManageSoftUpdateDTO manageSoft)
    {
        manageSoft.getFileList().forEach(f ->{
            f.setId(null);
        });
        return R.ok(manageSoftService.updateManageSoft(manageSoft));
    }

    /**
     * 删除管理系统-软著管理
     */
    @Operation(summary = "删除管理系统-软著管理", description  = "删除管理系统-软著管理")
    @PreAuthorize("@ss.hasPermi('manager:soft:remove')")
    @Log(title = "管理系统-软著管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public R<Integer> remove(@PathVariable("ids") Long[] ids)
    {
        return R.ok(manageSoftService.deleteManageSoftByIds(ids));
    }

    /*private  List<Long> upLoadFiles(List<MultipartFile> fileList){
        List<SysFiles> filesList = new LinkedList<>();
        // 上传文件路径
        String filePath = InternalConfig.getUploadPath();

        for (MultipartFile file : fileList)
        {
            try {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;

                SysFiles newFile = new SysFiles();
                newFile.setUrl(url);
                newFile.setPath(fileName);
                newFile.setFileType(FileUtils.getFileExtension(fileName));
                newFile.setSuffix(FileUtils.getFileExtension(fileName));
                newFile.setUniqueFileName(FileUtils.getName(fileName));
                newFile.setOriginalFileName(file.getOriginalFilename());
                filesList.add(newFile);
            }catch (Exception ex){
                throw new RuntimeException("上传失败");
            }
        }

        List<Long> fileIdList = sysFilesService.insertAll(filesList);
        return fileIdList;
    }*/
}
