package cn.demi.init.ct.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.ct.vo.ContainerVo;

@Transactional
public interface IContainerService extends IBaseService<ContainerVo> {
	
	public ContainerVo findByCode(String id,String code) throws GlobalException;
}
