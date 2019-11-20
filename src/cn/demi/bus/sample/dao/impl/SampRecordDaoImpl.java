package cn.demi.bus.sample.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.po.SampRecord;

@Repository("bus.sampRecordDao")
public class SampRecordDaoImpl extends BaseDaoImpl<SampRecord> implements ISampRecordDao {
}
