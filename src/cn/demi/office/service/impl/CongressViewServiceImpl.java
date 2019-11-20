package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
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
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.ICongressDao;
import cn.demi.office.po.Congress;
import cn.demi.office.service.ICongressViewService;
import cn.demi.office.vo.CongressVo;

/**
 * 
 * @ClassName: CongressViewServiceImpl
 * @Description:
 * @author: 吴华
 * @date: 2019年3月5日 下午6:10:55
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Service("office.congressViewService")
public class CongressViewServiceImpl extends BaseServiceImpl<CongressVo, Congress> implements ICongressViewService {
	@Autowired
	ICongressDao congressDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;

	/**
	 * 
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
		pageResult.addCondition("fstatus = '" + Congress.ST_TG + "'");
		pageResult.addCondition("userIds like '%" + getCurrent().getAccountId() + "%' or zjrId like '%"+getCurrent().getAccountId()+"%' or sqrId like '%"+getCurrent().getAccountId()+"%'");
		pageResult = congressDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Congress>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 *  更新 已查阅人
	 */
	@Override
	public void updateStatus(CongressVo vo) throws GlobalException {
		Congress po = congressDao.findById(vo.getId());
		if(po.getViewId()==null) {
			po.setViewId(getCurrent().getAccountId());
			po.setViewr(getCurrent().getUserName());
		}else {
			po.setViewId(po.getViewId()+","+getCurrent().getAccountId());
			po.setViewr(po.getViewr()+","+getCurrent().getUserName());
		}
		congressDao.update(po);
		Progress pg=progressDao.findByBusId(po.getId());
		String userId[]=pg.getUserId().split(",");
		String userName[]=pg.getUserName().split(",");
		List<String> newIds=new LinkedList<String>();
		List<String> newNames=new LinkedList<String>();
		int n=0;
		for (String id : userId) {
			if(id.trim().length()>0 && !id.equals(getCurrent().getAccountId())) {
				newIds.add(id);
				newNames.add(userName[n]);
			}
			n++;
		}
		if(newIds.size()>0) {
			progressDao.update(pg.getId(), EunmTask.HW_CY.getStatus(), String.join(",", newIds), String.join(",", newNames));
			progressLogDao.add(po.getId(), po.getId(), EunmTask.HW_CY.getStatus(), EunmTask.PASS_Y,"会务内容查看", getCurrent().getDepId(),getCurrent().getDepName(),getCurrent().getAccountId(),getCurrent().getUserName());
		}else {
			progressDao.delete(pg);
		}
	}

	public List<QueryCondition> toQueryList(CongressVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<>();
		QueryCondition qc = new QueryCondition(" userIds like '%" + getCurrent().getAccountId() + "%'");
		queryList.add(qc);
		return queryList; 
	}

}
