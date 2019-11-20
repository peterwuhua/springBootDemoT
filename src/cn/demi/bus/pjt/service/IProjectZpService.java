package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.pjt.vo.ProjectVo;

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
	
	/**
	 * 获取人员工作量
	 * @param roleCode 角色编码
	 * @param status 当前状态
	 * @return
	 * @throws GlobalException
	 */
	List<AuVo> listAu(String sampType) throws GlobalException;
}
