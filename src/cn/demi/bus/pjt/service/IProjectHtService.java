package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.ProjectVo;

@Transactional
public interface IProjectHtService extends IBaseService<ProjectVo> {
	/**
	 * 已办记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException;
	/**
	 * 生成合同
	 * @param v
	 * @throws GlobalException
	 */
	void update2Ht(ProjectVo v) throws GlobalException;
	
	void updateHtSt(String code) throws GlobalException;

	/**
	 * 编辑合同签订页面
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public ProjectVo find(String id) throws GlobalException;
	/**
	 * 生成合同组装 vo对象
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public ProjectVo find4Ht(String id) throws GlobalException;
	
	/**
	 * 
	 * @Title: findRatioByInvoiceId   
	 * @Description: 预付款比例 
	 * @param: @param id
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: double      
	 * @throws
	 */
	public double findRatioByInvoiceId(String id) throws GlobalException;
	
}
