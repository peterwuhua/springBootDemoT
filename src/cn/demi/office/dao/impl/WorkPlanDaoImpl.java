package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IWorkPlanDao;
import cn.demi.office.po.WorkPlan;

@Repository("office.workPlanDao")
public class WorkPlanDaoImpl extends BaseDaoImpl<WorkPlan> implements IWorkPlanDao {
}
