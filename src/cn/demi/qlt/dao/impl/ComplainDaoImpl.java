package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.IComplainDao;
import cn.demi.qlt.po.Complain;

@Repository("qlt.complainDao")
public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements IComplainDao {
}
