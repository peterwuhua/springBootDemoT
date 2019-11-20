package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.CusProduce;
/**
 * Create on : 2016年11月15日 下午2:47:36  <br>
 * Description : 客户生产单位DAO <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface ICusProduceDao extends IBaseDao<CusProduce> {
	/**
	 * Create on : Danlee Li 2016年11月15日 下午2:47:46 <br>
	 * Description : 根据客户ID获取生产单位信息列表 <br>
	 * @param cusId
	 * @return
	 * @throws GlobalException
	 */
	public List<CusProduce> listBycusId(String cusId) throws GlobalException;
	/**
	 * Create on : Danlee Li 2016年11月15日 下午2:47:55 <br>
	 * Description : 根据生产单位ID 查找生产单位和客户信息 <br>
	 * @param proId
	 * @return
	 */
	public CusProduce findByProId(String proId);
	
	/**
	 * Create on : Danlee Li 2016年11月15日 下午2:47:46 <br>
	 * Description : 根据客户IDS获取生产单位信息列表 <br>
	 * @param cusId
	 * @return
	 * @throws GlobalException
	 */
	public List<CusProduce> listBycusIds(String... cusId) throws GlobalException;
}

