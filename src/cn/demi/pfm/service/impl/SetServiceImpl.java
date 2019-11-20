package cn.demi.pfm.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.pfm.po.Set;
import cn.demi.pfm.service.ISetService;
import cn.demi.pfm.vo.SetVo;

@Service("pfm.setService")
public class SetServiceImpl extends BaseServiceImpl<SetVo, Set> implements ISetService {

}
