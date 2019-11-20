package cn.demi.app.offfice.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.offfice.service.AppQjService;
import cn.demi.app.offfice.vo.AppQjAuditEdit;
import cn.demi.app.offfice.vo.AppQjAuditPage;
import cn.demi.app.offfice.vo.AppQjSqPage;
import cn.demi.app.offfice.vo.AppQjShPage;
import cn.demi.app.offfice.vo.AppQjSpEdit;
import cn.demi.app.offfice.vo.AppQjSpPage;
import cn.demi.app.offfice.vo.AppQjSqEdit;
import cn.demi.app.offfice.vo.AppQjLogList;
import cn.demi.app.offfice.vo.AppQjShEdit;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Kq;
import cn.demi.office.service.IKqAuditService;
import cn.demi.office.service.IKqService;
import cn.demi.office.service.IKqShService;
import cn.demi.office.service.IKqSpService;
import cn.demi.office.vo.KqVo;

@Service("app.qjService")
public class AppQjServiceImpl implements AppQjService {
	@Autowired
	private IKqDao kqDao;
	@Autowired
	private IKqService kqService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IKqAuditService kqAuditService;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IKqShService kqShService;
	@Autowired
	private IKqSpService kqSpService;

	@Override
	public List<AppQjSqPage> qjSqList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  (user_id = '" + objVo.getRealUserId() + "' or  user_id = '" + objVo.getId() + "') ");
		hql.append(" ORDER BY create_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjSqPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjSqPage vo = new AppQjSqPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				vo.setFstatus(po.getFstatus());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public AppQjSqEdit appQjSqEdit(String id) throws GlobalException {
		KqVo po = kqService.findById(id);
		AppQjSqEdit vo = new AppQjSqEdit();
		vo.setId(po.getId());
		vo.setType(po.getType());
		vo.setDeptName(po.getDeptName());
		vo.setDeptId(po.getDeptId());
		vo.setUserName(po.getUserName());
		vo.setUserId(po.getUserId());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setHours(String.valueOf(po.getHours()));
		vo.setJober(po.getJober());
		vo.setJoberId(po.getJoberId());
		vo.setContent(po.getContent());
		List<ProgressLogVo> logVoList = progressLogService.findList(null, po.getId());
		List<AppQjLogList> logList = new ArrayList<>();
		for (ProgressLogVo prologVo : logVoList) {
			AppQjLogList appLogVo = new AppQjLogList();
			appLogVo.setBusType(prologVo.getBusType());
			appLogVo.setUserName(prologVo.getUserName());
			appLogVo.setLogTime(prologVo.getLogTime());
			appLogVo.setAudit(prologVo.getAudit());
			appLogVo.setMsg(prologVo.getMsg());
			logList.add(appLogVo);
		}
		vo.setAppQjSqLogList(logList);
		return vo;
	}

