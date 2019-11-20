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
import cn.core.framework.utils.StrUtils;
import cn.demi.office.dao.IQjglDao;
import cn.demi.office.po.Qjgl;
import cn.demi.office.service.IXjService;
import cn.demi.office.vo.QjglVo;

@Service("office.xjService")
public class XjServiceImpl extends BaseServiceImpl<QjglVo, Qjgl> implements IXjService {

	@Autowired
	private IQjglDao qjglDao;

	/**
	 * 
	 * <p>
	 * Title: updateStatus
	 * </p>
	 * <p>
	 * Description: 更新状态为已销假
	 * </p>
	 * 
	 * @param v
	 * @throws GlobalException
	 * @see cn.demi.office.service.IXjService#updateStatus(cn.demi.office.vo.QjglVo)
	 */
	@Override
	public void updateStatus(QjglVo v) throws GlobalException {
		Qjgl po = qjglDao.findById(v.getId());
		po.setFstatus(Constants.QJGL_STATUS_YXJ);
		po.setXjshow("1");//已销假
		qjglDao.update(po);
	}

	/**
	 * 
	 * @Title: gridData @Description: 未提交列表 @param: @param gridVo @param: @param
	 * v @param: @return @param: @throws GlobalException @return: GridVo @throws
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
		List<QueryCondition> queryList = new ArrayList<>();
		QueryCondition cond = new QueryCondition(" fstatus =  '"+Constants.QJGL_STATUS_SP_TG+"'"); 
		queryList.add(cond);
		pageResult.addConditionList(queryList);
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
	 * @Title: gridDatad @Description: 已提交列表 @param: @param gridVo @param: @param
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
		List<QueryCondition> queryList = new ArrayList<>();
		QueryCondition cond = new QueryCondition(" fstatus =  '"+Constants.QJGL_STATUS_YXJ+"' "); 
		queryList.add(cond);
		pageResult.addConditionList(queryList);
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
