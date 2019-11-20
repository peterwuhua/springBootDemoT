package cn.demi.cus.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.INormalListDao;
import cn.demi.cus.customer.po.NormalList;
import cn.demi.cus.customer.service.INormalListService;
import cn.demi.cus.customer.vo.NormalListVo;

@Service("cus.normalListService")
public class NormalListServiceImpl extends BaseServiceImpl<NormalListVo,NormalList> implements
		INormalListService {
	@Autowired private INormalListDao normalListDao;
	
	
	
	@Override
	public List<String> getGjStatusList() throws GlobalException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cates", "gjStatus"); //查询条件
		return normalListDao.queryByCondition(map);
	}



	@Override
	public List<String> getCusCatesList() throws GlobalException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cates", "cusCates"); //查询条件
		return normalListDao.queryByCondition(map); 
	}



	@Override
	public List<String> auditStatusList() throws GlobalException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cates", "auditStatus");
		return normalListDao.queryByCondition(map);
	}



	@Override
	public List<String> getFetchPayWay() throws GlobalException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cates", "payWay"); //查询条件
		return normalListDao.queryByCondition(map);
	}


}
