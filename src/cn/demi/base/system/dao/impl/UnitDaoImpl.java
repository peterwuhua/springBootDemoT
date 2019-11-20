package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.IUnitDao;
import cn.demi.base.system.po.Unit;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:58 </strong> <br>
 * <strong>Description : UnitDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.unitDao")
public class UnitDaoImpl extends BaseDaoImpl<Unit> implements IUnitDao {}
