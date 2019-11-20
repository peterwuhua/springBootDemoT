package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.IGsDao;
import cn.demi.qlt.po.Gs;

@Repository("qlt.gsDao")
public class GsDaoImpl extends BaseDaoImpl<Gs> implements IGsDao {
}
