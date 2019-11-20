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
import cn.demi.qlt.dao.ICheckDao;
import cn.demi.qlt.po.Check;
import cn.demi.qlt.service.ICheckService;
import cn.demi.qlt.vo.CheckVo;

@Service("qlt.checkService")
public class CheckServiceImpl extends BaseServiceImpl<CheckVo,Check> implements
		ICheckService {
	@Autowired 
	private ICheckDao checkDao;
	
	@Override
	public CheckVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	
	@Override
	public void add(CheckVo v) throws GlobalException {
		Check p = vo2Po(v);
		p.setNo(createCode());
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		checkDao.add(p);
		v.setId(p.getId());
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+checkDao.getEntityName(Check.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) checkDao.query(hql).getSingleResult();
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
	public void update(CheckVo v) throws GlobalException {
		Check p = checkDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		checkDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CheckVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("no");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = checkDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Check>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}

