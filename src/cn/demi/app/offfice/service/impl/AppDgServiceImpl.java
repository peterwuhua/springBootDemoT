package cn.demi.app.offfice.service.impl;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.offfice.service.AppDgService;
import cn.demi.app.offfice.vo.AppDg;
import cn.demi.app.offfice.vo.AppDgEdit;
import cn.demi.app.offfice.vo.kqshrUser;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.ICfgDao;
import cn.demi.base.system.po.Cfg;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IDgDao;
import cn.demi.office.dao.IDgTjDao;
import cn.demi.office.po.Dg;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("app.dgService")
public class AppDgServiceImpl implements AppDgService {
	@Autowired
	private IDgDao dgDao;
	@Autowired
	private IDgTjDao dgTjDao;
	@Autowired
	private ICfgDao cfgDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IAccountRoleService accountRoleService;

	@Override
	public List<AppDgEdit> dgList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + dgDao.getEntityName(Dg.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  (user_id = '" + objVo.getRealUserId() + "' or  user_id = '" + objVo.getUserId() + "') ");
		hql.append(" AND type != '打卡'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = dgDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Dg> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppDgEdit> outList = new ArrayList<>();
		if (null != list) {
			for (Dg po : list) {
				AppDgEdit vo = new AppDgEdit();
				vo.setId(po.getId());
				vo.setUserName(po.getUserName());
				vo.setUserId(po.getUserId());
				vo.setDate(po.getDate());
				vo.setContent(po.getContent());
				vo.setRemark(po.getRemark());
				vo.setAuditId(po.getAuditId());
				vo.setAuditName(po.getAuditName());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setStatus(po.getStatus());
				vo.setLatAndLng(po.getLatAndLng());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public List<AppDg> dgAuditList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT id,type,dept_name,user_name,date,content,status,remark,lat_and_lng,audit_name,audit_date,audit_result,audit_msg,audit_id   FROM v_office_dg ");
		hql.append(" where  audit_id like '%" + objVo.getUserId() + "%' and `status`  = '" + Dg.ST_1 + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = dgDao.queryBysql(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows()).getResultList();
		List<AppDg> outList = new ArrayList<>();
		if (null != list) {
			for (Object o : list) {
				Object[] objects = (Object[]) o;
				AppDg appDg = new AppDg();
				appDg.setId(StrUtils.null2Str(objects[0]));
				appDg.setType(StrUtils.null2Str(objects[1]));
				appDg.setDeptName(StrUtils.null2Str(objects[2]));
				appDg.setUserName(StrUtils.null2Str(objects[3]));
				appDg.setDate(StrUtils.null2Str(objects[4]));
				appDg.setContent(StrUtils.null2Str(objects[5]));
				appDg.setStatus(StrUtils.null2Str(objects[6]));
				appDg.setRemark(StrUtils.null2Str(objects[7]));
				appDg.setLatAndLng(StrUtils.null2Str(objects[8]));
				appDg.setAuditName(StrUtils.null2Str(objects[9]));
				appDg.setAuditDate(StrUtils.null2Str(objects[10]));
				appDg.setAuditResult(StrUtils.null2Str(objects[11]));
				appDg.setAuditMsg(StrUtils.null2Str(objects[12]));
				appDg.setAuditId(StrUtils.null2Str(objects[13]));
				outList.add(appDg);
			}
		}
		return outList;
	}

	@Override
	public List<AppDg> dgAuditEdList(ObjVo objVo) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT id,type,dept_name,user_name,date,content,status,remark,lat_and_lng,audit_name,audit_date,audit_result,audit_msg,audit_id   FROM v_office_dg ");
		hql.append(" where  audit_id like '%" + objVo.getUserId() + "%' AND audit_date is not null ");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = dgDao.queryBysql(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows()).getResultList();
		List<AppDg> outList = new ArrayList<>();
		if (null != list) {
			for (Object o : list) {
				Object[] objects = (Object[]) o;
				AppDg appDg = new AppDg();
				appDg.setId(StrUtils.null2Str(objects[0]));
				appDg.setType(StrUtils.null2Str(objects[1]));
				appDg.setDeptName(StrUtils.null2Str(objects[2]));
				appDg.setUserName(StrUtils.null2Str(objects[3]));
				appDg.setDate(StrUtils.null2Str(objects[4]));
				appDg.setContent(StrUtils.null2Str(objects[5]));
				appDg.setStatus(StrUtils.null2Str(objects[6]));
				appDg.setRemark(StrUtils.null2Str(objects[7]));
				appDg.setLatAndLng(StrUtils.null2Str(objects[8]));
				appDg.setAuditName(StrUtils.null2Str(objects[9]));
				appDg.setAuditDate(StrUtils.null2Str(objects[10]));
				appDg.setAuditResult(StrUtils.null2Str(objects[11]));
				appDg.setAuditMsg(StrUtils.null2Str(objects[12]));
				appDg.setAuditId(StrUtils.null2Str(objects[13]));
				outList.add(appDg);
			}
		}
		return outList;
	}

	@Override
	public boolean add2Dk(ObjVo objVo, String latAndLng, String checkOrNoCkeck) throws GlobalException {
		try {
			if (dgDao.findPm(objVo.getUserId(), DateUtils.getCurrDateTimeStr()) == null) {
				return false;
			} else {
				Dg po = new Dg();
				po.setType(Dg.TP_DK);
				po.setDeptId(objVo.getOrgId());
				po.setDeptName(objVo.getOrgName());
				po.setUserId(objVo.getUserId());
				po.setUserName(objVo.getName());
				po.setDate(DateUtils.getCurrDateTimeStr());
				po.setCurTime(DateUtils.getCurrDateTimeStr());
				po.setContent("考勤打卡");
				po.setStatus(Dg.ST_2);
				po.setStatus(Dg.ST_2);
				po.setCheckOrNoCheck(checkOrNoCkeck);
				dgDao.add(po);

				dgTjDao.updDgTj(po.getUserId(), DateUtils.getCurrDateTimeStr());
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String checkTime(ObjVo objVo, String checkOrNoCkeck) throws GlobalException {
		String msg = null;
		Cfg cfg = cfgDao.find(Cfg.TYPE_KT);
		if (cfg != null) {
			String amst = cfg.getD1();// 上午上班时间
			String amet = cfg.getD2();// 上午下班时间
			String pmst = cfg.getD3();// 下午上班时间
			String pmet = cfg.getD4();// 下午下班时间
			if (StrUtils.isNotBlankOrNull(amst) && StrUtils.isNotBlankOrNull(pmst)) {
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("HH");
				String str = df.format(date);
				int a = Integer.parseInt(str);
				if (a < 12) {
					if (checkOrNoCkeck.equals("1")) {
						List<Dg> dgList = dgDao.findApp(objVo.getUserId(), DateUtils.getCurrDateStr(), checkOrNoCkeck);
						   long am1 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateStr() + " " + amst + ":00", DateUtils.getCurrDateTimeStr());
						if (dgList == null || dgList.size() <= 0) {
							// 第一次打卡验证

							if (am1 > 0) {// 超过早上规定时间为迟到，不可打卡
								msg = "当前已过规定打卡时间，请进行补卡流程！（上班打卡时间：" + amst + "之前）";
							} else {
								add2Dk(objVo, "", checkOrNoCkeck);
								msg = "打卡成功";
							}
						} else {
							msg = "已签到";
						}
					} else if (checkOrNoCkeck.equals("2")) {
						List<Dg> dgList = dgDao.findApp(objVo.getUserId(), DateUtils.getCurrDateStr(), checkOrNoCkeck);
						 long am2 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateTimeStr(), DateUtils.getCurrDateStr() + " " + amet + ":00");
						if (dgList == null || dgList.size() <= 0) {
							// 可能是下班时间验证
							if (am2 > 0) {// 未到下班时间不可打卡
								msg = "未在规定打卡时间，不可打卡！（下班打卡时间：" + amet + "之后）";
							} else {
								add2Dk(objVo, "", checkOrNoCkeck);
								msg = "打卡成功";
							}
						} else {
							msg = "已签退";
						}
					}else {
						msg="未到打卡时间";
					}
				} else {
					if (checkOrNoCkeck.equals("3")) {
						List<Dg> dgList = dgDao.findPmApp(objVo.getUserId(), DateUtils.getCurrDateStr() + " 12:00:00",
								checkOrNoCkeck);
						if (dgList == null || dgList.size() <= 0) {
							  long pm1 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateStr() + " " + pmst + ":00", DateUtils.getCurrDateTimeStr());

							if (pm1 > 0) {// 超过下午规定时间为迟到，不可打卡
								msg = "当前已过规定打卡时间，请进行补卡流程！（上班打卡时间：" + pmst + "之前）";
							} else {
								add2Dk(objVo, "", checkOrNoCkeck);
								msg = "打卡成功";
							}
						} else {
							msg = "已签到";
						}
					} else if (checkOrNoCkeck.equals("4")) {
						List<Dg> dgList = dgDao.findPmApp(objVo.getUserId(), DateUtils.getCurrDateStr() + " 12:00:00",
								checkOrNoCkeck);
						if (dgList == null || dgList.size() <= 0) {
							long pm2 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateTimeStr(), DateUtils.getCurrDateStr() + " " + pmet + ":00");
							if (pm2 > 0) {// 超过下午规定时间为迟到，不可打卡
								msg = "当前已过规定打卡时间，请进行补卡流程！（上班打卡时间：" + pmet + "之前）";
							} else {
								add2Dk(objVo, "", checkOrNoCkeck);
								msg = "打卡成功";
							}
						} else {
							msg = "已签退";
						}
					}else {
						msg="已经过了打卡时间";
					}
				}
			}
		}
		return msg;
	}

	@Override
	public List<kqshrUser> kqshrUserList() throws GlobalException {
		List<kqshrUser> outList = new ArrayList<>();
		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_KQ_KQSHR);
		for (AccountVo po : accountList) {
			kqshrUser vo = new kqshrUser();
			vo.setId(po.getId());
			vo.setName(po.getUserVo().getName());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public boolean saveDgEdit(AppDgEdit appDgEdit, ObjVo objVo) throws GlobalException {
		try {
			if (StrUtils.isNotBlankOrNull(appDgEdit.getId())) {
				update(appDgEdit, objVo);
			} else {
				add(appDgEdit, objVo);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void add(AppDgEdit v, ObjVo objVo) {
		Dg po = new Dg();
		if (StrUtils.isBlankOrNull(v.getDate())) {
			po.setDate(v.getDate());
		} else {
			po.setDate(DateUtils.getCurrDateTimeStr());
		}
		po.setContent(v.getContent());
		po.setRemark(v.getRemark());
		po.setCurTime(DateUtils.getCurrDateTimeStr());
		po.setDeptId(objVo.getDepId());
		po.setDeptName(objVo.getOrgName());
		po.setUserId(objVo.getUserId());
		po.setUserName(objVo.getName());
		po.setType(v.getType());
		po.setAuditId(v.getAuditId());
		po.setAuditName(v.getAuditName());
		po.setLatAndLng(v.getLatAndLng());
		dgDao.add(po);
		v.setId(po.getId());
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setStatus(Dg.ST_1);
			Progress progress = progressDao.add(po.getId(), EunmTask.BK_SH.getStatus(), null, null, po.getAuditId(), po.getAuditName());
			po.setProgress(progress);
			progressLogDao.add(po.getId(), po.getId(), EunmTask.BK_SQ.getStatus(), v.getIsCommit(), po.getRemark(),
					po.getDeptId(), po.getDeptName(), po.getUserId(), po.getUserName());
		} else {
			po.setStatus(Dg.ST_0);
		}
		dgDao.update(po);

	}

	private void update(AppDgEdit v, ObjVo objVo) {
		Dg po = dgDao.findById(v.getId());
		po.setDate(v.getDate());
		po.setContent(v.getContent());
		po.setCurTime(DateUtils.getCurrDateTimeStr());
		po.setAuditId(v.getAuditId());
		po.setAuditName(v.getAuditName());
		po.setLatAndLng(v.getLatAndLng());
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setStatus(Dg.ST_1);
			Progress progress = progressDao.add(po.getId(), EunmTask.BK_SH.getStatus(), null, null, po.getAuditId(), po.getAuditName());
			po.setProgress(progress);
			progressLogDao.add(po.getId(), po.getId(), EunmTask.BK_SQ.getStatus(), v.getIsCommit(), po.getRemark(),
					po.getDeptId(), po.getDeptName(), po.getUserId(), po.getUserName());
		}
		dgDao.update(po);

	}

	@Override
	public List<String> todayDk(ObjVo objVo) throws GlobalException {
		String date = DateUtils.getCurrDateStr() + " 00:00:00";
		StringBuffer hql = new StringBuffer("FROM " + dgDao.getEntityName(Dg.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  (user_id = '" + objVo.getRealUserId() + "' or  user_id = '" + objVo.getUserId() + "') ");
		hql.append(" AND type = '打卡'");
		hql.append(" AND date > '" + date + "'");
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = dgDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Dg> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<String> outList = new ArrayList<>();
		outList.add("false");
		outList.add("false");
		outList.add("false");
		outList.add("false");
		if (null != list) {
			for (Dg po : list) {
				if(po.getCheckOrNoCheck()!= null) {
					switch (po.getCheckOrNoCheck()) {
					case "1":
						outList.set(0, "true");
						break;
					case "2":
						outList.set(1, "true");
						break;
					case "3":
						outList.set(2, "true");
						break;
					case "4":
						outList.set(3, "true");
						break;
					}
				}
				
			}
		}
		return outList;
	}

	@Override
	public List<AppDgEdit> dgTypeList(ObjVo objVo, String type) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + dgDao.getEntityName(Dg.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  (user_id = '" + objVo.getRealUserId() + "' or  user_id = '" + objVo.getUserId() + "') ");
		if (StrUtils.isNotBlankOrNull(type)) {
			hql.append(" AND type ='" + type + "'");
		}
		hql.append(" ORDER BY last_upd_time DESC");
		Query query = dgDao.query(hql.toString());
		int page = objVo.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Dg> list = query.setFirstResult((page - 1) * objVo.getRows()).setMaxResults(objVo.getRows())
				.getResultList();
		List<AppDgEdit> outList = new ArrayList<>();
		if (null != list) {
			for (Dg po : list) {
				AppDgEdit vo = new AppDgEdit();
				vo.setId(po.getId());
				vo.setUserName(po.getUserName());
				vo.setUserId(po.getUserId());
				vo.setDate(po.getDate());
				vo.setContent(po.getContent());
				vo.setRemark(po.getRemark());
				vo.setAuditId(po.getAuditId());
				vo.setAuditName(po.getAuditName());
				vo.setType(po.getType());
				vo.setDeptName(po.getDeptName());
				vo.setStatus(po.getStatus());
				vo.setLatAndLng(po.getLatAndLng());
				vo.setAuditMsg(po.getAuditMsg());
				outList.add(vo);
			}
		}
		return outList;
	}

	@Override
	public boolean updateAudit(String id, String AuditMsg, String isCommit, ObjVo objVo) throws GlobalException {
		try {
			Dg po = dgDao.findById(id);
			po.setAuditDate(DateUtils.getCurrDateTimeStr());
			po.setAuditMsg(AuditMsg);
			if (null != isCommit && isCommit.equals(Constants.PASS_Y)) {
				po.setStatus(Dg.ST_2);
				po.setAuditResult("通过");
				// 补卡通过审批后更新统计表
				dgTjDao.updDgTj(po.getUserId(), po.getDate());
				progressDao.update(po.getProgress().getId(), "finish", objVo.getUserId(), objVo.getName());
			} else if (null != isCommit && isCommit.equals(Constants.PASS_N)) {
				po.setAuditResult("不通过");
				po.setStatus(Dg.ST_0);
				progressDao.update(po.getProgress().getId(), EunmTask.BK_SQ.getStatus(), objVo.getUserId(), objVo.getName());
			}
			dgDao.update(po);
			progressLogDao.addApp(po.getId(), po.getId(), EunmTask.BK_SH.getStatus(), isCommit, AuditMsg, objVo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
