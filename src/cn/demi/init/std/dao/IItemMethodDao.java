package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.std.po.ItemMethod;

public interface IItemMethodDao extends IBaseDao<ItemMethod> {
	/**
	 *删除关系
	 * @param itemId
	 * @return
	 */
	public void deleteByItemId(String itemId);
	/**
	 * 删除关系
	 * @param methodId
	 * @return
	 */
	public void deleteByMethodId(String methodId);
	/**
	 * 获取方法项目关系
	 * @param itemId
	 * @return
	 */
	public List<ItemMethod> listByItemId(String itemId);
	/**
	 * 获取方法项目关系
	 * @param itemIds 项目集合
	 * @return
	 */
	public List<ItemMethod> listByItemIds(String itemIds);
	/**
	 * 获取方法项目关系
	 * @param methodId
	 * @return
	 */
	public List<ItemMethod> listByMethodId(String methodId);
	/**
	 * 获取方法项目关系
	 * @param methodId
	 * @param acctId
	 * @return
	 */
	public ItemMethod findByMethodIdAndItemId(String methodId,String itemId);
	/**
	 * 获取项目最大存储时间
	 * @param itemIds 项目Ids
	 * @return
	 */
	public float findMaxHours(String itemIds);
}

