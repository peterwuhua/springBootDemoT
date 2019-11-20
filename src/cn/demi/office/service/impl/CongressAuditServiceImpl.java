package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.ICongressDao;
import cn.demi.office.po.Congress;
import cn.demi.office.service.ICongressAuditService;
import cn.demi.office.vo.CongressVo;

/**
 * 
 * @ClassName: CongressAuditServiceImpl
 * @Description:
 * @author: 吴华
 * @date: 2019年3月5日 下午6:10:55
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Service("office.congressAuditService")
public class CongressAuditServiceImpl extends BaseServiceImpl<CongressVo, Congress> implements ICongressAuditService {
	@Autowired
	private ICongressDao congressDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description: 未审批列表
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
	public GridVo gridData(GridVo gridVo, CongressVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			// 先根据状态排序desc,然后在根据申请日期排序desc
			pageResult.addOrder("supportDate", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition("fstatus = '" + Congress.ST_SH + "'");
		pageResult = congressDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Congress>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: gridDatad
	 * </p>
	 * <p>
	 * Description: 已经审批的列表
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#gridDatad(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, CongressVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			// 先根据状态排序desc,然后在根据申请日期排序desc
			pageResult.addOrder("supportDate", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("sumUserId", QueryCondition.AK, getCurrent().getAccountId()));
		pageResult = congressDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Congress>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * @Title: updateAuditSuccess @Description:
	 *         审批提交通过，更改状态为“未查阅”,审批提交不通过，更改状态“审批不通过” @param: @param
	 *         v @param: @throws GlobalException @return: void @throws
	 */
	@Override
	public void updateAuditInfo(CongressVo v) throws GlobalException {
		Congress po = congressDao.findById(v.getId());
		po.setSumRemark(v.getSumRemark());
		po.setSumDate(v.getSumDate());
		po.setSumUserName(v.getSumUserName());
		po.setSumUserId(v.getSumUserId());
		commit(po, v);
		congressDao.update(po);
	}

	public void commit(Congress p, CongressVo v) throws GlobalException {
		Progress pg=progressDao.findByBusId(p.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setFstatus(Congress.ST_TG);
			if(null!=pg) {
				pg=progressDao.update(pg.getId(), EunmTask.HW_CY.getStatus(),null,null,p.getUserIds(), p.getUsers());
			}
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			p.setFstatus(Congress.ST_JJ);
			if(null!=pg) {
				pg=progressDao.update(pg.getId(), EunmTask.HW_SQ.getStatus(),null,null,p.getSqrId(), p.getSqr());
			}
		}
		progressLogDao.add(p.getId(), p.getId(), EunmTask.HW_SH.getStatus(), v.getIsCommit(), p.getSumRemark());
	}

}
