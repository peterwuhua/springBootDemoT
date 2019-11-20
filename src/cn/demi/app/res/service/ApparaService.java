package cn.demi.app.res.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.res.vo.AppApparain;
import cn.demi.app.res.vo.AppApparaout;
import cn.demi.app.res.vo.AppAppare;
import cn.demi.app.sys.vo.ObjVo;

public interface ApparaService {
	/**
	 * 仪器列表
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<AppAppare> apparalist(ObjVo v) throws GlobalException;

	/**
	 * 仪器详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppAppare findappare(String id) throws GlobalException;

	/**
	 * 获取检定校准仪器列表
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<AppAppare> apparajdjclist(ObjVo v) throws GlobalException;

	/**
	 * 获取仪器出库列表
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<AppApparaout> apparaoutlist(ObjVo v) throws GlobalException;

	/**
	 * 获取仪器入库列表
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<AppApparain> apparainlist(ObjVo v) throws GlobalException;

}
