package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pjt.po.Im;

public interface IImDao extends IBaseDao<Im> {
	/**
	 * 保存项目方法关系
	 * @param busId
	 * @param arr 关系数组 itemId-methodId,itemId-methodId
	 */
	void uptIm(String busId,String arr);
	/**
	 * 根据项目id删除分包详细信息
	 * @param pjId
	 */
	void deleteByBusId(String busId);
	/**
	 * 获取项目关系集合
	 * @param busId 业务Id
	 */
	List<Im> listByBusId(String busId);
	/**
	 * 获取唯一关系
	 * 根据业务Id和项目Id
	 * @param busId
	 * @param itemId
	 * @return
	 */
	Im findByBusIdAndItemId(String busId,String itemId);
	/**
	 * 获取项目方法id组合字符传
	 * itemId-methodId,itemId-methodId
	 * @param pjId
	 */
	String findByBusId(String busId);
	
	/**
	 * 保存项目方法关系
	 * @param busId
	 * @param arr 关系数组 itemId-methodId,itemId-methodId
	 */
	void uptIm4App(String busId,String arr,ObjVo objVo);
}

