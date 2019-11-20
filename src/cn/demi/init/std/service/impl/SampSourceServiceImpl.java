package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.ISampSourceDao;
import cn.demi.init.std.po.SampSource;
import cn.demi.init.std.service.ISampSourceService;
import cn.demi.init.std.vo.SampSourceVo;

@Service("init.sampSourceService")
public class SampSourceServiceImpl extends BaseServiceImpl<SampSourceVo,SampSource> implements
		ISampSourceService {
	@Autowired
	private ISampSourceDao sampSourceDao;
	
	@Override
	public void update(SampSourceVo v) throws GlobalException {
		SampSource p = sampSourceDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		sampSourceDao.update(p);
	}
	@Override
	public void save(SampSourceVo v) throws GlobalException {
		if(StrUtils.isEmpty(v.getId())){
			add(v);
		}else{
			update(v);
		}
	}
	@Override
	public void add(SampSourceVo v) throws GlobalException {
		SampSource p = vo2Po(v);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		sampSourceDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public SampSourceVo findByCode(String code) throws GlobalException {
		code=code.replaceAll("　", " ").replaceAll("_", "-").replaceAll("－", "-").trim();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code LIKE '"+code+"'"));
		List<SampSourceVo> list = super.list(queryConditions);
		SampSourceVo vo = new SampSourceVo();
		if(list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(SampSource p:sampSourceDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			sampSourceDao.update(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SampSourceVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = sampSourceDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SampSource>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<SampSourceVo> list(SampSourceVo v) throws GlobalException {
		String jpql="FROM "+sampSourceDao.getEntityName(SampSource.class)+" WHERE isDel='"+Po.N+"'";
		jpql+=" AND id in ('"+v.getIds().replace(",", "','")+"')";
		List<SampSource> plist=sampSourceDao.list(jpql);
		return toVoList(plist);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Sels(GridVo gridVo, SampSourceVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = sampSourceDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SampSource>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<SampSourceVo> list4Sels(String ids) throws GlobalException {
		List<SampSource> ssList=sampSourceDao.listByIds(ids);
		return toVoList(ssList);
	}
}
