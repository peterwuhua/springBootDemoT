package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.CustWork;

public interface ICustWorkDao extends IBaseDao<CustWork> {
	/**
	 * 根据项目id删除写实调查表详细信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取写实调查表详情集合
	 * @param pjId
	 */
	List<CustWork> listByProjectId(String pjId);
	
	/**
	 * 根据车间id删除写实调查表详细信息
	 * @param roomId 车间Id
	 */
	void deleteByRoomId(String roomId);
	/**
	 * 获取物写实调查表集合
	 * @param roomId 车间Id
	 */
	List<CustWork> listByRoomId(String roomId);
	
	/**
	 * 根据车间id删除写实调查表详细信息
	 * @param roomId 车间Id
	 */
	void deleteByPointId(String pointId);
	/**
	 * 获取物写实调查表集合
	 * @param roomId 车间Id
	 */
	List<CustWork> listByPointId(String pointId);
	
	int getMaxSort(String pjId);
}

