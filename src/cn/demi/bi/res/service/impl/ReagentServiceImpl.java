package cn.demi.bi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.res.dao.IReagentDao;
import cn.demi.bi.res.po.Reagent;
import cn.demi.bi.res.service.IReagentService;
import cn.demi.bi.res.vo.ReagentVo;

@Service("bi.reagentService")
public class ReagentServiceImpl extends BaseServiceImpl<ReagentVo,Reagent> implements IReagentService {
	
	@Autowired private IReagentDao reagentDao;
	
	@Override
	public void data2Vo(String[] values, ReagentVo v, String param) throws GlobalException {
		v.setName(values[1]);
		v.setEname(values[2]);
		v.setSname(values[3]);
		v.setGrade(values[4]);
		v.setType(values[5]);
		v.setSafeAmount(values[6]);
		v.setAmount(values[7]);
		v.setNo(values[8]);
		v.setUnit(values[9]);
		v.setRule(values[10]);
		v.setUser(values[11]);
		v.setDep(values[12]);
		v.setSupplier(values[13]);
		v.setMfg(values[14]);
		v.setExp(values[15]);
		v.setState(values[16]);
		v.setPrice(values[17]);
		v.setBnum(values[18]);
		v.setPurity(values[19]);
		v.setSaveCode(values[20]);
		v.setPurpose(values[21]);
		v.setRemark(values[22]);
	}
	
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = reagentDao.selectByPageType(pageType);
		return poList;
	}

	@Override
	public List<Object> MaxPayTenReagent(ReagentVo vo) throws GlobalException {
		return reagentDao.MaxPayTenReagent(vo);
	}
	
	@Override
	public List<QueryCondition> toQueryList(ReagentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getName()))
			queryConditions.add(new QueryCondition("name IN ('"+v.getName().replace(",", "','")+"')"));
		return queryConditions;
	}
	@Override
	public List<ReagentVo> list(ReagentVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getName() && StringUtils.isNotBlank(v.getName()))
			queryList.add(new QueryCondition("name",QueryCondition.EQ,v.getName()));
		return toVoList(reagentDao.list(queryList));
	}
}
