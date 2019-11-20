package cn.demi.qlt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.qlt.dao.IAccidentDao;
import cn.demi.qlt.po.Accident;

@Repository("qlt.accidentDao")
public class AccidentDaoImpl extends BaseDaoImpl<Accident> implements IAccidentDao {
}
