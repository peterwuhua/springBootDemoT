package cn.demi.office.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.ICmDao;
import cn.demi.office.po.Cm;
import cn.demi.office.service.ICmService;
import cn.demi.office.vo.CmVo;

@Service("office.cmService")
public class CmServiceImpl extends BaseServiceImpl<CmVo, Cm> implements ICmService {
	@Autowired
	private ICmDao cmDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;

	@Override
	public CmVo findById(String id) throws GlobalException {
		Cm po = cmDao.findById(id);
		return po2Vo(po);
	}

	/**
	 * 生成编号
	 */
	public String createCode() {
		String flag = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(no) FROM " + cmDao.getEntityName(Cm.class) + " WHERE isDel=" + Po.N + " AND no like '" + flag + "%'";
		String ls = (String) cmDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "01";
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
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
	}

	@Override
	public void add(CmVo v) throws GlobalException {
		Cm p = vo2Po(v);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setDeptId(getCurrent().getDepId());
		p.setDeptName(getCurrent().getDepName());
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		p.setFstatus(Cm.ST_0);
		p.setCode(organizeData());
		cmDao.add(p);
		v.setId(p.getId());
		progressDao.add(p.getId(), EunmTask.GS_SQ.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg = progressDao.findByBusId(p.getId());
			progressDao.update(pg.getId(), EunmTask.GS_SH.getStatus(), null, null, p.getAuditId(), p.getAuditName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.GS_SQ.getStatus(), v.getIsCommit(), p.getRemark(), p.getOrgId(), p.getOrgName(), p.getUserId(),
					p.getUserName());
			p.setFstatus(Cm.ST_1);
			p.setStatus(pg.getStatus());
		}
		cmDao.update(p);
	}

	@Override
	public void update(CmVo v) throws GlobalException {
		Cm p = cmDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg = progressDao.findByBusId(p.getId());
			progressDao.update(pg.getId(), EunmTask.GS_SH.getStatus(), p.getAuditId(), p.getAuditName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.GS_SQ.getStatus(), v.getIsCommit(), p.getRemark(), p.getOrgId(), p.getOrgName(), p.getUserId(),
					p.getUserName());
			p.setFstatus(Cm.ST_1);
			p.setStatus(pg.getStatus());
		}
		cmDao.update(p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("fstatus", QueryCondition.EQ, Cm.ST_0));
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = cmDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("fstatus", QueryCondition.NE, Cm.ST_0));
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = cmDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	public String organizeData() throws GlobalException {
		// 生成编号 年月日+序号(四位小数)
		String date=DateUtils.format(new Date(), "yyyyMMdd");
		String jpql = "select max(code) from " + cmDao.getEntityName(Cm.class) + " where isDel='" + Po.N + "' and code like '"+date+"%' order by code desc";
		String ls = (String) cmDao.query(jpql).getSingleResult();
		String no = date;
		if (ls == null) {
			no += "0001";
		} else {
			ls = ls.replace(date, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort = 0;
			}
			sort++;
			if (sort < 10) {
				no += "000" + sort;
			} else if(sort < 100){
				no += "00" +sort;
			}else if(sort < 1000){
				no +="0" +sort;
			}  else {
				no += sort;
			}
		}
		return no;
	}
}
