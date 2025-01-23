package com.internal.system.service.impl;

import java.util.LinkedList;
import java.util.List;
import com.internal.common.utils.DateUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.internal.system.mapper.SysFilesMapper;
import com.internal.system.domain.SysFiles;
import com.internal.system.service.ISysFilesService;

/**
 * 项目文件Service业务层处理
 * 
 * @author internal
 * @date 2025-01-20
 */
@Service
@AllArgsConstructor
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFiles> implements ISysFilesService
{

    private final SysFilesMapper sysFilesMapper;

    /**
     * 查询项目文件
     * 
     * @param id 项目文件主键
     * @return 项目文件
     */
    @Override
    public SysFiles selectSysFilesById(Long id)
    {
        return sysFilesMapper.selectById(id);
    }

    /**
     * 查询项目文件列表
     * 
     * @param sysFiles 项目文件
     * @return 项目文件
     */
    @Override
    public List<SysFiles> selectSysFilesList(SysFiles sysFiles)
    {
        return sysFilesMapper.selectSysFilesList(sysFiles);
    }

    /**
     * 新增项目文件
     * 
     * @param sysFiles 项目文件
     * @return 结果
     */
    @Override
    public int insertSysFiles(SysFiles sysFiles)
    {
        sysFiles.setCreateTime(DateUtils.getNowDate());
        return sysFilesMapper.insert(sysFiles);
    }

    /**
     * 修改项目文件
     * 
     * @param sysFiles 项目文件
     * @return 结果
     */
    @Override
    public int updateSysFiles(SysFiles sysFiles)
    {
        sysFiles.setUpdateTime(DateUtils.getNowDate());
        return sysFilesMapper.updateById(sysFiles);
    }

    /**
     * 批量删除项目文件
     * 
     * @param ids 需要删除的项目文件主键
     * @return 结果
     */
    @Override
    public int deleteSysFilesByIds(Long[] ids)
    {
        return sysFilesMapper.deleteSysFilesByIds(ids);
    }

    /**
     * 删除项目文件信息
     * 
     * @param id 项目文件主键
     * @return 结果
     */
    @Override
    public int deleteSysFilesById(Long id)
    {
        return sysFilesMapper.deleteSysFilesById(id);
    }

    @Override
    public List<Long> insertAll(List<SysFiles> filesList) {
        List<Long> idList = new LinkedList<>();
        filesList.forEach(x ->{
            sysFilesMapper.insert(x);
            idList.add(x.getId());
        });
        return idList;
    }
}
