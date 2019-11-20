package cn.demi.cus.contract.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.po.Contract;

/**
 * Create on : 2016年11月15日 下午2:46:39  <br>
 * Description :合同DAO  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface IContractDao extends IBaseDao<Contract> {
	
	public List<Contract> listBycusId(String... cusId) throws GlobalException;
	/**
	 * Create on : Eason Qin 2017年3月24日 下午2:56:21 <br>
	 * Description : 根据合同编号查询合同信息 <br>
	 * @param code
	 * @return
	 */
	public Contract findByCode(String code) throws GlobalException;
	/**
	 * Create on : Eason Qin 2017年3月29日 上午11:08:21 <br>
	 * Description : 根据客户Id查询合同信息 <br>
	 * @param cusId
	 * @return
	 * @throws GlobalException
	 */
	public Contract findByCusId(String cusId) throws GlobalException;
	
	/**
	 * 
	 * @Title: getPageResult4Bill   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param pageResult
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: PageResult      
	 * @throws
	 */
	public PageResult getPageResult4Bill(PageResult pageResult) throws GlobalException;
}

