package cn.demi.qlt.service.impl;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.dao.IAbilityDao;
import cn.demi.qlt.po.Ability;
import cn.demi.qlt.service.IAbilityService;
import cn.demi.qlt.vo.AbilityVo;

@Service("qlt.abilityService")
public class AbilityServiceImpl extends BaseServiceImpl<AbilityVo,Ability> implements
		IAbilityService {

	@Autowired
	private IAbilityDao abilityDao;
	@Override
	public void add(AbilityVo v) throws GlobalException {
		Ability p = vo2Po(v);
		p.setNo(createCode());
		abilityDao.add(p);
		v.setId(p.getId());
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+abilityDao.getEntityName(Ability.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) abilityDao.query(hql).getSingleResult();
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
	@Override
	public void update(AbilityVo v) throws GlobalException {
		Ability p = abilityDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		abilityDao.update(p);
	}
//	@Override
//	public void update4Audit(AbilityVo v) throws GlobalException {
//		Ability p = abilityDao.findById(v.getId());
//		p.setAuditId(v.getAuditId());
//		p.setAuditName(v.getAuditName());
//		p.setAuditDate(v.getAuditDate());
//		p.setStatus(v.getStatus());
//		abilityDao.update(p);
//	}
	@Override
	public void update4Record(AbilityVo v) throws GlobalException {
		Ability p = abilityDao.findById(v.getId());
		p.setJlId(v.getJlId());
		p.setJlName(v.getJlName());
		p.setJlDate(v.getJlDate());
		p.setStatus(Ability.ST_2);
		p.setResult(v.getResult());
		abilityDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, AbilityVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.addOrder("status", OrderCondition.ORDER_ASC);
			pageResult.addOrder("createTime", OrderCondition.ORDER_DESC);
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!StrUtils.isBlankOrNull(v.getStatus())) {
			pageResult.addCondition(new QueryCondition("status='"+v.getStatus()+"'"));
		}
		pageResult = abilityDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Ability>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Query(GridVo gridVo, AbilityVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("createTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status!='"+Ability.ST_0+"'"));
		pageResult = abilityDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Ability>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
