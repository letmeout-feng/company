package com.internal.system.mapper;

import java.util.List;
import com.internal.system.domain.SysFiles;
import com.internal.system.domain.SysFiles;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 项目文件Mapper接口
 * 
 * @author internal
 * @date 2025-01-20
 */
@Repository
public interface SysFilesMapper extends BaseMapper<SysFiles>
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
     * 删除项目文件
     * 
     * @param id 项目文件主键
     * @return 结果
     */
    public int deleteSysFilesById(Long id);

    /**
     * 批量删除项目文件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFilesByIds(Long[] ids);
}
