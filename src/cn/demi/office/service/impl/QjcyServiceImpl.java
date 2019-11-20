package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.dao.IQjglDao;
import cn.demi.office.po.Qjgl;
import cn.demi.office.service.IQjcyService;
import cn.demi.office.vo.QjglVo;

@Service("office.qjcyService")
public class QjcyServiceImpl extends BaseServiceImpl<QjglVo, Qjgl> implements IQjcyService {
	@Autowired
	private IQjglDao qjglDao;

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description: 当天之后审批通过的请假记录
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, QjglVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			//先根据状态排序desc,然后在根据编号排序desc
			pageResult.addOrder("no", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> condList = new ArrayList<>();
		QueryCondition cond = new QueryCondition(" fstatus = '" + Constants.QJGL_STATUS_SP_TG + "' or fstatus = '" + Constants.QJGL_STATUS_YXJ
				+ "'  ");
		condList.add(cond);
		QueryCondition cond1 = new QueryCondition(" beginTime >= '" + DateUtils.getCurrDateStr() + " 00:00:00' ");
		condList.add(cond1);
		pageResult.addConditionList(condList);
		pageResult.addOrder("supportDate", "desc");
		pageResult = qjglDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Qjgl>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * @Title: gridDatad @Description: 历史审批通过记录 @param: @param gridVo @param: @param
	 * v @param: @return @param: @throws GlobalException @return: GridVo @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, QjglVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			//先根据状态排序desc,然后在根据编号排序desc
			pageResult.addOrder("no", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> condList = new ArrayList<>();
		QueryCondition cond = new QueryCondition(" fstatus = '" + Constants.QJGL_STATUS_SP_TG + "' or fstatus = '" + Constants.QJGL_STATUS_YXJ + "' ");
		condList.add(cond);
		pageResult.addConditionList(condList);
		pageResult.addOrder("supportDate", "desc");
		pageResult = qjglDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Qjgl>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

}
