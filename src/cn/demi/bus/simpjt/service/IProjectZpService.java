package cn.demi.bus.simpjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectZpService extends IBaseService<ProjectVo> {
	/**
	 * 已办记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException;
	
	public List<AuVo> listAu(String sampType) throws GlobalException; 
}
