package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.ApparaRecordVo;

@Transactional
public interface IApparaRecordService extends IBaseService<ApparaRecordVo> {

	/**
	 * <strong>创建信息:Hans 2016年6月12日 下午4:12:16 </strong> <br>
	 * <strong>概要 :通过apparaId获取仪器操作list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param id
	 *@param type
	 *@return
	 * @throws GlobalException 
	 */
	List<ApparaRecordVo> apparaRecordListByAppId(String id, String type) throws GlobalException;

	/**
	 * <strong>创建信息:Hans 2016年6月13日 下午5:06:03 </strong> <br>
	 * <strong>概要 :通过date排序获取list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@return
	 *@throws GlobalException
	 */
	List<cn.demi.res.vo.ApparaRecordVo> listOrderByDate(String type,String appId) throws GlobalException;

	/**
	 * <strong>创建信息:Hans 2016年7月7日 下午2:37:24 </strong> <br>
	 * <strong>概要 :通过日期段获取list </strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param startTime
	 *@param endTime
	 *@return
	 *@throws GlobalException
	 */
	List<ApparaRecordVo> listByTime(String startTime, String endTime,String type) throws GlobalException;
	
}
