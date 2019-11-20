package cn.demi.bus.test.dao.impl;

import org.springframework.stereotype.Repository;
import cn.demi.bus.test.dao.IStantSolutDao;
import cn.demi.bus.test.po.StantSolut;
import cn.core.framework.common.dao.BaseDaoImpl;

@Repository("bus.stantSolutDao")
public class StantSolutDaoImpl extends BaseDaoImpl<StantSolut> implements IStantSolutDao {
}
