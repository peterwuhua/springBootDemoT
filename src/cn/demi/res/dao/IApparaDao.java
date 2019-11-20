package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.po.Appara;
/**
 * 仪器 数据持久层接口
 * @author QuJunLong
 *
 */
public interface IApparaDao extends IBaseDao<Appara> {
	/**
	 * 概要 :通过日期段获取list
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
	List<Appara> listByTime(String name,String startTime, String endTime, String type) throws GlobalException;
	
	List<String[]> listTestByDate(String name) throws GlobalException;
	
	/**
	 * 获取正常可使用的仪器集合
	 * @param ids 仪器ids
	 * @return
	 */
	List<Appara> listByIds4Use(String ids);
}

