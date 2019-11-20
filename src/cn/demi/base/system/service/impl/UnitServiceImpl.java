package cn.demi.base.system.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.base.system.po.Unit;
import cn.demi.base.system.service.IUnitService;
import cn.demi.base.system.vo.UnitVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:13:17 </strong> <br>
 * <strong>Description : UnitServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.unitService")
public class UnitServiceImpl extends BaseServiceImpl<UnitVo, Unit> implements
		IUnitService {
}
