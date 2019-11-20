package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.TrainVo;

@Transactional
public interface ITrainService extends IBaseService<TrainVo> {
	
	public void update4Run(String ids)throws GlobalException;
	
	public void update4Comp(TrainVo v)throws GlobalException;
	
	public void updateDetail(TrainVo v)throws GlobalException;
}
