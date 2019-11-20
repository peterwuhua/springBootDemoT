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
import cn.demi.bi.res.dao.IConsumableDao;
import cn.demi.bi.res.po.Consumable;
import cn.demi.bi.res.service.IConsumableService;
import cn.demi.bi.res.vo.ConsumableVo;

@Service("bi.consumableService")
public class ConsumableServiceImpl extends BaseServiceImpl<ConsumableVo,Consumable> implements IConsumableService {
	
	@Autowired private IConsumableDao consumableDao;
	
	@Override
	public void data2Vo(String[] values, ConsumableVo v, String param) throws GlobalException {
		v.setName(values[1]);
		v.setModel(values[2]);
		v.setType(values[3]);
		v.setSafeAmount(values[4]);
		v.setAmount(values[5]);
		v.setNo(values[6]);
		v.setUnit(values[7]);
		v.setUser(values[8]);
		v.setDep(values[9]);
		v.setSupplier(values[10]);
		v.setMfg(values[11]);
		v.setExp(values[12]);
		v.setState(values[13]);
		v.setPrice(values[14]);
		v.setRemark(values[15]);
	}
	
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = consumableDao.selectByPageType(pageType);
		return poList;
	}

	@Override
	public List<Object> MaxPayTenConsumable(ConsumableVo vo) throws GlobalException {
		return consumableDao.MaxPayTenConsumable(vo);
	}
	
	@Override
	public List<QueryCondition> toQueryList(ConsumableVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getName()))
			queryConditions.add(new QueryCondition("name IN ('"+v.getName().replace(",", "','")+"')"));
		return queryConditions;
	}
	@Override
	public List<ConsumableVo> list(ConsumableVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getName() && StringUtils.isNotBlank(v.getName()))
			queryList.add(new QueryCondition("name",QueryCondition.EQ,v.getName()));
		return toVoList(consumableDao.list(queryList));
	}
}
