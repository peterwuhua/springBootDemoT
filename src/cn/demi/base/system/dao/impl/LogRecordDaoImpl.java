package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.ILogRecordDao;
import cn.demi.base.system.po.LogRecord;

@Repository("sys.logRecordDao")
public class LogRecordDaoImpl extends BaseDaoImpl<LogRecord> implements ILogRecordDao {
}
