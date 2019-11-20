package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.NormalListVo;
/**
 * 
 * @ClassName:  INormalListService   
 * @Description:   
 * @author: 吴华 
 * @date:   2019年1月22日 下午2:46:44       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Transactional
public interface INormalListService extends IBaseService<NormalListVo> {
	
	
	/**
	 * 
	 * @Title: getGjStatusList   
	 * @Description: 跟进状态   
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<GjStatusVo>      
	 * @throws
	 */
	public List<String> getGjStatusList() throws GlobalException;
	
	

	
	/**
	 * 
	 * @Title: getCusCatesList   
	 * @Description: 客户类型
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<NormalVo>      
	 * @throws
	 */
	public List<String> getCusCatesList() throws GlobalException;

	/**
	 * 
	 * @Title: auditStatusList   
	 * @Description: 审批状态 
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<String>      
	 * @throws
	 */
	public List<String> auditStatusList() throws GlobalException;

	public List<String> getFetchPayWay() throws GlobalException;

	

	
}
