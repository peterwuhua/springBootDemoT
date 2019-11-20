package cn.demi.app.count.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.count.vo.AppObjvo;
import cn.demi.app.count.vo.CountItemList;
import cn.demi.app.count.vo.CountKhList;
import cn.demi.app.count.vo.CountKqList;
import cn.demi.app.count.vo.CountPriceList;
import cn.demi.app.count.vo.CountProject;
import cn.demi.app.count.vo.CountRept;
import cn.demi.app.count.vo.CountTaskInfo;
import cn.demi.app.count.vo.CountTaskList;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.test.vo.TaskItemVo;
import net.sf.json.JSONArray;

public interface ICountTaskService {
	/**
	 * 获取任务查询列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CountTaskList> taskList(ObjVo v, String no, String custname, String samptypename, String tasktype,
			String beginDate, String finishDate) throws GlobalException;

	/**
	 * 获取任务查询详情
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public CountTaskInfo taskInfo(ObjVo v) throws GlobalException;

	/**
	 * 获取费用统计列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CountPriceList> priceList(ObjVo v, String no, String beginDate, String finishDate, String taskType)
			throws GlobalException;

	/**
	 * 获取监测项目统计列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CountItemList> itemList(ObjVo v, String no, String itemName, String sampTypeName, String orgName,
			String userName, String beginDate, String finishDate, String taskType) throws GlobalException;

	/**
	 * 人员考核
	 *
	 * @return
	 * @throws GlobalException
	 */
	public List<CountKhList> khList() throws GlobalException;

	/**
	 * 考勤查询列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CountKqList> kqList(ObjVo v, String no, String name, String beginDate, String finishDate,
			String taskType) throws GlobalException;

	/**
	 * 报告统计
	 * 
	 * @param page
	 * @param row
	 * @param v
	 * @param no
	 * @param taskNo
	 * @param cusname
	 * @param sampTypeName
	 * @param beginDate
	 * @param finishDate
	 * @param taskType
	 * @return
	 * @throws GlobalException
	 */
	public List<CountRept> appReportList(int page, int row, ObjVo v, String no, String taskNo, String cusname,
			String sampTypeName, String beginDate, String finishDate, String taskType) throws GlobalException;

	/**
	 * 监测项目统计
	 */

	public List<CountProject> appProjectList(ObjVo v, String no, String custName, String wtName, String htPrice,
			String qdDate, String pay, String htSt) throws GlobalException;

	/**
	 * 人员工作量
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<AppObjvo> list4Uw(TaskItemVo v,ObjVo objVo) throws GlobalException;
}
