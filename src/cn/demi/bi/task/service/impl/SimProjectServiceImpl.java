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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.task.service.ISimProjectService;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 任务统计 业务逻辑层
 */
@Service("bi.simProjectService")
public class SimProjectServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements ISimProjectService {

	@Autowired
	private IProjectDao projectDao;


	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		// 费用信息
//		Invoice invoice = po.getInvoice();
//		InvoiceVo invoiceVo = new InvoiceVo();
//		if (null != invoice) {
//			invoiceVo = invoiceVo.toVo(invoice);
//		}
//		vo.setInvoiceVo(invoiceVo);
//		vo.setRoomList(geRoomList(vo.getId()));
//		vo.setMaterialList(geMaterialList(vo.getId()));
//		vo.setWorkList(geWorkList(vo.getId()));
//		vo.setPotList(getPointList(vo.getId()));
//		// 方案信息
//		vo.setSchemeList(getSchList4Show(vo));
		// 分包信息
//		List<ProjectFb> fbList = projectFbDao.listByProjectId(po.getId());
//		if (null != fbList) {
//			List<ProjectFbVo> fbVoList = new ArrayList<>();
//			for (ProjectFb fb : fbList) {
//				ProjectFbVo fbVo = new ProjectFbVo();
//				fbVo = fbVo.toVo(fb);
//				fbVoList.add(fbVo);
//			}
//			vo.setFbList(fbVoList);
//		}
		return vo;
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
			if (getCurrent().getOrgName().contains("环境")) {
				pageResult.addCondition(" itemType like '%环境%' ");

			} else if (getCurrent().getOrgName().contains("职业卫生")) {
				pageResult.addCondition(" itemType like '安全%' or itemType like '职业卫生%' ");
			}
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
		if (!StrUtils.isBlankOrNull(v.getUserName())) {
			QueryConditionList.add(new QueryCondition("userName", QueryCondition.AK, v.getUserName()));
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
		if (!StrUtils.isBlankOrNull(v.getItemType())) {
			QueryConditionList.add(new QueryCondition("itemType", QueryCondition.AK, v.getItemType()));
		}
		return QueryConditionList;
	}
}
