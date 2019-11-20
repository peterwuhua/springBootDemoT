package cn.demi.base.system.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.base.system.po.Cfg;
import cn.demi.base.system.service.ICfgService;
import cn.demi.base.system.vo.CfgVo;

@Service("sys.cfgService")
public class CfgServiceImpl extends BaseServiceImpl<CfgVo,Cfg> implements
		ICfgService {
}
