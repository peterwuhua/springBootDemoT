package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Contacts;
/**
 * Create on : 2016年11月15日 下午2:46:30  <br>
 * Description : 联系人DAO <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface IContactsDao extends IBaseDao<Contacts> {

	public List<Contacts> listBycusId(String... cusId) throws GlobalException;
	/**
	 * 获取客户下某个联系人
	 * @param cusId
	 * @param person
	 * @return
	 * @throws GlobalException
	 */
	public Contacts findBycusId(String cusId,String person) throws GlobalException;
}

