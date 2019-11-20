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
import cn.demi.qlt.dao.IAccidentDao;
import cn.demi.qlt.po.Accident;
import cn.demi.qlt.service.IAccidentService;
import cn.demi.qlt.vo.AccidentVo;

@Service("qlt.accidentService")
public class AccidentServiceImpl extends BaseServiceImpl<AccidentVo,Accident> implements
		IAccidentService {

	@Autowired
	private IAccidentDao accidentDao;
	@Override
	public void add(AccidentVo v) throws GlobalException {
		Accident p = vo2Po(v);
		p.setNo(createCode());
		accidentDao.add(p);
		v.setId(p.getId());
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+accidentDao.getEntityName(Accident.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) accidentDao.query(hql).getSingleResult();
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
	public void update(AccidentVo v) throws GlobalException {
		Accident p = accidentDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		accidentDao.update(p);
	}
	@Override
	public void update4Audit(AccidentVo v) throws GlobalException {
		Accident p = accidentDao.findById(v.getId());
		p.setAuditId(v.getAuditId());
		p.setAuditName(v.getAuditName());
		p.setAuditDate(v.getAuditDate());
		p.setResult(v.getResult());
		p.setRemark(v.getRemark());
		p.setStatus(Accident.ST_2);
		accidentDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, AccidentVo v) throws GlobalException {
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
		pageResult = accidentDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Accident>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Query(GridVo gridVo, AccidentVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("createTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status!='"+Accident.ST_0+"'"));
		pageResult = accidentDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Accident>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
