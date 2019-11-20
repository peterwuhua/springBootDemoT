package cn.demi.bus.pg.dao.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tencent.xinge.XingeApp;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.EunmProject;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.ISysMsgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Function;
import cn.demi.base.system.po.SysMsg;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;

@Repository("bus.progressDao")
public class ProgressDaoImpl extends BaseDaoImpl<Progress> implements IProgressDao {
	@Autowired
	private ISysMsgDao sysMsgDao;

	@Override
	public Progress add(String busId, String status, String orgId, String orgName, String userId, String userName) {
		Progress po = new Progress();
		po.setBusId(busId);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if (po.getSort() == null) {
			po.setSort(getMaxSort() + 1);
		}
		getEntityManager().persist(po);
		//pushMsg(orgId, userId, status, po);
		return po;
	}

	public Progress add4Project(String busId, String status, String orgId, String orgName, String userId, String userName) {
		Progress po = new Progress();
		po.setBusId(busId);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if (po.getSort() == null) {
			po.setSort(getMaxSort() + 1);
		}
		getEntityManager().persist(po);
		//pushMsg(orgId, userId, status, po);
		return po;
	}
	
	
	@Override
	public Progress update(String id, String status, String orgId, String orgName, String userId, String userName) {
		Progress po = super.findById(id);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		//pushMsg(orgId, userId, status, po);
		return po;
	}

	public void pushMsg(String orgId, String userId, String status, Progress po) {

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream in = classLoader.getResourceAsStream("resources/xinge.properties");
			Properties p = new Properties();
			p.load(in);
			long accessId=Long.valueOf(p.getProperty("ACCESS"));
			String secretKey=String.valueOf(p.get("SECRET"));
			
			if (!StrUtils.isBlankOrNull(userId)) {
				StringBuffer jpql = new StringBuffer("FROM " + getEntityName(Account.class));
				jpql.append(" WHERE id IN ('" + toString(userId).replace(",", "','") + "')");
				jpql.append(" AND isDel= " + Po.N);
				@SuppressWarnings("unchecked")
				List<Account> lst = super.query(jpql.toString()).getResultList();
				for (Account at : lst) {
					sysMsgDao.sendMsg(po.getBusId(), po.getBusType(), po.getBusType(),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", at.getId(),
							at.getLoginName(), SysMsg.RT_USER);
					// XingeApp.pushAccountAndroid(2100328362, "d85deb7fae55477a2143facea3be0efd",
					// EunmTask.getName(po.getStatus()), "您有一条新的" + EunmTask.getName(po.getStatus())
					// + "任务，具体请登陆系统查看！", at.getId());
					XingeApp.pushAccountAndroid(accessId, secretKey,
							EunmTask.getName(po.getStatus()),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", at.getId());
				}
			} else if (!StrUtils.isBlankOrNull(orgId)) {
				StringBuffer hql = new StringBuffer(
						"select distinct(at.id) from v_sys_account at join v_sys_account_role r on r.account_id=at.id join v_sys_role_fun rf on r.role_id = rf.role_id join v_"
								+ getEntityName(Function.class) + " fun on fun.id=rf.fun_id ");
				hql.append(" where fun.is_del='" + Po.N + "' AND fun.wf_code='" + status + "' AND at.org_id='" + orgId
						+ "'");
				@SuppressWarnings("unchecked")
				List<String> pList = super.queryBySql(hql.toString());
				for (String id : pList) {
					if (StrUtils.isBlankOrNull(id)) {
						continue;
					}

					sysMsgDao.sendMsg(po.getBusId(), po.getBusType(), po.getBusType(),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", id, "", SysMsg.RT_USER);

					// XingeApp.pushAccountAndroid(2100328362, "d85deb7fae55477a2143facea3be0efd",
					// EunmTask.getName(po.getStatus()), "您有一条新的" + EunmTask.getName(po.getStatus())
					// + "任务，具体请登陆系统查看！", id);
					XingeApp.pushAccountAndroid(accessId, secretKey,
							EunmTask.getName(po.getStatus()),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", id);

				}
			} else {
				StringBuffer hql = new StringBuffer(
						"select distinct(at.id) from v_sys_account at join v_sys_account_role r on r.account_id=at.id join v_sys_role_fun rf on r.role_id = rf.role_id join v_"
								+ getEntityName(Function.class) + " fun on fun.id=rf.fun_id ");
				hql.append(" where fun.is_del='" + Po.N + "' AND fun.wf_code='" + status + "' ");
				@SuppressWarnings("unchecked")
				List<String> pList = super.queryBySql(hql.toString());
				for (String id : pList) {
					if (StrUtils.isBlankOrNull(id)) {
						continue;
					}
					sysMsgDao.sendMsg(po.getBusId(), po.getBusType(), po.getBusType(),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", id, "", SysMsg.RT_USER);

					// XingeApp.pushAccountAndroid(2100328362, "d85deb7fae55477a2143facea3be0efd",
					// EunmTask.getName(po.getStatus()), "您有一条新的" + EunmTask.getName(po.getStatus())
					// + "任务，具体请登陆系统查看！", id);
					XingeApp.pushAccountAndroid(accessId, secretKey,
							EunmTask.getName(po.getStatus()),
							"您有一条新的" + EunmTask.getName(po.getStatus()) + "任务，具体请登陆系统查看！", id);

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public Progress update(String id, String status) {
		Progress po = super.findById(id);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		return po;
	}

	public Progress update4Project(String id, String status) {
		Progress po = super.findById(id);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		return po;
	}
	
	
	@Override
	public Progress findByBusId(String busId) {
		List<Progress> pList = super.findByProperty("busId", busId);
		if (null != pList && pList.size() > 0) {
			return pList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Progress update(String id, String status, String userId, String userName) {
		Progress po = super.findById(id);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		return po;
	}

	
	public Progress update4Project(String id, String status, String userId, String userName) {
		Progress po = super.findById(id);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		return po;
	}
	
	public Progress update4Project(String id, String status, String orgId, String orgName, String userId, String userName) {
		Progress po = super.findById(id);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onUpdate();
		getEntityManager().merge(po);
		//pushMsg(orgId, userId, status, po);
		return po;
	}
	
	
	@Override
	public void deleteByBusId(String busId) {
		String hql = "DELETE FROM  " + getEntityName(Progress.class) + " WHERE busId='" + busId + "' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public boolean checkAllComp(String taskId, String comSt) {
		StringBuffer hql = new StringBuffer(
				"FROM " + getEntityName(Progress.class) + " WHERE isDel='" + ProgressLog.N + "'");
		hql.append(" AND busId in ('" + taskId.replace(",", "','") + "') AND status='" + comSt + "' ");
		List<Progress> lst = super.list(hql.toString());
		if (lst != null && lst.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Progress addApp(String busId, String status, ObjVo objVo) {
		Progress po = new Progress();
		po.setBusId(busId);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setOrgId(objVo.getOrgId());
		po.setOrgName(objVo.getOrgName());
		po.setUserId(objVo.getUserId());
		po.setUserName(objVo.getName());
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if (po.getSort() == null) {
			po.setSort(getMaxSort() + 1);
		}
		getEntityManager().persist(po);
		//pushMsg(objVo.getOrgId(), objVo.getUserId(), status, po);
		return po;
	}

}
