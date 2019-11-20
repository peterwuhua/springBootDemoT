package cn.demi.bus.sample.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.sample.dao.ISampUsedDao;
import cn.demi.bus.sample.po.SampUsed;

@Repository("bus.sampUsedDao")
public class SampUsedDaoImpl extends BaseDaoImpl<SampUsed> implements ISampUsedDao {
}
