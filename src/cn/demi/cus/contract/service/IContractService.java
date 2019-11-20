package cn.demi.cus.contract.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.vo.ContractVo;
/**
 * Create on : 2016年11月15日 下午2:53:44  <br>
 * Description : 合同信息service<br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface IContractService extends IBaseService<ContractVo> {
	
	/**
	 * Create on : Danlee Li 2016年11月16日 下午2:42:56 <br>
	 * Description :根据客户ID获取JSON分页对象  <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo,ContractVo v) throws GlobalException;
	
	
	/**
	 * 
	 * @Title: gridData4YHQ   
	 * @Description: 已回签的合同
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public List<ContractVo> gridData4YHQ(ContractVo v) throws GlobalException;
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:52:55 <br>
	 * Description : 执行中合同 <br>
	 * @param gridVo GridVo
	 * @param v ContractVo
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Execut(GridVo gridVo,ContractVo v) throws GlobalException;
	
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:52:55 <br>
	 * Description : 已完结合同 <br>
	 * @param gridVo GridVo
	 * @param v ContractVo
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Com(GridVo gridVo,ContractVo v) throws GlobalException;
	
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:52:55 <br>
	 * Description : 快到期结合同 <br>
	 * @param gridVo GridVo
	 * @param v ContractVo
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Fast(GridVo gridVo,ContractVo v) throws GlobalException;
	
	/**
	 * Create on : Danlee Li 2017年3月30日 上午10:52:55 <br>
	 * Description : 已到期结合同 <br>
	 * @param gridVo GridVo
	 * @param v ContractVo
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Expired(GridVo gridVo,ContractVo v) throws GlobalException;
	
	
	
	
	/**
	 * 
	 * @Title: gridData4Sale   
	 * @Description: CRM的合同管理  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4Sale(GridVo gridVo,ContractVo v) throws GlobalException;
	
	
	
	/**
	 * 
	 * @Title: gridData4FastSale   
	 * @Description:   CRM快到期合同
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4FastSale(GridVo gridVo,ContractVo v) throws GlobalException;

	/**
	 * 
	 * @Title: gridData4ExpiredSale   
	 * @Description: CRM已到期合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4ExpiredSale(GridVo gridVo,ContractVo v) throws GlobalException;
	
	/**
	 * 
	 * @Title: gridData4ExecuteSale   
	 * @Description: CRM执行中合同   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4ExecuteSale(GridVo gridVo,ContractVo v) throws GlobalException;
	
	/**
	 * 
	 * @Title: gridData4ComSale   
	 * @Description:  CRM已完结合同  
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4ComSale(GridVo gridVo,ContractVo v) throws GlobalException;
	
	
	
	/**
	 * 
	 * @Title: executeSchedule   
	 * @Description:  执行定时任务，查询即将到期的合同，更新到客户回访表里，作为消息进行提醒  
	 * @param: @throws GlobalException      
	 * @return: void      
	 * @throws
	 */
	public void executeSchedule()throws GlobalException;
	
	
	public List<ContractVo> queryAllByAccountId()throws GlobalException;
	
	
	public GridVo gridListData(GridVo gridVo,ContractVo v) throws GlobalException;
	
	
	
}
