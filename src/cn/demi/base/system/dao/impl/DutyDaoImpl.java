package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.IDutyDao;
import cn.demi.base.system.po.Duty;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:14 </strong> <br>
 * <strong>Description : DutyDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.dutyDao")
public class DutyDaoImpl extends BaseDaoImpl<Duty> implements IDutyDao {
}
