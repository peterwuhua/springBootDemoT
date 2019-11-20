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
import cn.demi.qlt.dao.IComplainDao;
import cn.demi.qlt.po.Complain;
import cn.demi.qlt.service.IComplainService;
import cn.demi.qlt.vo.ComplainVo;

@Service("qlt.complainService")
public class ComplainServiceImpl extends BaseServiceImpl<ComplainVo,Complain> implements
		IComplainService {
	
	@Autowired 
	private IComplainDao complainDao;
	
	@Override
	public ComplainVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	
	@Override
	public void add(ComplainVo v) throws GlobalException {
		Complain p = vo2Po(v);
		p.setNo(createCode());
		complainDao.add(p);
		v.setId(p.getId());
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+complainDao.getEntityName(Complain.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) complainDao.query(hql).getSingleResult();
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
	public void update(ComplainVo v) throws GlobalException {
		Complain p = complainDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		complainDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ComplainVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("no");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = complainDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Complain>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
