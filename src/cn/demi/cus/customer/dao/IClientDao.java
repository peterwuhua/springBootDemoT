package cn.demi.cus.customer.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.cus.customer.po.Client;
/**
 * Description :  受检单位DAO<br>
 */
public interface IClientDao extends IBaseDao<Client> {
	
	/**
	 * 根据名称获取受检单位信息
	 * @param name
	 * @return
	 */
	public Client findByName(String name);
}

