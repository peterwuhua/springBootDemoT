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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.office.dao.ICongressDao;
import cn.demi.office.po.Congress;
import cn.demi.office.service.ICongressService;
import cn.demi.office.vo.CongressVo;

@Service("office.congressService")
public class CongressServiceImpl extends BaseServiceImpl<CongressVo,Congress> implements
		ICongressService {
	@Autowired
	private ICongressDao congressDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public CongressVo findById(String id) throws GlobalException {
		Congress po;
		try {
			po = congressDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	
	@Override
	public void add(CongressVo v) throws GlobalException {
		Congress p = vo2Po(v);
		p.setFstatus(Congress.ST_BC);
		congressDao.add(p);
		v.setId(p.getId());
		commint(v, p);
	}
	@Override
	public void update(CongressVo v) throws GlobalException {
		Congress  p = congressDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setFstatus(Congress.ST_BC);
		congressDao.update(p);
		commint(v, p);
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<Congress> list=congressDao.listByIds(ids);
		if(null!=list) {
			for (Congress congress : list) {
				progressDao.deleteByBusId(congress.getId());
				congressDao.delete(congress);
			}
		}
	}
	public void commint(CongressVo v,Congress p) {
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			progressDao.add(p.getId(), EunmTask.HW_SH.getStatus(),null,null, null, null);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.HW_SQ.getStatus(), v.getIsCommit(), p.getQtRemark());
			p.setStatus(EunmTask.HW_SH.getStatus());
			p.setFstatus(Congress.ST_SH);
			congressDao.update(p);
		}
	}
	/**
	 * <p>Title: gridData</p>   
	 * <p>Description: 会务申请列表</p>   
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.service.BaseServiceImpl#gridData(cn.core.framework.common.page.GridVo, cn.core.framework.common.vo.Vo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CongressVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			//先根据状态排序desc,然后在根据申请日期排序desc
			pageResult.addOrder("supportDate", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		
		pageResult = congressDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Congress>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	public List<QueryCondition> toQueryList(CongressVo v) throws GlobalException{
		List<QueryCondition> queryList = new ArrayList<>();
		QueryCondition qc = new QueryCondition(" sqrId = '" +getCurrent().getAccountId()+"'");
		queryList.add(qc);
		return queryList;
	}
	
	
}
