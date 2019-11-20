package cn.demi.office.service.impl;

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
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.po.Category;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;
import cn.demi.office.service.IDtService;
import cn.demi.office.vo.DtVo;

@Service("office.dtService")
public class DtServiceImpl extends BaseServiceImpl<DtVo,Dt> implements
		IDtService {
	@Autowired
	private IDtDao dtDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired 
	private ICategoryDao categoryDao;
	@Override
	public DtVo findById(String id) throws GlobalException {
		Dt po = dtDao.findById(id);
		DtVo vo=po2Vo(po);
		return vo;
	}
	@Override
	public void add(DtVo v) throws GlobalException {
		Dt p = vo2Po(v);
		if(StrUtils.isBlankOrNull(p.getCode())) {
			p.setCode(createCode());
		}
		if(v.getFileName()!=null&&v.getFileName().equals("online")) {
			p.setFileName(v.getFilePath().substring(v.getFilePath().lastIndexOf("/")+1));
			p.setFilePath(v.getFilePath());
			p.setType(p.getFileName().substring(p.getFileName().lastIndexOf(".")+1));
		}else if(!StrUtils.isBlankOrNull(v.getFileName())) {
			p.setFileName(v.getFileName());
			p.setFilePath(v.getFilePath());
		}
		if(!StrUtils.isBlankOrNull(v.getType())) {
			p.setType(v.getType());
		}
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setDeptId(getCurrent().getDepId());
		p.setDeptName(getCurrent().getDepName());
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		p.setStatus(EunmTask.DT_SQ.getStatus());
		Category c=categoryDao.findById(v.getCategoryVo().getId());
		p.setCategory(c);
		if(null != p.getCategory() && !StrUtils.isBlankOrNull(p.getTitle()) && !StrUtils.isBlankOrNull(p.getCategory().getPath())){
			p.setPath(p.getCategory().getPath()+"/"+p.getTitle());
		}
		dtDao.add(p);
		v.setId(p.getId());
		progressDao.add(p.getId(), EunmTask.DT_SQ.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		commit(p,v);
	}
	public void commit(Dt p,DtVo v) {
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg=progressDao.findByBusId(p.getId());
			progressDao.update(pg.getId(), EunmTask.DT_QF.getStatus(),null,null,p.getAuditId(), p.getAuditName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.DT_SQ.getStatus(), v.getIsCommit(), p.getRemark(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
			p.setStatus(pg.getStatus());
		}
		dtDao.update(p);
	}
	@Override
	public void update(DtVo v) throws GlobalException {
		Dt p = dtDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(v.getFileName()!=null&&v.getFileName().equals("online")) {
			p.setFileName(v.getFilePath().substring(v.getFilePath().lastIndexOf("/")+1));
			p.setFilePath(v.getFilePath());
			p.setType(p.getFileName().substring(p.getFileName().lastIndexOf(".")+1));
		}else if(!StrUtils.isBlankOrNull(v.getFileName())) {
			p.setFileName(v.getFileName());
			p.setFilePath(v.getFilePath());
		}
		if(!StrUtils.isBlankOrNull(v.getType())) {
			p.setType(v.getType());
		}
		Category c=categoryDao.findById(v.getCategoryVo().getId());
		p.setCategory(c);
		if(null != p.getCategory() && !StrUtils.isBlankOrNull(p.getTitle()) && !StrUtils.isBlankOrNull(p.getCategory().getPath())){
			p.setPath(p.getCategory().getPath()+"/"+p.getTitle());
		}
		dtDao.update(p);
		commit(p, v);
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
		String hql="SELECT max(code) FROM "+dtDao.getEntityName(Dt.class)+" WHERE isDel="+Po.N+" AND code like '"+flag+"%'";
		String ls=(String) dtDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="01";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ,EunmTask.DT_SQ.getStatus()));
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dtDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dt>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.NE,EunmTask.DT_SQ.getStatus()));
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dtDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dt>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
