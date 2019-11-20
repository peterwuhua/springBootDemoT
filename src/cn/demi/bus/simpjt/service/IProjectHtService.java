package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.vo.ProjectVo;

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
	void update2Ht(Project p) throws GlobalException;
	
	void updateHtSt(String code) throws GlobalException;

	/**
	 * 编辑合同签订页面
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public ProjectVo find(String id) throws GlobalException;
	
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
	
	/**
	 * 
	 * @Title: createHtCode   
	 * @Description: 生成合同编号  
	 * @param: @param vo
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: String      
	 * @throws
	 */
	public String createHtCode(ProjectVo vo) throws GlobalException;
	
}
