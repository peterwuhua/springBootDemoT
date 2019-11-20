package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.std.po.PstandItem;

public interface IPstandItemDao extends IBaseDao<PstandItem> {
	
	/**
	 * 一般 关系集合
	 * 水
	 * @param sampTypeIds
	 * @param standIds
	 * @param itemId
	 * @return
	 */
//	List<PstandItem> listStand4Item(String sampTypeIds,String standIds,String itemId);
	/**
	 * 一般 关系集合
	 * 水
	 * @param sampTypeIds
	 * @param standIds
	 * @param itemId
	 * @return
	 */
	List<PstandItem> listStand4Item(String standIds,String itemId);
	/**
	 * 标准项目 关系集合
	 * @param sampTypeIds
	 * @param standIds
	 * @param itemId
	 * @return
	 */
	List<PstandItem> listStand(String standIds,String itemId,String varStr);
	/**
	 * 获取 声 类标准集合
	 * 获取气 类标准的浓度指标集合
	 * @param sampTypeIds
	 * @param standIds
	 * @param itemId  //项目
	 * @param itemType 功能区 指标
	 * @return
	 */
	List<PstandItem> listStand4Item(String standIds,String itemId,String itemType);
	/**
	 * 获取 标准集合
	 * @param standIds 标准集合
	 * @param itemId  项目
	 * @param itemType 指标
	 * @param type  分类
	 * @param otherType 其他分类
	 * @return
	 */
	List<PstandItem> listStand(String standIds,String itemId,String itemType,String type,String otherType);
	/**
	 * 获取 气  类标准集合
	 * 速率 指标
	 * @param sampTypeIds
	 * @param standIds
	 * @param itemId  //项目
	 * @param itemType 功能区
	 * @param nd 浓度
	 * @param pqtgd 排气筒高度
	 * @return
	 */
	List<PstandItem> listStand4Item(String sampTypeIds,String standIds,String itemId,String itemType,String nd,String pqtgd);
	/**
	 * 获取  符合 限值 的 最严格标准
	 * @param sampTypeIds 样品
	 * @param standIds 标准ids
	 * @param itemId  //项目
	 * @param limited 限值
	 * @return
	 */
	PstandItem findStand(String sampTypeIds,String standIds,String itemId,String limited);
	/**
	 * 判定结果是否 符合评价标准的限值
	 * @param pi  判定 限值 对象
	 * @param value  输入结果
	 * @return
	 */
	boolean check(PstandItem pi,String value);
	/**
	 * 判定结果是否 符合评价标准的限值
	 * @param varStr   限值
	 * @param value  输入结果
	 * @return
	 */
	boolean check(String varStr,String value);
	
	/**
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort(String standId);
}

