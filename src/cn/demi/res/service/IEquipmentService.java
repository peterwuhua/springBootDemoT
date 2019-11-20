package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.EquipmentVo;

@Transactional
public interface IEquipmentService extends IBaseService<EquipmentVo> {

	GridVo gridDatad(GridVo gridVo, EquipmentVo v, String startDate, String endDate, String type) throws GlobalException;
	
	List<EquipmentVo> listAll(EquipmentVo v) throws GlobalException;
	
	/**
	 * 已出库仪器列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	List<EquipmentVo> listData4Out(GridVo gridVo, EquipmentVo v) throws GlobalException;
}
