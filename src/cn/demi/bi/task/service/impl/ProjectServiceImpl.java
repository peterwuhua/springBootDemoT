package cn.demi.bi.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bi.task.service.IProjectService;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.CustMaterial;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.po.CustWork;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustPointVo;
import cn.demi.bus.pjt.vo.CustRoomVo;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.InvoiceVo;
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SchemeVo;

/**
 * 任务统计 业务逻辑层
 */
@Service("bi.projectService")
public class ProjectServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectService {

	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private IProjectFbDao projectFbDao;
	@Autowired
	private IImDao imDao;
	@Autowired
	private ICustWorkDao custWorkDao;
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private ICustMaterialDao custMaterialDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		// 费用信息
		Invoice invoice = po.getInvoice();
		InvoiceVo invoiceVo = new InvoiceVo();
		if (null != invoice) {
			invoiceVo = invoiceVo.toVo(invoice);
		}
		vo.setInvoiceVo(invoiceVo);
		vo.setRoomList(geRoomList(vo.getId()));
		vo.setMaterialList(geMaterialList(vo.getId()));
		vo.setWorkList(geWorkList(vo.getId()));
		vo.setPotList(getPointList(vo.getId()));
		// 方案信息
		vo.setSchemeList(getSchList4Show(vo));
		// 分包信息
		List<ProjectFb> fbList = projectFbDao.listByProjectId(po.getId());
		if (null != fbList) {
			List<ProjectFbVo> fbVoList = new ArrayList<>();
			for (ProjectFb fb : fbList) {
				ProjectFbVo fbVo = new ProjectFbVo();
				fbVo = fbVo.toVo(fb);
				fbVoList.add(fbVo);
			}
			vo.setFbList(fbVoList);
		}
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

	// 装配 方案详情 查看
	public List<SchemeVo> getSchList4Show(ProjectVo vo) throws GlobalException {
		List<SchemeVo> schVoList = new ArrayList<SchemeVo>();
		List<Scheme> schList = schemeDao.listByPjId(vo.getId());
		for (Scheme scheme : schList) {
			SchemeVo schVo = new SchemeVo();
			schVo = schVo.toVo(scheme);
			schVo.setPointList(getSchPointList(scheme.getId()));
			schVoList.add(schVo);
		}
		return schVoList;
	}

	public List<SchemePointVo> getSchPointList(String schemeId) throws GlobalException {
		List<SchemePointVo> spVoList = new ArrayList<>();
		List<SchemePoint> spList = schemePointDao.listBySchId(schemeId);
		int sort = 1;
		for (SchemePoint sp : spList) {
			SchemePointVo spVo = new SchemePointVo();
			spVo = spVo.toVo(sp);
			spVo.setImId(imDao.findByBusId(sp.getId()));
			spVo.setSort(sort);
			spVoList.add(spVo);
			sort++;
		}
		return spVoList;
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
		if (!getCurrent().getRoleNames().contains("总经理") && !getCurrent().getRoleNames().contains("副总") && !getCurrent().getRoleNames().contains("管理员")) {
			pageResult.addCondition(new QueryCondition("orgId='" + getCurrent().getOrgId() + "' "));
		}
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ProjectVo v) throws GlobalException {
		List<QueryCondition> QueryConditionList = new ArrayList<QueryCondition>();

		if (!StrUtils.isBlankOrNull(v.getNo())) {
			QueryConditionList.add(new QueryCondition("no", QueryCondition.AK, v.getNo()));
		}
		if (null != v.getCustVo() && !StrUtils.isBlankOrNull(v.getCustVo().getCustName())) {
			QueryConditionList.add(new QueryCondition("cust.custName", QueryCondition.AK, v.getCustVo().getCustName()));
		}
		if (!StrUtils.isBlankOrNull(v.getTaskType())) {
			QueryConditionList.add(new QueryCondition("taskType", QueryCondition.AK, v.getTaskType()));
		}
		if (!StrUtils.isBlankOrNull(v.getStartDate())) {
			QueryConditionList.add(new QueryCondition("rdate>='" + v.getStartDate() + " 00:00:00'"));
		}
		if (!StrUtils.isBlankOrNull(v.getEndDate())) {
			QueryConditionList.add(new QueryCondition("rdate<='" + v.getEndDate() + " 23:59:59'"));
		}
		if (!StrUtils.isBlankOrNull(v.getSampName())) {
			QueryConditionList.add(new QueryCondition("sampName", QueryCondition.AK, v.getSampName()));
		}
		if (!StrUtils.isBlankOrNull(v.getSampType())) {
			QueryConditionList.add(new QueryCondition("sampType", QueryCondition.AK, v.getSampType()));
		}
		if (!StrUtils.isBlankOrNull(v.getUserName())) {
			QueryConditionList.add(new QueryCondition("userName", QueryCondition.AK, v.getUserName()));
		}
		QueryConditionList.add(new QueryCondition("status", QueryCondition.NE, EunmTask.PROJECT_LX.getStatus()));
		return QueryConditionList;
	}
}
