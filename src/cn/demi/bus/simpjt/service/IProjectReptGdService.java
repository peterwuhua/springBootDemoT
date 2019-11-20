package cn.demi.bus.simpjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectReptGdService extends IBaseService<ProjectVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ProjectVo v) throws GlobalException;
	

	
	/**
	 * 
	 * @Title: findById   
	 * @Description: 
	 * @param: @param id
	 * @param: @param files
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ProjectVo      
	 * @throws
	 */
	public ProjectVo findById(String id,List<FilesVo> files) throws GlobalException;
	
	

	
}
