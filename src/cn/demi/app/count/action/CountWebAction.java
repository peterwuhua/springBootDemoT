package cn.demi.app.count.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.Action;
import cn.core.framework.constant.ConstantApp;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.count.service.ICountTaskService;
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
import cn.demi.app.sys.vo.OutVo;
import cn.demi.bi.task.service.ITaskItemService;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.vo.TaskItemVo;

@Controller("app.countWebAction")
@RequestMapping("/app_count_web/")
public class CountWebAction extends Action {

	@Autowired
	private ICountTaskService countTaskService;
	@Autowired
	private ITaskItemService testItemService;

	/**
	 * 统计查询任务查询
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_search_task.do")
	public String taskCount(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String custname = request.getParameter("custname");
		String samptypename = request.getParameter("samptypename");
		String tasktype = request.getParameter("tasktype");
		String beginDate = request.getParameter("beginDate");
		String finishDate = request.getParameter("finishDate");
		List<CountTaskList> list = countTaskService.taskList(objVo, no, custname, samptypename, tasktype, beginDate,
				finishDate);

		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 统计查询任务查询
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_task_info.do")
	public String taskInfo(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String id = request.getParameter("id");
		objVo.setId(id);
		CountTaskInfo countTaskInfo = countTaskService.taskInfo(objVo);
		outVo.setData(countTaskInfo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 费用统计列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_price_list.do")
	public String priceCount(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String beginDate = request.getParameter("beginDate");
		String finishDate = request.getParameter("finishDate");
		String taskType = request.getParameter("taskType");
		List<CountPriceList> list = countTaskService.priceList(objVo, no, beginDate, finishDate, taskType);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 项目统计列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_item_list.do")
	public String itemCount(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String itemName = request.getParameter("itemName");
		String sampTypeName = request.getParameter("sampTypeName");
		String orgName = request.getParameter("orgName");
		String userName = request.getParameter("userName");
		String beginDate = request.getParameter("beginDate");
		String finishDate = request.getParameter("finishDate");
		String taskType = request.getParameter("taskType");
		List<CountItemList> list = countTaskService.itemList(objVo, no, itemName, sampTypeName, orgName, userName,
				beginDate, finishDate, taskType);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 人员考核统计列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_kh_list.do")
	public String khCount(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		List<CountKhList> list = countTaskService.khList();
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 考勤查询列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_kq_list.do")
	public String KqCount(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String beginDate = request.getParameter("beginDate");
		String finishDate = request.getParameter("finishDate");
		String taskType = request.getParameter("taskType");
		List<CountKqList> list = countTaskService.kqList(objVo, no, name, beginDate, finishDate, taskType);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 报告列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_rept_list.do")
	public String appReportList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String taskNo = request.getParameter("taskNo");
		String cusname = request.getParameter("cusname");
		String sampTypeName = request.getParameter("sampTypeName");
		String beginDate = request.getParameter("beginDate");
		String finishDate = request.getParameter("finishDate");
		String taskType = request.getParameter("taskType");
		List<CountRept> list = countTaskService.appReportList(page, rows, objVo, no, taskNo, cusname, sampTypeName,
				beginDate, finishDate, taskType);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 报告列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_project_list.do")
	public String appProjectList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		String no = request.getParameter("no");
		String custName = request.getParameter("custName");
		String wtName = request.getParameter("wtName");
		String htPrice = request.getParameter("htPrice");
		String qdDate = request.getParameter("qdDate");
		String pay = request.getParameter("pay");
		String htSt = request.getParameter("htSt");
		List<CountProject> list = countTaskService.appProjectList(objVo, no, custName, wtName, htPrice, qdDate, pay,
				htSt);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 报告列表
	 *
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "count_user_work_list.do")
	public String list4Uw(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String TestMan = request.getParameter("testMan");
		String DeptName = request.getParameter("deptName");
		String StartDate = request.getParameter("startDate");
		String EndDate = request.getParameter("endDate");
		TaskItemVo v = new TaskItemVo();
		v.setTestMan(TestMan);
		v.setDeptName(DeptName);
		v.setStartDate(StartDate);
		v.setEndDate(EndDate);
		List<AppObjvo> list = countTaskService.list4Uw(v, objVo);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

}
