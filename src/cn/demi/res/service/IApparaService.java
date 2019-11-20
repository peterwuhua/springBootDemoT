package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.vo.ApparaVo;

@Transactional
public interface IApparaService extends IBaseService<ApparaVo> {

	GridVo gridDatad(GridVo gridVo, ApparaVo v, String startDate, String endDate, String type) throws GlobalException;
	
	List<ApparaVo> listAll(ApparaVo v) throws GlobalException;
	 
	/**
	 * 可检定/校准的仪器列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridData4Record(GridVo gridVo, ApparaVo v) throws GlobalException;
	/**
	 * 可维修的仪器列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridData4Reppair(GridVo gridVo, ApparaVo v) throws GlobalException;
	
	/**
	 * 
	 * @Title: gridData4Out   
	 * @Description: 设备弹出左侧数据源集合   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	GridVo gridData4Out(GridVo gridVo, ApparaVo v) throws GlobalException;
	
	
	List<ApparaVo> listData4Out(GridVo gridVo, ApparaVo v) throws GlobalException;
	
	
	/**
	 * 设备弹出右侧数据源集合
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	List<ApparaVo> listData4Sels(String ids) throws GlobalException;
	/**
	 *获取首页提醒集合
	 * @param list
	 * @return
	 * @throws GlobalException
	 */
	List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
}
