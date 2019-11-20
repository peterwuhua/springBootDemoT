package cn.demi.init.car.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.car.vo.CarUseVo;

@Transactional
public interface ICarUseService extends IBaseService<CarUseVo> {
	/**
	 * 采样安排调用车辆使用情况
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CarUseVo> listData(CarUseVo v) throws GlobalException;
}
