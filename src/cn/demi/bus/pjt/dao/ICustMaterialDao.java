package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.CustMaterial;

public interface ICustMaterialDao extends IBaseDao<CustMaterial> {
	/**
	 * 根据项目id删除物料详细信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取物料详情集合
	 * @param pjId
	 */
	List<CustMaterial> listByProjectId(String pjId);
	
	/**
	 * 根据车间id删除物料详细信息
	 * @param roomId 车间Id
	 */
	void deleteByRoomId(String roomId);
	/**
	 * 获取物料详情集合
	 * @param roomId 车间Id
	 */
	List<CustMaterial> listByRoomId(String roomId);
	
	int getMaxSort(String pjId);
	
	/**
	 * 
	 * @Title: listByPjtId   
	 * @Description: 获取物料类型集合  
	 * @param: @param pjId
	 * @param: @return      
	 * @return: List<TypeExVo>      
	 * @throws
	 */
	List<CustMaterial> listTypeByPjtId(String pjId);
	
	/**
	 * 
	 * @Title: listBypjtIdAndType   
	 * @Description: 获取物料集合   
	 * @param: @param pjId
	 * @param: @param type
	 * @param: @return      
	 * @return: List<CustMaterial>      
	 * @throws
	 */
	List<CustMaterial> listBypjtIdAndType(String pjId,String type);
}

