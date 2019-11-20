package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.ClientPoint;

public interface IClientPointDao extends IBaseDao<ClientPoint> {
	/**
	 * 根据检测单位 获取点位集合
	 * @param client
	 * @return
	 * @throws GlobalException
	 */
	public List<ClientPoint> listBycusId(String... client) throws GlobalException;
	/**
	 * 获取客户下某个点位
	 * @param cusId
	 * @param pointName
	 * @return
	 * @throws GlobalException
	 */
	public ClientPoint findBycusId(String cusId,String pointName) throws GlobalException;
	/**
	 * 获取客户下某个点位
	 * @param cusId 客户Id
	 * @param sampTypeId 样品类型
	 * @param sampType  大类
	 * @param pointName 点位Id
	 * @return
	 * @throws GlobalException
	 */
	public ClientPoint findBycusId(String cusId,String sampTypeId,String sampType,String pointName) throws GlobalException;
}

