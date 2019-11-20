package cn.demi.zk.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.zk.vo.ZkSamplingVo;

@Transactional
public interface IZkSamplingService extends IBaseService<ZkSamplingVo> {
	
}
