package cn.demi.qlt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.dao.IVisitDao;
import cn.demi.qlt.po.Visit;
import cn.demi.qlt.service.IVisitService;
import cn.demi.qlt.vo.VisitVo;

@Service("qlt.visitService")
public class VisitServiceImpl extends BaseServiceImpl<VisitVo,Visit> implements
		IVisitService {
	
	@Autowired 
	private IVisitDao visitDao;
	
	@Override
	public VisitVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	
	@Override
	public void add(VisitVo v) throws GlobalException {
		Visit p = vo2Po(v);
		p.setNo(createCode());
		visitDao.add(p);
		v.setId(p.getId());
		setVisit(p);
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+visitDao.getEntityName(Visit.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) visitDao.query(hql).getSingleResult();
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
	public void update(VisitVo v) throws GlobalException {
		Visit p = visitDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		visitDao.update(p);
		setVisit(p);
	}
	//处理继续回访
	public void setVisit(Visit p) throws GlobalException {
		if(p.getStatus().equals(Visit.ST_1)) {
			Visit np = new Visit();
			np.setCustId(p.getCustId());
			np.setCustName(p.getCustName());
			np.setUser(p.getUser());
			np.setPhone(p.getPhone());
			np.setStatus(Visit.ST_1);
			visitDao.add(np);
			p.setStatus(Visit.ST_2);
			visitDao.update(p);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, VisitVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("createTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = visitDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Visit>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
