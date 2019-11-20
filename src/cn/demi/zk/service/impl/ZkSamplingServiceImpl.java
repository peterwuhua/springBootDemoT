package cn.demi.zk.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.zk.po.ZkSampling;
import cn.demi.zk.service.IZkSamplingService;
import cn.demi.zk.vo.ZkSamplingVo;

@Service("zk.samplingService")
public class ZkSamplingServiceImpl extends BaseServiceImpl<ZkSamplingVo,ZkSampling> implements
		IZkSamplingService {
}
