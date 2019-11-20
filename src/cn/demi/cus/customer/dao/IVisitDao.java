package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Visit;

public interface IVisitDao extends IBaseDao<Visit> {
	
	public List<Visit> listBycusId(String... cusId) throws GlobalException;
}

