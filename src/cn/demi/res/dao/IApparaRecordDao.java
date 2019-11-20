package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.po.ApparaRecord;

public interface IApparaRecordDao extends IBaseDao<ApparaRecord> {

	/**
	 * <strong>创建信息:Hans 2016年6月12日 下午4:34:51 </strong> <br>
	 * <strong>概要 :通过apparaId获取仪器操作list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param id
	 *@param type
	 *@return
	 */
	public List<ApparaRecord> apparaRecordListByAppId(String id, String type);

	/**
	 * <strong>创建信息:Hans 2016年6月13日 下午5:06:03 </strong> <br>
	 * <strong>概要 :通过date排序获取list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@return
	 *@throws GlobalException
	 */
	public List<ApparaRecord> listOrderByDate(String type,String appId);

	/**
	 * <strong>创建信息:Hans 2016年7月7日 下午2:37:24 </strong> <br>
	 * <strong>概要 :通过日期段获取list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param startTime
	 *@param endTime
	 *@return
	 *@throws GlobalException
	 */
	public List<ApparaRecord> listByTime(String startTime, String endTime,String type);
	
}

