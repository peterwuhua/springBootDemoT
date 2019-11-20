package cn.demi.init.ti.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.ti.dao.IRxNdDao;
import cn.demi.init.ti.po.RxNd;

@Repository("init.rxNdDao")
public class RxNdDaoImpl extends BaseDaoImpl<RxNd> implements IRxNdDao {
}