	@Override
	public boolean appQjSqSave(AppQjSqEdit appQjSqEdit, ObjVo objVo) throws GlobalException {
		try {
			if (StrUtils.isNotBlankOrNull(appQjSqEdit.getId())) {
				update(appQjSqEdit, objVo);
			} else {
				add(appQjSqEdit, objVo);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void add(AppQjSqEdit vo, ObjVo objVo) {
		Kq po = new Kq();
		po = po.toPo(vo, po);
		po.setType(vo.getType());
		po.setNo(createCode());
		po.setDeptId(objVo.getDepId());
		po.setDeptName(objVo.getOrgName());
		po.setOrgId(objVo.getOrgId());
		po.setOrgName(objVo.getOrgName());
		po.setUserId(vo.getUserId());
		po.setUserName(vo.getUserName());
		po.setFstatus(Kq.ST_0);
		kqDao.add(po);
		vo.setId(po.getId());
		Progress pg = progressDao.add(po.getId(), EunmTask.QJ_SQ.getStatus(), po.getOrgId(), po.getOrgName(),
				po.getUserId(), po.getUserName());
		po.setStatus(pg.getStatus());
		commit(po, vo);
		kqDao.update(po);

	}

	private void update(AppQjSqEdit vo, ObjVo objVo) {
		Kq po = kqDao.findById(vo.getId());
		po = po.toPo(vo, po);
		po.setFstatus(Kq.ST_0);
		commit(po, vo);
		kqDao.update(po);

	}

	public void commit(Kq po, AppQjSqEdit v) {
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg = progressDao.findByBusId(po.getId());
			pg = progressDao.update(pg.getId(), EunmTask.QJ_AD.getStatus(), po.getOrgId(), po.getOrgName(), null, null);
			po.setStatus(pg.getStatus());
			po.setFstatus(Kq.ST_1);
		}
	}

	/**
	 * 生成编号
	 */
	public String createCode() {
		String flag = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(no) FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N + " AND no like '"
				+ flag + "%'";
		String ls = (String) kqDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "0001";
		} else {
			ls = ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort = 0;
			}
			sort++;
			if (sort < 10) {
				no += "000" + sort;
			} else if (sort < 100) {
				no += "00" + sort;
			} else if (sort < 1000) {
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	@Override
	public List<AppQjAuditPage> qjAuditList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  org_id = '" + objVo.getOrgId() + "");
		hql.append(" and status = '" + EunmTask.QJ_AD.getStatus() + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjAuditPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjAuditPage vo = new AppQjAuditPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public List<AppQjAuditPage> qjAuditListEd(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  auditId = '" + objVo.getUserId() + "");
		hql.append(" and auditDate is not null");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjAuditPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjAuditPage vo = new AppQjAuditPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				vo.setAuditDate(po.getAuditDate());
				vo.setAuditName(po.getAuditName());
				vo.setAuditResult(po.getAuditResult());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public AppQjAuditEdit appQjAuditEdit(String id) throws GlobalException {
		KqVo po = kqAuditService.findById(id);
		AppQjAuditEdit vo = new AppQjAuditEdit();
		vo.setId(po.getId());
		vo.setType(po.getType());
		vo.setDeptName(po.getDeptName());
		vo.setUserName(po.getUserName());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setHours(String.valueOf(po.getHours()));
		vo.setJober(po.getJober());
		vo.setContent(po.getContent());
		vo.setAuditMsg(po.getAuditMsg());
		vo.setAuditName(po.getAuditName());
		vo.setAuditId(po.getAuditId());
		vo.setAuditDate(po.getAuditDate());
		List<ProgressLogVo> logVoList = progressLogService.findList(null, po.getId());
		List<AppQjLogList> logList = new ArrayList<>();
		for (ProgressLogVo prologVo : logVoList) {
			AppQjLogList appLogVo = new AppQjLogList();
			appLogVo.setBusType(prologVo.getBusType());
			appLogVo.setUserName(prologVo.getUserName());
			appLogVo.setLogTime(prologVo.getLogTime());
			appLogVo.setAudit(prologVo.getAudit());
			appLogVo.setMsg(prologVo.getMsg());
			logList.add(appLogVo);
		}
		vo.setQjLogList(logList);
		return vo;
	}

	@Override
	public boolean qjAuditUpdate(AppQjAuditEdit v, ObjVo objVo) throws GlobalException {
		try {
			Kq po = kqDao.findById(v.getId());
			po.setAuditDate(v.getAuditDate());
			po.setAuditId(v.getAuditId());
			po.setAuditName(v.getAuditName());
			po.setAuditMsg(v.getAuditMsg());
			Progress pg = progressDao.findByBusId(po.getId());
			if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
				po.setAuditResult("通过");
				if (po.getHours() <= 4) {// 小于4小时 直接通过 到 销假
					po.setFstatus(Kq.ST_2);
					pg = progressDao.update(pg.getId(), EunmTask.QJ_XJ.getStatus(), null, null, po.getUserId(),
							po.getUserName());
				} else {// 副总审核
					pg = progressDao.update(pg.getId(), EunmTask.QJ_SH.getStatus(), null, null, null, null);
				}
			} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
				po.setAuditResult("不通过");
				po.setFstatus(Kq.ST_B);
				pg = progressDao.update(pg.getId(), EunmTask.QJ_SQ.getStatus(), null, null, po.getUserId(),
						po.getUserName());
			}
			po.setStatus(pg.getStatus());
			kqDao.update(po);
			progressLogDao.addApp(po.getId(), po.getId(), EunmTask.QJ_AD.getStatus(), v.getIsCommit(), v.getAuditMsg(),
					objVo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	@Override
	public List<AppQjShPage> appQjShList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status = '" + EunmTask.QJ_SH.getStatus() + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjShPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjShPage vo = new AppQjShPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public List<AppQjShPage> appQjShListEd(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and shDate is not null");
		hql.append(" and shId = '" + objVo.getUserId() + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjShPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjShPage vo = new AppQjShPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				vo.setShDate(po.getShDate());
				vo.setShName(po.getShName());
				vo.setShResult(po.getShResult());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public AppQjShEdit appQjShEdit(String id) throws GlobalException {
		KqVo po = kqShService.findById(id);
		AppQjShEdit vo = new AppQjShEdit();
		vo.setId(po.getId());
		vo.setType(po.getType());
		vo.setDeptName(po.getDeptName());
		vo.setUserName(po.getUserName());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setHours(String.valueOf(po.getHours()));
		vo.setJober(po.getJober());
		vo.setContent(po.getContent());
		vo.setShDate(po.getShDate());
		vo.setShName(po.getShName());
		vo.setShId(po.getShId());
		vo.setShMsg(po.getShMsg());
		List<ProgressLogVo> logVoList = progressLogService.findList(null, po.getId());
		List<AppQjLogList> logList = new ArrayList<>();
		for (ProgressLogVo prologVo : logVoList) {
			AppQjLogList appLogVo = new AppQjLogList();
			appLogVo.setBusType(prologVo.getBusType());
			appLogVo.setUserName(prologVo.getUserName());
			appLogVo.setLogTime(prologVo.getLogTime());
			appLogVo.setAudit(prologVo.getAudit());
			appLogVo.setMsg(prologVo.getMsg());
			logList.add(appLogVo);
		}
		vo.setQjLogList(logList);
		return vo;
	}

	@Override
	public boolean qjShUpdate(AppQjShEdit v, ObjVo objVo) throws GlobalException {
		try {
			Kq po = kqDao.findById(v.getId());
			po.setShDate(v.getShDate());
			po.setShId(v.getShId());
			po.setShName(v.getShName());
			po.setShMsg(v.getShMsg());
			Progress pg = progressDao.findByBusId(po.getId());
			if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
				po.setShResult("通过");
				if (po.getHours() <= 24) {
					// 小于4小时 直接通过 到 销假
					po.setFstatus(Kq.ST_2);
					pg = progressDao.update(pg.getId(), EunmTask.QJ_XJ.getStatus(), null, null, po.getUserId(),
							po.getUserName());
				} else {
					// 副总审核
					pg = progressDao.update(pg.getId(), EunmTask.QJ_SP.getStatus(), null, null, null, null);
				}
			} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
				po.setShResult("不通过");
				po.setFstatus(Kq.ST_B);
				pg = progressDao.update(pg.getId(), EunmTask.QJ_SQ.getStatus(), null, null, po.getUserId(),
						po.getUserName());
			}
			po.setStatus(pg.getStatus());
			kqDao.update(po);
			progressLogDao.addApp(po.getId(), po.getId(), EunmTask.QJ_SH.getStatus(), v.getIsCommit(), v.getShMsg(),
					objVo);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	@Override
	public List<AppQjSpPage> AppQjSpList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status = '" + EunmTask.QJ_SP.getStatus() + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjSpPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjSpPage vo = new AppQjSpPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public List<AppQjSpPage> AppQjSpListEd(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel=" + Po.N);
		hql.append(" and spDate is not null");
		hql.append(" and spId = '" + objVo.getUserId() + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = kqDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppQjSpPage> outList = new ArrayList<>();
		if (null != list) {
			for (Kq po : list) {
				AppQjSpPage vo = new AppQjSpPage();
				vo.setId(po.getId());
				vo.setNo(po.getNo());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setUserName(po.getUserName());
				vo.setStartTime(po.getStartTime());
				vo.setEndTime(po.getEndTime());
				vo.setContent(po.getContent());
				vo.setSpDate(po.getSpDate());
				vo.setSpName(po.getSpName());
				vo.setSpResult(po.getSpResult());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public AppQjSpEdit appQjSpEdit(String id) throws GlobalException {
		KqVo po = kqShService.findById(id);
		AppQjSpEdit vo = new AppQjSpEdit();
		vo.setId(po.getId());
		vo.setType(po.getType());
		vo.setDeptName(po.getDeptName());
		vo.setUserName(po.getUserName());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setHours(String.valueOf(po.getHours()));
		vo.setJober(po.getJober());
		vo.setContent(po.getContent());
		vo.setSpDate(po.getSpDate());
		vo.setSpName(po.getSpName());
		vo.setSpId(po.getSpId());
		vo.setSpMsg(po.getSpMsg());
		List<ProgressLogVo> logVoList = progressLogService.findList(null, po.getId());
		List<AppQjLogList> logList = new ArrayList<>();
		for (ProgressLogVo prologVo : logVoList) {
			AppQjLogList appLogVo = new AppQjLogList();
			appLogVo.setBusType(prologVo.getBusType());
			appLogVo.setUserName(prologVo.getUserName());
			appLogVo.setLogTime(prologVo.getLogTime());
			appLogVo.setAudit(prologVo.getAudit());
			appLogVo.setMsg(prologVo.getMsg());
			logList.add(appLogVo);
		}
		vo.setQjLogList(logList);
		return vo;
	}

	@Override
	public boolean qjSpUpdate(AppQjSpEdit v, ObjVo objVo) throws GlobalException {
		try {
			Kq po = kqDao.findById(v.getId());
			po.setSpDate(v.getSpDate());
			po.setSpId(v.getSpId());
			po.setSpName(v.getSpName());
			po.setSpMsg(v.getSpMsg());
			Progress pg = progressDao.findByBusId(po.getId());
			if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
				po.setShResult("通过");
				po.setFstatus(Kq.ST_2);
				pg = progressDao.update(pg.getId(), EunmTask.QJ_XJ.getStatus(), null, null, po.getUserId(),
						po.getUserName());
			} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
				po.setShResult("不通过");
				po.setFstatus(Kq.ST_B);
				pg = progressDao.update(pg.getId(), EunmTask.QJ_SQ.getStatus(), null, null, po.getUserId(),
						po.getUserName());
			}
			po.setStatus(pg.getStatus());
			kqDao.update(po);
			progressLogDao.addApp(po.getId(), po.getId(), EunmTask.QJ_SP.getStatus(), v.getIsCommit(), v.getSpMsg(),
					objVo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
