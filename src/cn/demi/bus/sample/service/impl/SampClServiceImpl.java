package cn.demi.bus.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISampClService;
import cn.demi.bus.sample.vo.SamplingVo;

@Service("bus.sampClService")
public class SampClServiceImpl extends BaseServiceImpl<SamplingVo,Sampling> implements
		ISampClService {
	@Autowired
	private ISamplingDao samplingDao;
	@Override
	public SamplingVo findById(String id) throws GlobalException {
		Sampling po = samplingDao.findById(id);
		SamplingVo vo=po2Vo(po);
		return vo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SamplingVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='"+Sampling.ST_30+"'"));
		pageResult.addCondition(new QueryCondition("type='"+Sampling.SAMP_TYPE_PT+"'"));
		pageResult = samplingDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Sampling>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<SamplingVo> listByTaskId(String taskId) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel="+Po.N);
		hql.append(" AND task.id ='"+taskId+"'");
		hql.append(" ORDER BY sampCode asc");
		List<Sampling> sampList=samplingDao.list(hql.toString());
		List<SamplingVo> voList=toVoList(sampList);
		return voList;
	}

	@Override
	public List<SamplingVo> listByTaskId(String taskId, String type) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel="+Po.N);
		hql.append(" AND task.id ='"+taskId+"'");
		hql.append(" AND type ='"+type+"'");
		hql.append(" ORDER BY sampCode asc");
		List<Sampling> sampList=samplingDao.list(hql.toString());
		List<SamplingVo> voList=toVoList(sampList);
		return voList;
	}
}
