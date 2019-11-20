package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IWorkReportDao;
import cn.demi.office.po.WorkReport;

@Repository("office.workReportDao")
public class WorkReportDaoImpl extends BaseDaoImpl<WorkReport> implements IWorkReportDao {
}
