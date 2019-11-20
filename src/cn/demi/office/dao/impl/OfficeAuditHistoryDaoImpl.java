package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;
import cn.demi.office.dao.IOfficeAuditHistoryDao;
import cn.demi.office.po.OfficeAuditHistory;
import cn.core.framework.common.dao.BaseDaoImpl;

@Repository("office.auditHistoryDao")
public class OfficeAuditHistoryDaoImpl extends BaseDaoImpl<OfficeAuditHistory> implements IOfficeAuditHistoryDao {
}
