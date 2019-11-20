package cn.demi.bus.test.service.impl;

import org.springframework.stereotype.Service;
import cn.demi.bus.test.po.StantSolutMx;
import cn.demi.bus.test.vo.StantSolutMxVo;
import cn.demi.bus.test.service.IStantSolutMxService;
import cn.core.framework.common.service.BaseServiceImpl;

@Service("bus.stantSolutMxService")
public class StantSolutMxServiceImpl extends BaseServiceImpl<StantSolutMxVo,StantSolutMx> implements
		IStantSolutMxService {
}
