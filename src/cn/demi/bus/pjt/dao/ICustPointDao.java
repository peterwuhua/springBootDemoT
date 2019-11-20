package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.CustPoint;

public interface ICustPointDao extends IBaseDao<CustPoint> {
	
	/**
	 * 根据项目id删除监测点详细信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取监测点详情集合
	 * @param pjId
	 */
	List<CustPoint> listByProjectId(String pjId);
	
	/**
	 * 根据车间id删除监测点详细信息
	 * @param roomId 车间Id
	 */
	void deleteByRoomId(String roomId);
	/**
	 * 获取监测点详情集合
	 * @param roomId 车间Id
	 */
	List<CustPoint> listByRoomId(String roomId);
	
	int getMaxSort(String pjId);
	
	CustPoint findByName(String roomId,String name);
}

