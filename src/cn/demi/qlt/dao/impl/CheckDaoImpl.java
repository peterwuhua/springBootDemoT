package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.ICheckDao;
import cn.demi.qlt.po.Check;

@Repository("qlt.checkDao")
public class CheckDaoImpl extends BaseDaoImpl<Check> implements ICheckDao {
}
