package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.vo.EquipmentOutVo;

@Transactional
public interface IEquipmentOutService extends IBaseService<EquipmentOutVo> {
	
	/**
	 * 获取当天之后的仪器出库记录
	 * 用于采样安排环节数据输出
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	List<EquipmentOutVo> listData(EquipmentOutVo v) throws GlobalException;
	/**
	 *获取首页提醒集合
	 * @param list
	 * @return
	 * @throws GlobalException
	 */
	List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
}
