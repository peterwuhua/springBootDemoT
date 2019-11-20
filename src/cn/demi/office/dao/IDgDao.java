package cn.demi.office.dao;

import cn.demi.office.po.Dg;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;

public interface IDgDao extends IBaseDao<Dg> {
	
	/**
	 * 移动端 打卡数据插入
	 * @param atId 账户Id
	 * @return
	 */
	Dg insert(String atId);
	/**
	 * 获取用户某日打卡记录
	 * @param userId 用户Id(非账户Id)
	 * @param date  日期
	 * @return
	 */
	List<Dg> find(String userId,String date);
	List<Dg> findPm(String userId,String date);
	List<Dg> findPmApp(String userId, String date, String checkOrNoCkeck);
	List<Dg> findApp(String userId, String date, String checkOrNoCkeck);
}

