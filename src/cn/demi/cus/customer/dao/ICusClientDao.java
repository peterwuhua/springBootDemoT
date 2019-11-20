package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.cus.customer.po.CusClient;

/**
 * Create on : 2016年11月15日 下午2:46:53  <br>
 * Description :客户受检单位DAO  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface ICusClientDao extends IBaseDao<CusClient> {
	/**
	 * Description : 根据受检单位ID获取受检单位客户关联信息 <br>
	 * @param clientId 受检单位ID
	 * @param custId 客户Id
	 * @return
	 */
	public CusClient findByClientIdAndCustId(String clientId,String custId);
	/**
	 * Description : 根据受检单位ID获取受检单位客户关联信息 <br>
	 * @param clientId 受检单位ID
	 * @return
	 */
	public List<CusClient> findByClientId(String clientId);
	/**
	 * Description :根据客户ID获取受检单位信息列表   <br>
	 * @param cusId
	 * @return
	 */
	public List<CusClient> listBycusId(String cusId);
	
	/**
	 * Description :根据客户ID获取受检单位信息列表   <br>
	 * @param cusId
	 * @return
	 */
	public List<CusClient> listBycusIds(String ...ids);
}

