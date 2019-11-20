package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.po.Equipment;
/**
 * 仪器 数据持久层接口
 * @author QuJunLong
 *
 */
public interface IEquipmentDao extends IBaseDao<Equipment> {
	/**
	 * 概要 :通过日期段获取list
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
	List<Equipment> listByTime(String name,String startTime, String endTime, String type) throws GlobalException;
	
	List<String[]> listTestByDate(String name) throws GlobalException;
}

