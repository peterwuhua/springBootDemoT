package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IQjglDao;
import cn.demi.office.po.Qjgl;

@Repository("office.qjglDao")
public class QjglDaoImpl extends BaseDaoImpl<Qjgl> implements IQjglDao {
}
