package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.res.dao.IStandardDao;
import cn.demi.res.dao.IStandardLogDao;
import cn.demi.res.po.Standard;
import cn.demi.res.po.StandardLog;
import cn.demi.res.service.IStandardService;
import cn.demi.res.vo.StandardVo;

@Service("res.standardService")
public class StandardServiceImpl extends BaseServiceImpl<StandardVo,Standard> implements
		IStandardService {
	@Autowired 
	private IStandardDao standardDao;
	@Autowired 
	private IStandardLogDao standardLogDao;
	@Autowired 
	private IAccountDao accountDao;
	@Autowired 
	private IOrgDao orgDao;
	
	public static final String EFFECTIVE_DAY = ApplicationUtils.getValue("config.standard.day").toString();
	
	@Override
	public void data2Vo(String[] values,StandardVo v,String param){
		v.setNo(values[1]);
		v.setName(values[2]);
		v.setCerNo(values[3]);
		v.setCas(values[4]);
		v.setRule(values[5]);
		v.setContent(values[6]);
		v.setAmount(values[7].equals("")?0:Double.valueOf(values[7]));
		v.setSafeAmount(values[8].equals("")?0:Double.valueOf(values[8]));
		v.setUnit(values[8]);
		v.setExp(values[9]);
		//v.setKeeper(values[10]);
	}
	
	@Override
	public void add(StandardVo v) throws GlobalException {
		//v.setDep(getCurrent().getOrgCode());
		Standard p = vo2Po(v);
		if(StrUtils.isNotBlankOrNull(v.getKeepId())) {
			Account at=accountDao.findById(v.getKeepId());
			p.setDeptId(at.getOrg().getId());
			p.setDeptName(at.getOrg().getName());
			Org org=orgDao.findOrg(at.getOrg().getId());
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		standardDao.add(p);
		v.setId(p.getId());
		
		addLog(v,0d,p);
	}

	private void addLog(StandardVo v,Double lastAmount,Standard p) {
		StandardLog rl = new StandardLog();
		BeanUtils.copyProperties(v, rl,rl.IGNORE_PROPERTY);
		rl.setLastAmount(lastAmount);
		rl.setRkNum(String.valueOf(v.getAmount()-lastAmount));
		rl.setDate(DateUtils.getCurrDateTimeStr());
		rl.setStandard(p);
		standardLogDao.add(rl);
	}
	
	@Override
	public void update(StandardVo v) throws GlobalException {
		Standard p =standardDao.findById(v.getId());
		addLog(v,p.getAmount(),p);
		p.toPo(v, p);
		if(StrUtils.isNotBlankOrNull(v.getKeepId())) {
			Account at=accountDao.findById(v.getKeepId());
			p.setDeptId(at.getOrg().getId());
			p.setDeptName(at.getOrg().getName());
			Org org=orgDao.findOrg(at.getOrg().getId());
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
		}
		standardDao.update(p);
	}

	@Override
	public PageResult pageResult(StandardVo v, PageResult pageResult)
			throws GlobalException {
		pageResult.addCondition("dep", QueryCondition.EQ,getCurrent().getDepCode());
		return super.pageResult(v, pageResult);
	}
	
	@Override
	public PageResult effectivePage(StandardVo v, PageResult pageResult)
			throws GlobalException {
		int effectiveDay = Integer.valueOf(EFFECTIVE_DAY);
		String effectiveDate = DateUtils.getNextDate(DateUtils.getCurrDateStr(), effectiveDay);
		pageResult.addCondition("dep", QueryCondition.EQ,getCurrent().getDepCode());
		pageResult.addCondition("exp < '"+effectiveDate+"'");
		return super.pageResult(v, pageResult);
	}

	@Override
	public List<QueryCondition> toQueryList(StandardVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if("Y".equals(v.getIsSafe())){
			queryConditions.add(new QueryCondition("safeAmount>amount"));
		}
		return queryConditions;
	}

	@Override
	public GridVo effectivePageData(GridVo gridVo, StandardVo v) throws GlobalException {
		
			PageResult pageResult = new PageResult();
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
			
			pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
			pageResult.addConditionList(toQueryList(v));
			
			PageBean pageBean =	pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if(0!=gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			
			PageResult p = super.pageResult(pageResult);
			List<StandardVo> vList = new ArrayList<StandardVo>();
			for(int i = 0 ; i < p.getResultList().size() ; i++){
				StandardVo vo =  (StandardVo) p.getResultList().get(i) ;
				if(null != vo.getExp() && !"".equals(vo.getExp())){
					Date expDate = DateUtils.parseToDate(vo.getExp());
					if(new Date().getTime() >= expDate.getTime()){
						vo.setDateType("已过效期");
					}else{
						vo.setDateType("未过效期");
					}
				}
				vList.add(vo);
			}
			gridVo.setDatas(vList);
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
			return gridVo;
		
	}

	@Override
	public void importData(StandardVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)){
			for(Standard p:standardDao.list()) {
				p.setIsDel(Po.Y);
				standardDao.update(p);
			}
		}
		StandardVo vo = null;
		for (int i = 4, j = dataArrays.length; i < j; i++) {
			if (dataArrays[i].length == 0)
				continue;
				
			String[] values = dataArrays[i];
			if(null==values[0] || "".equals(values[0].trim())){
				log.info("第 "+ i +"行无效数据不做导入");
				continue;
			}
			
			vo = v.instance();
			try {
				vo.setSort(Integer.valueOf(values[0]));
			} catch (Exception e) {
				vo.setSort(i);
			}
			// 数组数据对应至对象
			data2Vo(values, vo, param);
			add(vo);
		}
	}
	@Override
	public String copy(StandardVo v) throws GlobalException {
		v = findById(v.getIds());
		v.setId(null);
		v.setNo(null);
		v.setAmount(0.0);
		Standard p = vo2Po(v);
		standardDao.add(p);
		return p.getId();
	}
	
	
	
}
