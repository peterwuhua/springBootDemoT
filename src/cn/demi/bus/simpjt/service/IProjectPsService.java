package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectPsService extends IBaseService<ProjectVo> {
	/**
	 * 已办记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException;
	/**
	 * 终止合同
	 * @param v
	 * @throws GlobalException
	 */
	void update2Stop(ProjectVo v) throws GlobalException;
	
	/**
	 *保存评审记录文件
	 * @param v
	 * @throws GlobalException
	 */
	void update2PsFile(ProjectVo v) throws GlobalException;
	/**
	 * 合同评审文件数据源
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public ProjectVo find4File(String id) throws GlobalException;
}
