package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.ISurveyDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.pjt.po.CustMaterial;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.po.CustWork;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.Survey;
import cn.demi.bus.pjt.service.IProjectTkService;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustPointVo;
import cn.demi.bus.pjt.vo.CustRoomVo;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SurveyVo;
import cn.demi.bus.pjt.vo.TypeExVo;

@Service("bus.schemeTkService")
public class ProjectTkServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectTkService {
	@Autowired
	private ICustDao custDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ISurveyDao surveyDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICustWorkDao custWorkDao;
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private ICustMaterialDao custMaterialDao;
	
	@Autowired
	private IImDao imDao;

	@Override
	public SurveyVo findByProjectId(String projectId) throws GlobalException {
		Survey su = surveyDao.findByProjectId(projectId);
		SurveyVo suVo = new SurveyVo();
		suVo = suVo.toVo(su);
		if (null == su) {
			suVo = new SurveyVo();
			Project project = projectDao.findById(projectId);
			ProjectVo projectVo = new ProjectVo();
			projectVo = projectVo.toVo(project);
			suVo.setProjectVo(projectVo);
			suVo.setUserId(project.getTkUserId());
			suVo.setUserName(project.getTkUserName());
			suVo.setTdate(DateUtils.getCurrDateStr());
			suVo.setBuildUnit(project.getCust().getCustName());
		}
		return suVo;
	}

	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		vo.setRoomList(geRoomList(vo.getId()));
		vo.setMaterialList(geMaterialList(vo.getId()));
		vo.setWorkList(geWorkList(vo.getId()));
		vo.setPotList(getPointList(vo.getId()));
		return vo;
	}

	// 获取车间集合
	public List<CustRoomVo> geRoomList(String pjId) throws GlobalException {
		List<CustRoom> crList = custRoomDao.listByProjectId(pjId);
		List<CustRoomVo> crVoList = new ArrayList<CustRoomVo>();
		for (CustRoom room : crList) {
			CustRoomVo roomVo = new CustRoomVo();
			roomVo = roomVo.toVo(room);
			crVoList.add(roomVo);
		}
		return crVoList;
	}

	// 获取采样点集合
	public List<CustPointVo> getPointList(String pjId) throws GlobalException {
		List<CustPoint> cpList = custPointDao.listByProjectId(pjId);
		List<CustPointVo> cpVoList = new ArrayList<CustPointVo>();
		if (cpList != null) {
			for (CustPoint cp : cpList) {
				CustPointVo cpVo = new CustPointVo();
				cpVo = cpVo.toVo(cp);
				cpVo.setImId(imDao.findByBusId(cp.getId()));
				cpVoList.add(cpVo);
			}
		}
		return cpVoList;
	}

	// 获取写实记录集合
	public List<CustWorkVo> geWorkList(String pjId) throws GlobalException {
		List<CustWork> wkList = custWorkDao.listByProjectId(pjId);
		List<CustWorkVo> wkVoList = new ArrayList<CustWorkVo>();
		for (CustWork wk : wkList) {
			CustWorkVo wkVo = new CustWorkVo();
			wkVo = wkVo.toVo(wk);
			wkVoList.add(wkVo);
		}
		return wkVoList;
	}

	// 获取物料集合
	public List<CustMaterialVo> geMaterialList(String pjId) throws GlobalException {
		List<CustMaterial> list = custMaterialDao.listByProjectId(pjId);
		List<CustMaterialVo> voList = new ArrayList<CustMaterialVo>();
		for (CustMaterial po : list) {
			CustMaterialVo vo = new CustMaterialVo();
			vo = vo.toVo(po);
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		vo.setRoomList(geRoomList(vo.getId()));
		vo.setMaterialList(geMaterialList(vo.getId()));
		vo.setWorkList(geWorkList(vo.getId()));
		vo.setPotList(getPointList(vo.getId()));
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project p = projectDao.findById(v.getId());
		p.setTkDate(v.getTkDate());
		p.setTkMsg(v.getTkMsg());
		p.setTkUserId(v.getTkUserId());
		p.setTkUserName(v.getTkUserName());
		Cust cust = p.getCust();
		if (null != v.getCustVo()) {
			cust.setTkJdr(v.getCustVo().getTkJdr());
			custDao.update(cust);
		}

		List<CustRoomVo> roomList = v.getRoomList();
		if (roomList != null) {
			for (CustRoomVo roomVo : roomList) {
				CustRoom room = custRoomDao.findById(roomVo.getId());
				room.setProjectId(p.getId());
				room.setCust(cust);
				room.setName(roomVo.getName());
				room.setWorks(roomVo.getWorks());
				room.setWorkType(roomVo.getWorkType());
				room.setWorkWay(roomVo.getWorkWay());
				room.setSort(roomVo.getSort());
				custRoomDao.update(room);
			}
		}
		List<CustPointVo> potList = v.getPotList();
		if (potList != null) {
			for (CustPointVo potVo : potList) {
				CustPoint pot = custPointDao.findById(potVo.getId());
				pot.setName(potVo.getName());
				pot.setWorkTal(potVo.getWorkTal());
				pot.setWorkNum(potVo.getWorkNum());
				pot.setProductName(potVo.getProductName());
				pot.setProductTal(potVo.getProductTal());
				pot.setProductNum(potVo.getProductNum());
				pot.setItemIds(potVo.getItemIds());
				pot.setItemNames(potVo.getItemNames());
				pot.setWorkHours(potVo.getWorkHours());
				pot.setFhName(potVo.getFhName());
				pot.setFhTal(potVo.getFhTal());
				pot.setFhNum(potVo.getFhNum());
				pot.setOthers(potVo.getOthers());
				pot.setSort(potVo.getSort());
				custPointDao.update(pot);
				// 更新项目方法关系
				imDao.uptIm(pot.getId(), potVo.getImId());
			}
		}
		List<CustMaterialVo> mtList = v.getMaterialList();
		if (mtList != null) {
			for (CustMaterialVo mtVo : mtList) {
				CustMaterial mt = custMaterialDao.findById(mtVo.getId());
				mt.setType(mtVo.getType());
				mt.setName(mtVo.getName());
				mt.setCts(mtVo.getCts());
				mt.setXz(mtVo.getXz());
				mt.setYl(mtVo.getYl());
				mt.setUseType(mtVo.getUseType());
				mt.setSort(mtVo.getSort());
				custMaterialDao.update(mt);
			}
		}
		List<CustWorkVo> workList = v.getWorkList();
		if (workList != null) {
			for (CustWorkVo workVo : workList) {
				CustWork work = custWorkDao.findById(workVo.getId());
				work.setUser(workVo.getUser());
				work.setAge(workVo.getAge());
				work.setStartTime(workVo.getStartTime());
				work.setEndTime(workVo.getEndTime());
				work.setWorks(workVo.getWorks());
				work.setRemark(workVo.getRemark());
				work.setWorkNum(work.getPoint().getWorkHours() + "");
				work.setItemIds(work.getPoint().getItemIds());
				work.setItemNames(work.getPoint().getItemNames());
				custWorkDao.update(work);
			}
		}
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			// 更新流程
			Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.PROJECT_BZ.getStatus(), null, null, null, null);
			p.setStatus(pg.getStatus());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.PROJECT_TK.getStatus(), EunmTask.PASS_Y, v.getTkMsg());
		}
		projectDao.update(p);
	}

	@Override
	public void update4Survey(SurveyVo v) throws GlobalException {
		Survey su = null;
		if (StrUtils.isBlankOrNull(v.getId())) {
			su = new Survey();
			// 更新指派信息
			su.toPo(v, su);
			su.setProject(projectDao.findById(v.getProjectVo().getId()));
			surveyDao.add(su);
		} else {
			su = surveyDao.findById(v.getId());
			// 更新指派信息
			su.toPo(v, su);
			surveyDao.update(su);
		}
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			// 更新流程
			Project p = su.getProject();
			Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.PROJECT_BZ.getStatus(), null, null, null, null);
			p.setStatus(pg.getStatus());
			p.setTkDate(DateUtils.getCurrDateTimeStr());
			p.setTkUserId(su.getUserId());
			p.setTkUserName(su.getUserName());
			projectDao.update(p);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.PROJECT_TK.getStatus(), EunmTask.PASS_Y, v.getRemark());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("tkUserId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' "));
		pageResult.addCondition(new QueryCondition("status like '" + EunmTask.PROJECT_TK.getStatus() + "'"));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + projectDao.getEntityName(Project.class) + " t,"
				+ projectDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmTask.PROJECT_TK.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = projectDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Project> pjList = projectDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(pjList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<Project> list) throws GlobalException {
		List tempList = new ArrayList();
		for (Project p : list) {
			Map<String, Object> map = po2map(p);
			if (null != map.get("tkDate")) {
				String tdate = String.valueOf(map.get("tkDate"));
				long n = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), tdate);
				if (n < 0) {
					map.put("color", "red");
				} else if (n < 3) {
					map.put("color", "blue");
				}
			}
			tempList.add(map);
		}
		return tempList;
	}

	@Override
	public void update4Zy(SurveyVo v) throws GlobalException {
		Project pt = projectDao.findById(v.getProjectVo().getId());
		pt.setTkUserId(v.getProjectVo().getTkUserId());
		pt.setTkUserName(v.getProjectVo().getTkUserName());
		projectDao.update(pt);
		Progress pg = pt.getProgress();
		pg.setUserId(pt.getTkUserId());
		pg.setUserName(pt.getTkUserName());
		progressDao.update(pg);
		progressLogDao.add(pt.getId(), pt.getId(), EunmTask.PROJECT_TK.getStatus(), EunmTask.PASS_S,
				"任务转移 - 接收人：" + pt.getTkUserName() + " - 原因：" + v.getRemark());
	}

	@Override
	public void excutSchedule() throws GlobalException {
		String hql = "FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel='" + Po.N + "' AND status='" + EunmTask.PROJECT_TK.getName() + "'";
		List<Project> schemeList = projectDao.list(hql);
		if (null != schemeList) {
			for (Project project : schemeList) {
				try {
					long d = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), project.getTkDate());
					if (d == 0) {// 当天
						String msg = "【系统提示】项目 编号为" + project.getNo() + "的任务今天到期，请及时处理！";
						List<Account> atList = accountDao.listByIds(project.getTkUserId());
						if (null != atList) {
							for (Account at : atList) {
								OpenIM.pushMsg(Constants.SUADMIN_LOGIN, at.getLoginName(), msg);
							}
						}
					} else if (d < 0) {
						String msg = "【系统提示】项目 编号为" + project.getNo() + "的任务已超期" + (-d) + "天，请及时处理！";
						List<Account> atList = accountDao.listByIds(project.getTkUserId());
						if (null != atList) {
							for (Account at : atList) {
								OpenIM.pushMsg(Constants.SUADMIN_LOGIN, at.getLoginName(), msg);
							}
						}
						OpenIM.pushMsg(Constants.SUADMIN_LOGIN, accountDao.findById(project.getZpUserId()).getLoginName(), msg);
					}
				} catch (Exception e) {
					log.error("定时任务：踏勘任务【" + project.getNo() + "】定时检测异常！", e);
				}
			}
		}
	}

	@Override
	public ProjectVo findById4Word(String id, String type) throws GlobalException {
		
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		vo.setRoomList(geRoomList(vo.getId()));
		
		if (type.equals(Project.WLD)) {
			vo.setMaterialTypeList(geMaterialList4Word(vo.getId()));
		} else if (type.equals(Project.DWB)) {
			
		}
		vo.setWorkList(geWorkList(vo.getId()));
		vo.setPotList(getPointList(vo.getId()));
		return vo;
	}

	private List<TypeExVo> geMaterialList4Word(String pjId) throws GlobalException {
		List<CustMaterial> clist = custMaterialDao.listTypeByPjtId(pjId);
		List<TypeExVo> typeList = new ArrayList<TypeExVo>();
		for (int i = 0; i < clist.size(); i++) {
			TypeExVo vo = new TypeExVo();
			String type = clist.get(i).getType();
			vo.setType(type);
			List<CustMaterial> list = custMaterialDao.listBypjtIdAndType(pjId, type);
			List<CustMaterialVo> mvos = new ArrayList<>();
			for (CustMaterial po : list) {
				CustMaterialVo cvo = new CustMaterialVo();
				cvo = cvo.toVo(po);
				mvos.add(cvo);
			}
			vo.setMaterialList(mvos);
			typeList.add(vo);
		}
		return typeList;
	}

	@Override
	public void update4Word(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setWlName(v.getWlName());
		po.setWlPath(v.getWlPath());
		po.setDwName(v.getDwName());
		po.setDwPath(v.getDwPath());
		po.setDcName(v.getDcName());
		po.setDcPath(v.getDcPath());
		projectDao.update(po);
	}

	@Override
	public CustWorkVo findById4DCB(String id) throws GlobalException {
		CustWork po = custWorkDao.findById(id);
		CustWorkVo vo =new CustWorkVo();
		vo =vo.toVo(po);
		return vo;
	}
}
