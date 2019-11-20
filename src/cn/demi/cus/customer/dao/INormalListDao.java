package cn.demi.cus.customer.dao;

import java.util.List;
import java.util.Map;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.cus.customer.po.NormalList;

public interface INormalListDao extends IBaseDao<NormalList> {
	
	List<String> queryByCondition(Map<String,Object> map);
}

