package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.std.po.Pstandard;

public interface IPstandardDao extends IBaseDao<Pstandard> {
	 
	/**
	 * 根据编号获取标准
	 * @param code
	 * @return
	 */
	Pstandard findByCode(String code);
	 
	/**
	 * 获取标准集合
	 * @param sampTyleIds 样品类型Id
	 * @return
	 */
	List<Pstandard> listBySampTyle(String sampTyleIds);	
	/**
	 * 获取标准集合
	 * @param sampTyleIds 样品类型Id
	 * @return
	 */
	List<Pstandard> listBySampTyle(String ids,String sampTyleIds);	
}

