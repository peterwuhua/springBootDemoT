package cn.demi.bus.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.bus.sample.dao.ISampUsedDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampUsed;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISampCkService;
import cn.demi.bus.sample.vo.SampUsedVo;

@Service("bus.sampCkService")
public class SampCkServiceImpl extends BaseServiceImpl<SampUsedVo,SampUsed> implements
		ISampCkService {
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ISampUsedDao sampUsedDao;
	@Autowired
	private IAccountDao accountDao;
	@Override
	public SampUsedVo findById(String id) throws GlobalException {
		SampUsed po = sampUsedDao.findById(id);
		SampUsedVo vo=po2Vo(po);
		return vo;
	}
	@Override
	public void update(SampUsedVo v) throws GlobalException {
		SampUsed po=new SampUsed();
		if(!StrUtils.isBlankOrNull(v.getId())) {
			po = sampUsedDao.findById(v.getId());
		}
		Sampling samp=samplingDao.findById(v.getSamplingVo().getId());
		po.setSampling(samp);
		po.setTask(samp.getTask());
		po.setCust(samp.getCust());
		po.setSampTypeId(samp.getSampTypeId());
		po.setSampTypeName(samp.getSampName());
		po.setSampCode(samp.getSampCode());
		//po.setSampType(samp.getSampType());
		po.setSampName(v.getSampName());
		po.setPointName(samp.getPointName());
		po.setPointCode(samp.getPointCode());
		po.setItemIds(samp.getItemIds());
		po.setItemNames(samp.getItemNames());
		po.setNum(v.getNum());
		po.setType(SampUsed.TYPE_OUT);
		Account at=accountDao.findById(v.getUseId());
		po.setOrgId(getCurrent().getOrgId());
		po.setOrgName(getCurrent().getOrgName());
		if(null!=at) {
			po.setDeptId(at.getOrg().getId());
			po.setDeptName(at.getOrg().getName());
		}
		po.setUseId(v.getUseId());
		po.setUseName(v.getUseName());
		po.setUseDate(v.getUseDate());
		po.setContent(v.getContent());
		po.setStatus(v.getStatus());
		po.setRemark(v.getRemark());
		po.setGh(v.getGh());
		sampUsedDao.saveOrUpdate(po);
		if(po.getStatus().equals(Sampling.ST_21)&&po.getGh().equals(Constants.S)) {
			initGh(po);
		}
	}
	public void initGh(SampUsed out) {
		SampUsed po=new SampUsed();
		po.setType(SampUsed.TYPE_IN);
		po.setStatus(Sampling.ST_22);
		po.setSampling(out.getSampling());
		po.setTask(out.getTask());
		po.setCust(out.getCust());
		po.setSampTypeId(out.getSampTypeId());
		po.setSampTypeName(out.getSampName());
		po.setSampCode(out.getSampCode());
		po.setSampId(out.getSampId());
		po.setSampName(out.getSampName());
		po.setPointName(out.getPointName());
		po.setPointCode(out.getPointCode());
		po.setItemIds(out.getItemIds());
		po.setItemNames(out.getItemNames());
		po.setOrgId(getCurrent().getOrgId());
		po.setOrgName(getCurrent().getOrgName());
		po.setDeptId(out.getDeptId());
		po.setDeptName(out.getDeptName());
		po.setUseId(out.getUseId());
		po.setUseName(out.getUseName());
		sampUsedDao.add(po);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SampUsedVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("status", OrderCondition.ORDER_ASC);
			pageResult.addOrder("createTime", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("type='"+SampUsed.TYPE_OUT+"'"));
		pageResult = sampUsedDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SampUsed>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	@Override
	public List<SampUsedVo> listByTaskId(String taskId) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+samplingDao.getEntityName(SampUsed.class)+" WHERE isDel="+Po.N);
		hql.append(" AND task.id ='"+taskId+"'");
		hql.append(" ORDER BY sampCode asc");
		List<SampUsed> sampList=sampUsedDao.list(hql.toString());
		List<SampUsedVo> voList=toVoList(sampList);
		return voList;
	}

	@Override
	public List<SampUsedVo> listByTaskId(String taskId, String type) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+samplingDao.getEntityName(SampUsed.class)+" WHERE isDel="+Po.N);
		hql.append(" AND task.id ='"+taskId+"'");
		hql.append(" AND type ='"+type+"'");
		hql.append(" ORDER BY sampCode asc");
		List<SampUsed> sampList=sampUsedDao.list(hql.toString());
		List<SampUsedVo> voList=toVoList(sampList);
		return voList;
	}
}
