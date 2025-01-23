package com.internal.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internal.system.domain.SysFiles;

/**
 * 项目文件Service接口
 * 
 * @author internal
 * @date 2025-01-20
 */
public interface ISysFilesService extends IService<SysFiles>
{
    /**
     * 查询项目文件
     * 
     * @param id 项目文件主键
     * @return 项目文件
     */
    public SysFiles selectSysFilesById(Long id);

    /**
     * 查询项目文件列表
     * 
     * @param sysFiles 项目文件
     * @return 项目文件集合
     */
    public List<SysFiles> selectSysFilesList(SysFiles sysFiles);

    /**
     * 新增项目文件
     * 
     * @param sysFiles 项目文件
     * @return 结果
     */
    public int insertSysFiles(SysFiles sysFiles);

    /**
     * 修改项目文件
     * 
     * @param sysFiles 项目文件
     * @return 结果
     */
    public int updateSysFiles(SysFiles sysFiles);

    /**
     * 批量删除项目文件
     * 
     * @param ids 需要删除的项目文件主键集合
     * @return 结果
     */
    public int deleteSysFilesByIds(Long[] ids);

    /**
     * 删除项目文件信息
     * 
     * @param id 项目文件主键
     * @return 结果
     */
    public int deleteSysFilesById(Long id);

    public List<Long> insertAll(List<SysFiles> filesList);
}
