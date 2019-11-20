package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.INsDao;
import cn.demi.qlt.po.Ns;

@Repository("qlt.nsDao")
public class NsDaoImpl extends BaseDaoImpl<Ns> implements INsDao {
}
