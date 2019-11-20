package cn.demi.init.std.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.std.dao.IMstandardDao;
import cn.demi.init.std.po.Mstandard;

@Repository("init.mstandardDao")
public class MstandardDaoImpl extends BaseDaoImpl<Mstandard> implements IMstandardDao {
}
