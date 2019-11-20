package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.std.po.Item;

public interface IItemDao extends IBaseDao<Item> {
	
	Item findByName(String name);
	/**
	 * 获取样品编号代码
	 * @param itemIds
	 * @return
	 */
	String findCode(String itemIds);
	
	/**
	 * 检查是否都是现场项目
	 * @param itemIds
	 * @return true 全现场 false 含有实验室项目
	 */
	boolean checkIsNow(String itemIds);
	
	List<Item> listByPname(String name);
	/**
	 * 获取最大保存期限
	 * @param ids
	 * @return
	 */
	Float getMaxHours(String ids);
	/**
	 * 获取分析费合计
	 * @param ids
	 * @return
	 */
	float getSumPrice(String ids);
	/**
	 * 获取项目集合
	 * @param ids 项目Id
	 * @param isNow 项目类型：现场监测 是/否
	 * @return
	 */
	List<Item> listByIds(String ids,String isNow);
}

