package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.ICongressDao;
import cn.demi.office.po.Congress;

@Repository("office.congressDao")
public class CongressDaoImpl extends BaseDaoImpl<Congress> implements ICongressDao {
}
