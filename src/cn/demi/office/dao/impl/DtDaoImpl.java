package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;

@Repository("office.dtDao")
public class DtDaoImpl extends BaseDaoImpl<Dt> implements IDtDao {
}
