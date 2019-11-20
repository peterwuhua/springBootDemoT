package cn.demi.res.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.res.po.TrainDetail;
import cn.demi.res.service.ITrainDetailService;
import cn.demi.res.vo.TrainDetailVo;

@Service("res.trainDetailService")
public class TrainDetailServiceImpl extends BaseServiceImpl<TrainDetailVo,TrainDetail> implements
		ITrainDetailService {
}
