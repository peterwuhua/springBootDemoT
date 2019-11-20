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
import cn.demi.bi.res.dao.IStandardDao;
import cn.demi.bi.res.po.Standard;
import cn.demi.bi.res.service.IStandardService;
import cn.demi.bi.res.vo.StandardVo;

@Service("bi.standardService")
public class StandardServiceImpl extends BaseServiceImpl<StandardVo,Standard> implements IStandardService {
	
	@Autowired private IStandardDao standardDao;
	
	@Override
	public void data2Vo(String[] values, StandardVo v, String param) throws GlobalException {
		v.setNo(values[1]);
		v.setCas(values[2]);
		v.setCerNo(values[3]);
		v.setName(values[4]);
		v.setEname(values[5]);
		v.setSafeAmount(values[6]);
		v.setAmount(values[7]);
		v.setUnit(values[8]);
		v.setRule(values[9]);
		v.setContent(values[10]);
		v.setUser(values[11]);
		v.setDep(values[12]);
		v.setSupplier(values[13]);
		v.setProducer(values[14]);
		v.setMfg(values[15]);
		v.setExp(values[16]);
		v.setState(values[17]);
		v.setPrice(values[18]);
		v.setSaveCondition(values[19]);
		v.setPurDate(values[20]);
		v.setRemark(values[21]);
	}
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = standardDao.selectByPageType(pageType);
		return poList;
	}

	@Override
	public List<Object> MaxPayTenStandard(StandardVo vo) throws GlobalException {
		return standardDao.MaxPayTenStandard(vo);
	}
	
	@Override
	public List<QueryCondition> toQueryList(StandardVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getName()))
			queryConditions.add(new QueryCondition("name IN ('"+v.getName().replace(",", "','")+"')"));
		return queryConditions;
	}
	@Override
	public List<StandardVo> list(StandardVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getName() && StringUtils.isNotBlank(v.getName()))
			queryList.add(new QueryCondition("name",QueryCondition.EQ,v.getName()));
		return toVoList(standardDao.list(queryList));
	}
}
