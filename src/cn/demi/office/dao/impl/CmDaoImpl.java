package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.ICmDao;
import cn.demi.office.po.Cm;

@Repository("office.cmDao")
public class CmDaoImpl extends BaseDaoImpl<Cm> implements ICmDao {
}
