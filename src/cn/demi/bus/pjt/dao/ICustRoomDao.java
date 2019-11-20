package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.CustRoom;

public interface ICustRoomDao extends IBaseDao<CustRoom> {
	
	/**
	 * 根据项目id删除车间详细信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取车间详情集合
	 * @param pjId
	 */
	List<CustRoom> listByProjectId(String pjId);
	/**
	 * 获取最大序号
	 * @param pjId
	 * @return
	 */
	int getMaxSort(String pjId);
	/**
	 * 获取车间信息
	 * @param pjId 项目id
	 * @param roomName 车间名称
	 * @return
	 */
	CustRoom findByName(String pjId,String roomName);
}

