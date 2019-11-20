package cn.demi.init.car.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.car.vo.CarVo;

@Transactional
public interface ICarService extends IBaseService<CarVo> {
	
	/**
	 * 可选择车辆
	 * 指正常车辆
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Select(GridVo gridVo, CarVo v) throws GlobalException;
	
	/**
	 * 采样安排
	 * 显示车辆列表
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CarVo> list4Select(GridVo gridVo,CarVo v) throws GlobalException;
}
