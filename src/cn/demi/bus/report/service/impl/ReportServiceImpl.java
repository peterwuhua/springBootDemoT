package cn.demi.bus.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.po.CustMaterial;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.dao.IReportDetailDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.po.ReportDetail;
import cn.demi.bus.report.service.IReportService;
import cn.demi.bus.report.vo.RItemVo;
import cn.demi.bus.report.vo.RappVo;
import cn.demi.bus.report.vo.ReportDetailVo;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.bus.report.vo.RsampVo;
import cn.demi.bus.report.vo.RtabDataVo;
import cn.demi.bus.report.vo.RtabRoomVo;
import cn.demi.bus.report.vo.RtabRowVo;
import cn.demi.bus.report.vo.RtabVo;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.dao.ISampSourceDao;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;
import cn.demi.init.std.po.SampSource;
import cn.demi.init.std.vo.PstandItemVo;
import cn.demi.pfm.dao.ISRecordDao;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;

@Service("bus.reportService")
public class ReportServiceImpl extends BaseServiceImpl<ReportVo, Report> implements IReportService {
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private IReportDetailDao reportDetailDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private ISampSourceDao sampSourceDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private ICustMaterialDao custMaterialDao;
	@Autowired
	private ISampRecordDao sampRecordDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ReportVo findById(String id) throws GlobalException {
		Report po = reportDao.findById(id);
		ReportVo vo = new ReportVo();
		vo = vo.toVo(po);
		List<FilesVo> fileList = new ArrayList<FilesVo>();
		List<SampType> sampList = sampTypeDao.listByIds(po.getSampTypeId(), "sort", OrderCondition.ORDER_ASC);
		List<ReportDetailVo> stVoList = new ArrayList<>();
		for (SampType samp : sampList) {
			ReportDetailVo stVo = new ReportDetailVo();
			stVo.setSampName(samp.getName());
			String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + vo.getId()
					+ "' AND sampTypeId ='" + samp.getId() + "' AND stype='" + Sampling.SAMP_TYPE_PT + "' AND parent.id is null ORDER BY sort asc";
			List<ReportDetail> detialList = reportDetailDao.list(hql);
			List<ReportDetailVo> dtVoList = new ArrayList<>();
			for (ReportDetail dl : detialList) {
				ReportDetailVo dlVo = new ReportDetailVo();
				dlVo = dlVo.toVo(dl);
				List<ReportDetail> subList = reportDetailDao.listByParent(dl.getId());
				List<ReportDetailVo> subVoList = new ArrayList<ReportDetailVo>();
				if (null != subList && subList.size() > 0) {
					for (ReportDetail sub : subList) {
						ReportDetailVo subVo = new ReportDetailVo();
						subVo = subVo.toVo(sub);
						subVoList.add(subVo);
						// 附件
						hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + sub.getTim().getId() + "'  ";
						List<Files> fList = filesDao.list(hql);
						if (null != fList) {
							for (Files f : fList) {
								FilesVo fVo = new FilesVo();
								fVo = fVo.toVo(f);
								fileList.add(fVo);
							}
						}
					}
				}
				dlVo.setSubList(subVoList);
				dtVoList.add(dlVo);
				// 附件
				hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + dl.getTim().getId() + "' ";
				List<Files> fList = filesDao.list(hql);
				if (null != fList) {
					for (Files f : fList) {
						FilesVo fVo = new FilesVo();
						fVo = fVo.toVo(f);
						fileList.add(fVo);
					}
				}
			}
			stVo.setSubList(dtVoList);
			stVoList.add(stVo);
		}
		vo.setDetailList(stVoList);
		List<ReportDetailVo> zkVoList = new ArrayList<>();
		for (SampType samp : sampList) {
			ReportDetailVo stVo = new ReportDetailVo();
			stVo.setSampName(samp.getName());
			String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + vo.getId()
					+ "' AND sampTypeId ='" + samp.getId() + "' AND stype<>'" + Sampling.SAMP_TYPE_PT + "' AND parent.id is null ORDER BY sort asc";
			List<ReportDetail> detialList = reportDetailDao.list(hql);
			List<ReportDetailVo> dtVoList = new ArrayList<>();
			if (null != detialList && detialList.size() > 0) {
				for (ReportDetail dl : detialList) {
					ReportDetailVo dlVo = new ReportDetailVo();
					dlVo = dlVo.toVo(dl);
					List<ReportDetail> subList = reportDetailDao.listByParent(dl.getId());
					List<ReportDetailVo> subVoList = new ArrayList<ReportDetailVo>();
					if (null != subList && subList.size() > 0) {
						for (ReportDetail sub : subList) {
							ReportDetailVo subVo = new ReportDetailVo();
							subVo = subVo.toVo(sub);
							subVoList.add(subVo);
							// 附件
							hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + sub.getTim().getId() + "'  ";
							List<Files> fList = filesDao.list(hql);
							if (null != fList) {
								for (Files f : fList) {
									FilesVo fVo = new FilesVo();
									fVo = fVo.toVo(f);
									fileList.add(fVo);
								}
							}
						}
					}
					dlVo.setSubList(subVoList);
					dtVoList.add(dlVo);
					// 附件
					hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + dl.getTim().getId() + "' ";
					List<Files> fList = filesDao.list(hql);
					if (null != fList) {
						for (Files f : fList) {
							FilesVo fVo = new FilesVo();
							fVo = fVo.toVo(f);
							fileList.add(fVo);
						}
					}
				}
				stVo.setSubList(dtVoList);
				zkVoList.add(stVo);
			}
		}
		vo.setZdetailList(zkVoList);
		if (!vo.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			List<TaskItem> timList = taskItemDao.listByTaskId(vo.getTaskVo().getId());
			List<TaskItemVo> timVoList = new ArrayList<TaskItemVo>();
			for (TaskItem tim : timList) {
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				int num = testItemDao.maxItemNum4Tim(tim.getId());
				timVo.setItemList(list4Item(tim.getId(), num));
				timVo.setCount(num);
				timVoList.add(timVo);
			}
			vo.setTimList(timVoList);
		}
		// 附件
		String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + vo.getTaskVo().getId() + "' ";
		List<Files> fList = filesDao.list(hql);
		if (null != fList) {
			for (Files f : fList) {
				FilesVo fVo = new FilesVo();
				fVo = fVo.toVo(f);
				fileList.add(fVo);
			}
		}
		vo.setFileList(fileList);
		return vo;
	}

	@Override
	public ReportVo find(String id) throws GlobalException {
		Report po = reportDao.findById(id);
		ReportVo vo = new ReportVo();
		vo = vo.toVo(po);
		if (vo.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			find4Hj(vo);
		} else {
			find4Zw(vo);
		}
		if (StrUtils.isBlankOrNull(vo.getReportDate())) {
			vo.setReportDate(DateUtils.getCurrDateStr());
		}
		if (StrUtils.isBlankOrNull(vo.getMakeDate())) {
			vo.setMakeDate(DateUtils.getCurrDateTimeStr());
		}
		if (StrUtils.isBlankOrNull(vo.getMakeUserId())) {
			vo.setMakeUser(CurrentUtils.getCurrent().getUserName());
			vo.setMakeUserId(CurrentUtils.getCurrent().getAccountId());
		}
		return vo;
	}

	// 环境 报告编辑页面数据
	public ReportVo find4Hj(ReportVo vo) throws GlobalException {
		List<FilesVo> fileList = new ArrayList<FilesVo>();
		List<SampType> sampList = sampTypeDao.listByIds(vo.getSampTypeId(), "sort", OrderCondition.ORDER_ASC);
		List<ReportDetailVo> stVoList = new ArrayList<>();
		List<String> idList = new ArrayList<>();
		boolean hasND = false;
		for (SampType samp : sampList) {
			ReportDetailVo stVo = new ReportDetailVo();
			stVo.setSampName(samp.getName());
			String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + vo.getId()
					+ "' AND sampTypeId ='" + samp.getId() + "' AND parent.id is null ORDER BY sort asc";
			List<ReportDetail> detialList = reportDetailDao.list(hql);
			List<ReportDetailVo> dtVoList = new ArrayList<>();
			for (ReportDetail dl : detialList) {
				ReportDetailVo dlVo = new ReportDetailVo();
				dlVo = dlVo.toVo(dl);
				if (dlVo.getValue().equals("未检出") || dlVo.getValue().equals("ND")) {
					hasND = true;
				}
				List<ReportDetail> subList = reportDetailDao.listByParent(dl.getId());
				List<ReportDetailVo> subVoList = new ArrayList<ReportDetailVo>();
				if (null != subList && subList.size() > 0) {
					for (ReportDetail sub : subList) {
						ReportDetailVo subVo = new ReportDetailVo();
						subVo = subVo.toVo(sub);
						if (dlVo.getValue().equals("未检出") || dlVo.getValue().equals("ND")) {
							hasND = true;
						}
						subVoList.add(subVo);
						// 附件
						hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + sub.getTim().getId() + "'  ";
						List<Files> fList = filesDao.list(hql);
						if (null != fList) {
							for (Files f : fList) {
								if (!idList.contains(f.getId())) {
									idList.add(f.getId());
									FilesVo fVo = new FilesVo();
									fVo = fVo.toVo(f);
									fileList.add(fVo);
								}
							}
						}
					}
				}
				dlVo.setSubList(subVoList);
				dtVoList.add(dlVo);
				// 附件
				hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + dl.getTim().getId() + "' ";
				List<Files> fList = filesDao.list(hql);
				if (null != fList) {
					for (Files f : fList) {
						if (!idList.contains(f.getId())) {
							idList.add(f.getId());
							FilesVo fVo = new FilesVo();
							fVo = fVo.toVo(f);
							fileList.add(fVo);
						}
					}
				}
			}
			stVo.setSubList(dtVoList);
			stVoList.add(stVo);
		}
		vo.setDetailList(stVoList);
		// 附件
		String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + vo.getTaskVo().getId() + "' ";
		List<Files> fList = filesDao.list(hql);
		if (null != fList) {
			for (Files f : fList) {
				FilesVo fVo = new FilesVo();
				fVo = fVo.toVo(f);
				fileList.add(fVo);
			}
		}
		vo.setFileList(fileList);
		// 解释说明
		if (vo.getJssm() == null && hasND) {
			vo.setJssm("结果低于方法检出限用“ND”表示，方法检出限见“检测依据。");
		}
		// 检测计划和程序说明
		if (vo.getJhsm() == null) {
			vo.setJhsm(getJhsm(vo));
		}
		// 检测结果
		if (vo.getResult() == null) {
			if (vo.getTaskVo().getPj().equals(Constants.S)) {
				vo.setResult(getResultStr(vo));
			} else {
				vo.setResult("委托检测不予以评价");
			}
		}
		// 检测内容
		if (vo.getJcct() == null) {
			vo.setJcct(getJcct(vo));
		}
		// 检测环境
		if (vo.getJchj() == null) {
			vo.setJchj(getJchj(vo));
		}
		return vo;
	}

	// 职卫报告 编辑页面 数据
	public ReportVo find4Zw(ReportVo vo) throws GlobalException {
		List<FilesVo> fileList = new ArrayList<FilesVo>();
		List<TaskItem> timList = taskItemDao.listByTaskId(vo.getTaskVo().getId());
		List<TaskItemVo> timVoList = new ArrayList<TaskItemVo>();
		for (TaskItem tim : timList) {
			TaskItemVo timVo = new TaskItemVo();
			timVo = timVo.toVo(tim);
			int num = testItemDao.maxItemNum4Tim(tim.getId());
			timVo.setItemList(list4Item(tim.getId(), num));
			timVo.setCount(num);
			timVoList.add(timVo);
			// 附件
			String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + tim.getId() + "' ";
			List<Files> fList = filesDao.list(hql);
			if (null != fList) {
				for (Files f : fList) {
					FilesVo fVo = new FilesVo();
					fVo = fVo.toVo(f);
					fileList.add(fVo);
				}
			}
		}
		vo.setTimList(timVoList);
		// 附件信息
		String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + vo.getTaskVo().getId() + "' ";
		List<Files> fList = filesDao.list(hql);
		if (null != fList) {
			for (Files f : fList) {
				FilesVo fVo = new FilesVo();
				fVo = fVo.toVo(f);
				fileList.add(fVo);
			}
		}
		vo.setFileList(fileList);
		// 检测结果
		if (vo.getResult() == null) {
			vo.setResult(getResultStr4Zw(vo));
		}
		return vo;
	}

	// 获取测试任务下的测试项目集合
	public List<TestItemVo> list4Item(String timId, int num) throws GlobalException {
		List<TestItem> itList = testItemDao.listByTimId(timId);
		List<TestItemVo> itVoList = new ArrayList<>();
		if (null != itList) {
			for (TestItem it : itList) {
				TestItemVo itVo = new TestItemVo();
				itVo = itVo.toVo(it);
				if (it.getType().equals(TaskItem.ITEM_TYPE_XC) && !it.getItemName().contains("粉尘")) {
					itVo.setTrVo(findResult(it.getId(), itVo));
				} else if (it.getItemName().contains("粉尘")) {
					itVo.setTrList(list4Result(it.getId()));
				} else {
					itVo.setTrArry(arry4Result(it.getId(), new String[num], itVo));
				}
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}

	// 获取测试项目的测试结果集合 职卫
	public List<TestResultVo> list4Result(String itId) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		List<TestResultVo> rVoList = new ArrayList<TestResultVo>();
		if (null != rList) {
			for (TestResult r : rList) {
				TestResultVo rVo = new TestResultVo();
				rVo = rVo.toVo(r);
				rVoList.add(rVo);
			}
		}
		return rVoList;
	}

	// 获取测试项目的结果信息
	public TestResultVo findResult4fs(String itId, TestItemVo v, int code) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		TestResultVo rVo = new TestResultVo();
		if (null != rList && rList.size() > 0) {
			rVo = rVo.toVo(rList.get(0));
			rVo.setValue(StrUtils.escapeWord(rVo.getValue()));
			rVo.setValue2(StrUtils.escapeWord(rVo.getValue2()));
			if (StrUtils.isBlankOrNull(v.getWorkHours())) {
				v.setWorkHours(rVo.getSampVo().getWorkHours());
			}
			if (StrUtils.isBlankOrNull(v.getFcType())) {
				v.setFcType(rVo.getSampVo().getFcType());
			}
			if (rVo.getSampVo().getPointVo() != null) {
				if (rVo.getSampVo().getPointVo().getPointCode() == null) {
					rVo.getSampVo().getPointVo().setPointCode(String.valueOf(code));
				}
			}
		}
		return rVo;
	}

	// 获取测试项目的结果信息
	public TestResultVo findResult(String itId, TestItemVo v) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		TestResultVo rVo = new TestResultVo();
		if (null != rList && rList.size() > 0) {
			rVo = rVo.toVo(rList.get(0));
			rVo.setValue(StrUtils.escapeWord(rVo.getValue()));
			rVo.setValue2(StrUtils.escapeWord(rVo.getValue2()));
			if (StrUtils.isBlankOrNull(v.getWorkHours())) {
				v.setWorkHours(rVo.getSampVo().getWorkHours());
			}
			if (StrUtils.isBlankOrNull(v.getFcType())) {
				v.setFcType(rVo.getSampVo().getFcType());
			}
		}
		return rVo;
	}

	// 获取测试项目的测试结果集合
	@SuppressWarnings("unused")
	public String[] arry4Result(String itId, String[] trArry, TestItemVo v) throws GlobalException {
		for (String tr : trArry) {
			tr = "/";
		}
		List<TestResult> rList = testResultDao.listByItId(itId);
		if (null != rList) {
			int i = 0;
			for (TestResult r : rList) {
				if (v.getItemName().contains("粉尘") && null != r.getSamp() && null != r.getSamp().getRecord()) {
					trArry[i] = StrUtils.escapeWord(r.getSamp().getRecord().getV4());
				} else {
					trArry[i] = StrUtils.escapeWord(r.getValue());
				}
				if (StrUtils.isBlankOrNull(v.getWorkHours())) {
					v.setWorkHours(r.getSamp().getWorkHours());
				}
				if (StrUtils.isBlankOrNull(v.getFcType())) {
					v.setFcType(r.getSamp().getFcType());
				}
				i++;
			}
		}
		return trArry;
	}

	// 检测环境
	public String getJchj(ReportVo vo) {
		String qw = "气温：";
		String sd = "相对湿度：";
		String qy = "气压：";
		String tq = "天气：";
		String fs = "风速：";
		String fx = "风向：";
		List<SampCyd> cydList = sampCydDao.listByTaskId(vo.getTaskVo().getId());
		for (SampCyd cyd : cydList) {
			if (cyd.getType().equals(EnumCyd.HJ_WQ) || cyd.getType().equals(EnumCyd.HJ_YQ)) {
				// 气 获取气象
				List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
				for (Sampling samp : sampList) {
					if (samp.getRecord() != null) {
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo6()) && !tq.contains(samp.getRecord().getDemo6())) {
							tq += samp.getRecord().getDemo6() + "，";
						}
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo7()) && !fx.contains(samp.getRecord().getDemo7())) {
							fx += samp.getRecord().getDemo7() + "，";
						}
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo8()) && !fs.contains(samp.getRecord().getDemo8())) {
							fs += samp.getRecord().getDemo8() + "m/s，";
						}
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo9()) && !qy.contains(samp.getRecord().getDemo9())) {
							qy += samp.getRecord().getDemo9() + "kPa，";
						}
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo10()) && !qw.contains(samp.getRecord().getDemo10())) {
							qw += samp.getRecord().getDemo10() + "℃，";
						}
						if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo11()) && !sd.contains(samp.getRecord().getDemo11())) {
							sd += samp.getRecord().getDemo11() + "%，";
						}
					}
				}
			} else {
				if (!StrUtils.isBlankOrNull(cyd.getTx()) && !tq.contains(cyd.getTx())) {
					tq += cyd.getTx() + "，";
				}
				if (!StrUtils.isBlankOrNull(cyd.getFx()) && !fx.contains(cyd.getFx())) {
					fx += cyd.getFx() + "，";
				}
				if (!StrUtils.isBlankOrNull(cyd.getFs()) && !fs.contains(cyd.getFs())) {
					fs += cyd.getFs() + "m/s，";
				}
				if (!StrUtils.isBlankOrNull(cyd.getQy()) && !qy.contains(cyd.getQy())) {
					qy += cyd.getQy() + "kPa，";
				}
				if (!StrUtils.isBlankOrNull(cyd.getQw()) && !qw.contains(cyd.getQw())) {
					qw += cyd.getQw() + "℃，";
				}
				if (!StrUtils.isBlankOrNull(cyd.getSd()) && !sd.contains(cyd.getSd())) {
					sd += cyd.getSd() + "%，";
				}
			}
		}
		String s = qw + sd + qy + tq + fs + fx;
		return s;
	}

	// 职卫 结论
	public String getResultStr4Zw(ReportVo vo) {
		String result = "";
		if (vo.getTaskType().equals(EnumBus.DQ.getName())) {
			result = "检测结果表明：\r\n\t";
			int n = 1;
			String standIds = vo.getStandIds();
			List<Pstandard> standList = pstandardDao.listByIds(standIds);
			for (Pstandard pstd : standList) {
				String rt = reportDetailDao.findItems(vo.getId(), pstd.getId(), TaskItem.RESULT_NO);// 不合格项目
				if (pstd.getCode().equals("GBZ 2.1-2007")) {
					if (!StrUtils.isBlankOrNull(rt)) {
						result += "除" + rt + "不符合国家标准要求外，其余检测岗位（检测点）接触工作场所空气中化学有害因素浓度均符合GBZ 2.1-2007《工作场所有害因素职业接触限值 第 1 部分：化学有害因素》的要求；";
						n++;
					} else {
						result += "所有检测岗位（检测点）接触工作场所空气中化学有害因素浓度均符合GBZ 2.1-2007《工作场所有害因素职业接触限值 第 1 部分：化学有害因素》的要求；";
					}
				} else if (pstd.getCode().equals("GBZ 2.2-2007")) {
					if (!StrUtils.isBlankOrNull(rt)) {
						result += "除" + rt + "不符合国家标准要求外，其余检测岗位（检测点）物理因素强度均符合GBZ 2.2-2007《工作场所有害因素职业接触限值 第 2 部分：物理因素》的要求；";
						n++;
					} else {
						result += "所有检测岗位（检测点）物理因素强度均符合GBZ 2.2-2007《工作场所有害因素职业接触限值 第 2 部分：物理因素》的要求；";
					}
				} else {
					if (!StrUtils.isBlankOrNull(rt)) {
						result += "除" + rt + "不符合国家标准要求外，其余检测岗位（检测点）均符合" + pstd.getCode() + "《" + pstd.getName() + "》的要求；";
						n++;
					} else {
						result += "所有检测岗位（检测点）均符合" + pstd.getCode() + "《" + pstd.getName() + "》的要求；";
					}
				}
			}
			if (n > 1) {
				result += "\r\n\t超标分析：";
			}
		} else {
			String itemName = vo.getTaskVo().getItemNames();
			if (StrUtils.isBlankOrNull(itemName)) {
				itemName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			} else {
				itemName = itemName.replaceAll(",", "、");
			}
			String stands = "";
			if (!StrUtils.isBlankOrNull(vo.getStandIds())) {
				List<Pstandard> sdList = pstandardDao.listByIds(vo.getStandIds());
				for (Pstandard psd : sdList) {
					stands += psd.getCode() + "《" + psd.getName() + "》、";
				}
				if (stands.length() > 1) {
					stands = stands.substring(0, stands.length() - 1);
				}
			} else {
				stands = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp";
			}
			result += "1.检测状态描述：";
			result += "\r\n\t在检测过程中，工作线正常运行。";
			result += "\r\n2.检测结果汇总和结论：";
			result += "\r\n\t我公司受" + vo.getCustVo().getWtName() + "的委托，按照" + stands + "等标准要求，对该公司部分工作场所的" + itemName + "进行检测，具体结果分析见下表：";
		}
		return result;
	}

	// 组织检测结果 环境
	public String getResultStr(ReportVo vo) {
		String result = "检测结果表明：";
		List<SampType> stList = sampTypeDao.listByIds(vo.getSampTypeId());
		int n = 1;
		String standIds = vo.getStandIds();
		if (StrUtils.isBlankOrNull(standIds)) {
			return "委托检测不予以评价";
		}
		List<Pstandard> standList = pstandardDao.listByIds(standIds);
		for (SampType st : stList) {
			String f = "";
			if (!st.getType().equals(Constants.SAMP_S)) {
				f = "样品检测项目";
			} else {
				f = "等效A声级检测值";
			}
			String pass = "";
			String noPass = "";
			for (Pstandard pstd : standList) {
				String rt1 = reportDetailDao.findItems(vo.getId(), st.getId(), pstd.getId(), TaskItem.RESULT_YES);// 合格项目
				String rt2 = reportDetailDao.findItems(vo.getId(), st.getId(), pstd.getId(), TaskItem.RESULT_NO);// 不合格项目
				if (!StrUtils.isBlankOrNull(rt1)) {
					if (!st.getType().equals(Constants.SAMP_S)) {
						pass += rt1;
					}
					if (st.getType().equals(Constants.SAMP_Q)) {
						pass += "的排放浓度";
					}
					pass += "不超过《" + pstd.getName() + "》（" + pstd.getCode() + "）限值要求，";
				}
				if (!StrUtils.isBlankOrNull(rt2)) {
					if (!st.getType().equals(Constants.SAMP_S)) {
						noPass += rt2;
					}
					if (st.getType().equals(Constants.SAMP_Q)) {
						noPass += "的排放浓度";
					}
					noPass += "超过《" + pstd.getName() + "》（" + pstd.getCode() + "）限值要求，";
				}
			}
			if (n == 1 && (!StrUtils.isBlankOrNull(pass) || !StrUtils.isBlankOrNull(noPass))) {
				result += "\r\n\t本次" + st.getName() + f + pass + noPass;
				if (result.endsWith("，")) {
					result = result.substring(0, result.length() - 1);
				}
				result += "；";
			} else if (!StrUtils.isBlankOrNull(pass) || !StrUtils.isBlankOrNull(noPass)) {
				result += "\r\n\t" + st.getName() + f + pass + noPass;
				if (result.endsWith("，")) {
					result = result.substring(0, result.length() - 1);
				}
				result += "；";
			}
			n++;
		}
		return result;
	}

	// 组织检测内容
	public String getJcct(ReportVo vo) {
		String result = "";
		List<SampType> stList = sampTypeDao.listByIds(vo.getSampTypeId());
		int n = 1;
		for (SampType st : stList) {
			List<ReportDetail> rdList = reportDetailDao.listBySampTypeId(vo.getId(), st.getId());
			Set<String> itemSet = new HashSet<String>();
			for (ReportDetail rd : rdList) {
				itemSet.add(rd.getItemName());
			}
			if (itemSet.size() == 0) {
				continue;
			}
			if (st.getName().contains("噪声")) {
				if (n > 1) {
					result += "\r\n" + n + "." + String.join(",", itemSet) + "：等效A声级Leq [dB(A)]；";
				} else {
					result += "1." + String.join(",", itemSet) + "：等效A声级Leq [dB(A)]；";
				}
			} else {
				if (n > 1) {
					result += "\r\n" + n + "." + st.getName() + "：" + String.join(",", itemSet) + "；";
				} else {
					result += "1." + st.getName() + "：" + String.join(",", itemSet) + "；";
				}
			}
			n++;
		}
		return result;
	}

	// 检测计划和程序说明 采样方法集合
	public String getJhsm(ReportVo vo) {
		String result = "《" + vo.getCustVo().getCustName() + "样品采样计划单》";
		List<SampCyd> cydList = sampCydDao.listByTaskId(vo.getTaskVo().getId());
		String ids = "";
		for (SampCyd cyd : cydList) {
			if (!StrUtils.isBlankOrNull(cyd.getCyStandId())) {
				ids += "," + cyd.getCyStandId();
			}
		}
		if (ids.length() > 1) {
			ids = ids.substring(1);
		}
		List<SampSource> ssList = sampSourceDao.listByIds(ids);
		for (SampSource ss : ssList) {
			result += "\r\n《" + ss.getName() + "》(" + ss.getCode() + ")";
		}
		return result;
	}

	@Override
	public ReportVo find4Report(ReportVo vo) throws GlobalException {
		String uuid = vo.getUuid();
		Report po = reportDao.findById(vo.getId());
		vo = vo.toVo(po);
		if (!StrUtils.isBlankOrNull(uuid) && uuid.equals("dq")) {
			getDate4Dq(po, vo);// 获取定期报告汇总文件
		} else if (vo.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			if (vo.getTaskType().equals(EnumBus.HP.getName())) {
				getData4HjPj(po, vo);// 环评
			} else {
				getData4Hj(po, vo);// 环境委托
			}
		} else if (vo.getSampType().equals(EnumBus.SAMP_TYPE_ZW) || vo.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			if (vo.getTaskType().equals(Constants.REPORT_TASKTYPE)) {
				getDate4Fs(po, vo);
				vo.setTemplate("bus-report-fs.ftl");
			} else {
				getDate4Zw(po, vo);// 职卫报告
			}
		}

		// 通用格式化处理
		if (null != vo.getTaskVo()) {
			String cynames = "";
			if (null != vo.getTaskVo().getCyName()) {
				if (vo.getTaskVo().getCyName().contains(",")) {
					cynames = vo.getTaskVo().getCyName();
					cynames = cynames.replaceAll(",", "、").replaceAll("，", "、");// 将中英文逗号转成顿号
					vo.getTaskVo().setCyName(cynames);
				}
			}
		}
		if (vo.getTaskType().equals(EnumBus.HP.getName()) || vo.getTaskType().equals(EnumBus.RC.getName())) {
			vo.setTaskType(EnumBus.WT.getName());
		}
		if (null != vo.getResult()) {
			String result = vo.getResult();
			result = result.replace("无组织废气", "废气（无组织）");
			result = result.replace("有组织废气", "废气（有组织）");
			vo.setResult(result.replace("\r\n", "<w:p></w:p>"));
		}
		if (vo.getJcct() != null) {
			String jcnr = vo.getJcct();
			jcnr = jcnr.replace("无组织废气", "废气（无组织）");
			jcnr = jcnr.replace("有组织废气", "废气（有组织）");
			jcnr = jcnr.replace("\r\n", "<w:p></w:p>");
			vo.setJcct(jcnr);
		}
		if (null != vo.getJhsm()) {
			vo.setJhsm(vo.getJhsm().replace("\r\n", "<w:p></w:p>"));
		}
		return vo;
	}

	// 环境 报告 部分组装 环评报告
	public void getData4HjPj(Report po, ReportVo vo) throws GlobalException {
		// 获取分析方法
		getMethod(po, vo);
		// 获取仪器信息集合
		List<Appara> appList = apparaDao.listByIds(vo.getAppIds());
		List<RappVo> appVoList = new ArrayList<RappVo>();
		if (null != appList) {
			for (Appara appara : appList) {
				RappVo appVo = new RappVo();
				appVo.setAppName(appara.getName());
				appVo.setAppRule(appara.getSpec());
				appVo.setAppCode(appara.getNo());
				appVoList.add(appVo);
			}
		}
		vo.setAppList(appVoList);
		// 无组装气
		getTab4QwHp(po, vo);
		// 水监测结果
		getTab4WPj(po, vo);
		// 声
		getTab4S(po, vo);
		// 土
		getTab4TPj(po, vo);
		// 其他属性
		String acceptDate = vo.getTaskVo().getDate();
		if (!StrUtils.isBlankOrNull(acceptDate)) {
			acceptDate = acceptDate.substring(0, 10);
			vo.getTaskVo().setDate(acceptDate.replace("-", "."));
		}
		if (!StrUtils.isBlankOrNull(vo.getReportDate())) {
			String rdata = vo.getReportDate();
			vo.setReportDate(DateUtils.getChineseDate(rdata));
			vo.setReportDateStr(DateUtils.formatToChineseDate(rdata));
		}
		if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyDate())) {
			String cyDate = vo.getTaskVo().getCyDate().substring(0, 10);
			String cyEndDate = null;
			if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyEndDate())) {
				cyEndDate = vo.getTaskVo().getCyEndDate().substring(0, 10);
				cyEndDate = DateUtils.getChineseDate(cyEndDate);
			}
			cyDate = DateUtils.getChineseDate(cyDate);
			if (null != cyEndDate && !cyEndDate.equals(cyDate)) {
				cyDate = cyDate + "~" + cyEndDate;
			}
			vo.setCyDate(cyDate);
		}
		String testDate = vo.getTestDate();
		String tesEndtDate = vo.getTestEndDate();
		if (!StrUtils.isBlankOrNull(testDate)) {
			testDate = DateUtils.getChineseDate(testDate);
		}
		if (!StrUtils.isBlankOrNull(tesEndtDate)) {
			tesEndtDate = DateUtils.getChineseDate(tesEndtDate);
		}
		if (!testDate.equals(tesEndtDate)) {
			testDate = testDate + "~" + tesEndtDate;
		}
		vo.setTestDate(testDate);
		if (!StrUtils.isBlankOrNull(vo.getSampTypeName())) {
			String sampTypeName = vo.getSampTypeName();
			sampTypeName = sampTypeName.replace(",", "、");
			sampTypeName = sampTypeName.replace("有组织废气", "废气（有组织）");
			sampTypeName = sampTypeName.replace("无组织废气", "废气（无组织）");
			vo.setSampTypeName(sampTypeName);
		}
	}

	// 环境 报告 部分组装
	public void getData4Hj(Report po, ReportVo vo) throws GlobalException {
		// 获取分析方法
		getMethod(po, vo);
		// 获取仪器信息集合
		List<Appara> appList = apparaDao.listByIds(vo.getAppIds());
		List<RappVo> appVoList = new ArrayList<RappVo>();
		if (null != appList) {
			for (Appara appara : appList) {
				RappVo appVo = new RappVo();
				appVo.setAppName(appara.getName());
				appVo.setAppRule(appara.getSpec());
				appVo.setAppCode(appara.getNo());
				appVoList.add(appVo);
			}
		}
		vo.setAppList(appVoList);
		// 有组装 气
		getTab4Q(po, vo);
		// 无组装气
		getTab4QW(po, vo);
		// 水监测结果
		getTab4W(po, vo);
		// 声
		getTab4S(po, vo);
		// 土
		getTab4T(po, vo);
		// 其他属性
		String acceptDate = vo.getTaskVo().getDate();
		if (!StrUtils.isBlankOrNull(acceptDate)) {
			acceptDate = acceptDate.substring(0, 10);
			vo.getTaskVo().setDate(acceptDate.replace("-", "."));
		}
		if (!StrUtils.isBlankOrNull(vo.getReportDate())) {
			String rdata = vo.getReportDate();
			vo.setReportDate(DateUtils.getChineseDate(rdata));
			vo.setReportDateStr(DateUtils.formatToChineseDate(rdata));
		}
		if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyDate())) {
			String cyDate = vo.getTaskVo().getCyDate().substring(0, 10);
			String cyEndDate = null;
			if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyEndDate())) {
				cyEndDate = vo.getTaskVo().getCyEndDate().substring(0, 10);
				cyEndDate = DateUtils.getChineseDate(cyEndDate);
			}
			cyDate = DateUtils.getChineseDate(cyDate);
			if (null != cyEndDate && !cyEndDate.equals(cyDate)) {
				cyDate = cyDate + "~" + cyEndDate;
			}
			vo.setCyDate(cyDate);
		}
		String testDate = vo.getTestDate();
		String tesEndtDate = vo.getTestEndDate();
		if (!StrUtils.isBlankOrNull(testDate)) {
			testDate = DateUtils.getChineseDate(testDate);
		}
		if (!StrUtils.isBlankOrNull(tesEndtDate)) {
			tesEndtDate = DateUtils.getChineseDate(tesEndtDate);
		}
		if (!testDate.equals(tesEndtDate)) {
			testDate = testDate + "~" + tesEndtDate;
		}
		vo.setTestDate(testDate);
		if (!StrUtils.isBlankOrNull(vo.getSampTypeName())) {
			String sampTypeName = vo.getSampTypeName();
			sampTypeName = sampTypeName.replace(",", "、");
			sampTypeName = sampTypeName.replace("有组织废气", "废气（有组织）");
			sampTypeName = sampTypeName.replace("无组织废气", "废气（无组织）");
			vo.setSampTypeName(sampTypeName);
		}
	}

	// 组装 环境报告 分析方法
	@SuppressWarnings("unchecked")
	public void getMethod(Report po, ReportVo vo) throws GlobalException {
		List<SampType> sampList = sampTypeDao.listByIds(po.getSampTypeId(), "sort", OrderCondition.ORDER_ASC);
		List<RsampVo> sampVoList = new ArrayList<RsampVo>();
		Set<String> appIdsSet = new HashSet<String>();// 收集仪器信息 放入报告对象
		String testDate = "";// 收集检测开始结束日期放入报告对象
		String testEndDate = "";
		for (SampType samp : sampList) {
			RsampVo sampVo = new RsampVo();
			String sampName = samp.getName();
			sampName = sampName.replace("无组织废气", "废气（无组织）");
			sampName = sampName.replace("有组织废气", "废气（有组织）");
			sampVo.setSampName(sampName);
			String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + vo.getId()
					+ "' AND sampTypeId ='" + samp.getId() + "' AND parent.id is null GROUP BY itemId ORDER BY methodId asc";
			List<ReportDetail> detialList = reportDetailDao.list(hql);
			List<RItemVo> itemList = new ArrayList<RItemVo>();
			String itemName = null;
			for (ReportDetail dt : detialList) {
				String methodId = dt.getMethodId();
				String sql = "select DISTINCT CONCAT('《',IFNULL(t1.name,''),'》（',IFNULL(t1.code,''),'）') AS mname from v_bus_report_detail t0 inner join v_init_method t1 on t0.method_id = t1.id  where t0.method_id in('"
						+ methodId.replace(",", "','") + "')";
				List<String> list = sampTypeDao.queryBySql(sql); // 检测方法标准号
				String methodName = "";
				if (list != null) {
					for (String obj : list) {
						methodName += "、" + obj;// 方法后面跟编号
					}
				}
				if (methodName.length() > 1) {
					methodName = methodName.substring(1);
				}
				RItemVo itVo = new RItemVo();
				if (null != samp.getName() && samp.getName().contains("噪声")) {
					itemName = dt.getItemName();
					itVo.setItemName("等效A声级Leq [dB(A)]");
				} else {
					itVo.setItemName(dt.getItemName());
				}
				itVo.setMethodName(methodName);
				boolean f = reportDetailDao.checkJcx(vo.getId(), dt.getItemId());
				if (f) {
					itVo.setLimitLine("/");
				} else {
					itVo.setLimitLine(dt.getLimitLine());
				}
				itemList.add(itVo);
				// 获取分析日期范围
				String testTime = dt.getTestTime().substring(0, 10);
				if (testDate == null) {
					testDate = testTime;
				}
				if (testEndDate == null) {
					testEndDate = testTime;
				}
				if (DateUtils.getIntevalDays(testDate, testTime) < 0) {
					testDate = testTime;
				}
				if (DateUtils.getIntevalDays(testEndDate, testTime) > 0) {
					testEndDate = testTime;
				}
				if (StrUtils.isNotBlankOrNull(dt.getAppId())) {
					String appIds[] = dt.getAppId().split(",");
					for (String appId : appIds) {
						if (appId.trim().length() > 0) {
							appIdsSet.add(appId);
						}
					}
				}
			}
			if (sampName.equals("噪声")) {
				sampVo.setSampName(itemName);
			}
			sampVo.setItemList(itemList);
			sampVoList.add(sampVo);
		}
		vo.setFfList(sampVoList);
		if (!StrUtils.isBlank(testDate)) {// 检测日期非空才赋给模板值
			vo.setTestDate(testDate);
		}
		if (!StrUtils.isBlank(testEndDate)) {
			vo.setTestEndDate(testEndDate);
		}
		vo.setAppIds(String.join(",", appIdsSet));
	}

	// 环境 水 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4W(Report po, ReportVo vo) throws GlobalException {
		List<SampType> wsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_W);
		if (null != wsampList && wsampList.size() > 0) {
			List<RtabVo> wtabList = new ArrayList<>();
			for (SampType sampType : wsampList) {// 一级tab循环 按样品类型
				RtabVo tabVo = new RtabVo();
				tabVo.setSampName(sampType.getName());
				tabVo.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				// 检查是否存在水的 点位
				String jpql = "FROM " + taskPointDao.getEntityName(TaskPoint.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
						+ "' AND sampTypeId='" + sampType.getId() + "' ORDER BY sort ASC";
				List<TaskPoint> pointList = taskPointDao.list(jpql);
				if (null != pointList && pointList.size() > 0) {
					List<RtabVo> tpList = new ArrayList<RtabVo>();
					int page = 1;
					for (TaskPoint taskPoint : pointList) {
						RtabVo ptTabVo = new RtabVo();// 点位tab
						ptTabVo.setPointName(taskPoint.getPointName());
						ptTabVo.setPage(page);
						int cols = samplingDao.maxNum4PointDay(taskPoint.getId());
						if (cols == 0)
							cols = 1;
						ptTabVo.setCols(cols);
						ptTabVo.setColWth(5000 / cols);
						String[] zhdList = new String[cols];
						ptTabVo.setHeadList(zhdList);
						String limit = "";
						// 开始tab内部 循环 日期 块循环 项目循环
						// 获取检测点位采样日期集合
						String sql = "select distinct(cy_date) from v_bus_report_detail where is_del=" + Po.N + "  AND point_id='" + taskPoint.getId()
								+ "' order by cy_date asc";
						List<String> rqList = taskPointDao.queryBySql(sql);
						List<RtabVo> dtList = new ArrayList<RtabVo>();
						for (String date : rqList) {// 二级 日期块循环
							RtabVo dtTabVo = new RtabVo();// 日期块
							dtTabVo.setDate(DateUtils.getChineseDate(date));
							dtTabVo.setPointName(taskPoint.getPointName());
							// 查询该日期下的样品
							jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id='" + taskPoint.getId()
									+ "' AND zkType='" + Sampling.SAMP_TYPE_PT + "' AND cyDate like '" + date + "' group by sampCode ORDER BY sampCode ASC";
							List<Sampling> samList = samplingDao.query(jpql).getResultList();
							// 样品编号集合
							String[] headList = new String[cols];
							// 时间集合
							String[] dateList = new String[cols];
							// 水温集合
							String[] swList = new String[cols];
							// 性状集合
							String[] xzList = new String[cols];
							int n = 0;
							for (Sampling samp : samList) {
								headList[n] = samp.getSampCode();
								dateList[n] = samp.getCyTime();
								// swList[n] = samp.getRecord().getDemo4();
								xzList[n] = samp.getXz();
								n++;
							}
							dtTabVo.setHeadList(headList);
							dtTabVo.setDateList(dateList);
							dtTabVo.setDtList(swList);
							dtTabVo.setAddList(xzList);
							// 项目循环块
							List<RtabRowVo> rowList = new ArrayList<RtabRowVo>();
							String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND pointId ='"
									+ taskPoint.getId() + "' AND sampTypeId='" + sampType.getId() + "' AND cyDate like'" + date + "' ";
							hql += "GROUP BY itemId ORDER BY sort asc";// 当日检测项目
							List<ReportDetail> itemList = reportDetailDao.list(hql);
							if (null != itemList) {
								// 所有检测结果
								String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND pointId ='"
										+ taskPoint.getId() + "' AND sampTypeId='" + sampType.getId() + "' AND cyDate like'" + date + "' ";
								List<ReportDetail> dataList = reportDetailDao.list(subhql);
								for (ReportDetail rd : itemList) {
									RtabRowVo itemRow = new RtabRowVo();
									itemRow.setItemName(rd.getItemName());
									itemRow.setUnit(rd.getUnit());
									itemRow.setLimt(rd.getLimited());
									String[] valueArry = new String[cols];
									for (int i = 0; i < headList.length; i++) {
										if (StrUtils.isBlankOrNull(headList[i])) {
											continue;
										}
										for (ReportDetail dt : dataList) {
											if ("水温".equals(rd.getItemName()) && headList[i].equals(dt.getSampCode())) {
												swList[i] = rd.getValue();
											} else if (dt.getItemName().equals(rd.getItemName()) && headList[i].equals(dt.getSampCode())) {
												valueArry[i] = rd.getValue();
											}
										}
									}
									itemRow.setValueList(valueArry);
									if (!rd.getItemName().equals("水温")) {
										rowList.add(itemRow);
									}
									String standCode = null;
									if (!StrUtils.isBlankOrNull(rd.getStandId())) {
										List<Pstandard> pdlist = pstandardDao.listByIds(rd.getStandId());
										if (null != pdlist && pdlist.size() > 0) {
											standCode = pdlist.get(0).getCode();
										}
									}
									if (!StrUtils.isBlankOrNull(standCode) && !limit.contains(standCode)) {
										limit += "," + standCode;
									}
								}
							}
							dtTabVo.setRowList(rowList);
							dtList.add(dtTabVo);
						}
						// tab内部结束
						ptTabVo.setTabList(dtList);
						if (limit.length() > 0) {
							limit = limit.substring(1);
						}
						ptTabVo.setRemark(limit);
						tpList.add(ptTabVo);
						page++;
					}
					tabVo.setTabList(tpList);
					wtabList.add(tabVo);
				}
			}
			vo.setWtabList(wtabList);
		}
	}

	// 环境 -评价 水 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4WPj(Report po, ReportVo vo) throws GlobalException {
		List<SampType> wsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_W);
		if (null != wsampList && wsampList.size() > 0) {
			List<RtabVo> wtabList = new ArrayList<RtabVo>();
			for (SampType st : wsampList) {// 一级tab循环 按样品类型
				RtabVo tabVo = new RtabVo();
				tabVo.setSampName(st.getName());
				tabVo.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + st.getId() + "' order by cy_date,cy_time asc";
				List<String> rqList = taskPointDao.queryBySql(sql);
				if (rqList != null) {
					List<RtabVo> dateList = new ArrayList<RtabVo>();
					int page = 1;
					for (String date : rqList) {// 二级 tab 循环 按 日期
						RtabVo dttabVo = new RtabVo();
						dttabVo.setPage(page);
						dttabVo.setDate(DateUtils.getChineseDate(date));
						String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
								+ "' AND sampTypeId='" + st.getId() + "' AND cyDate like '" + date + "%' AND zkType='" + Sampling.SAMP_TYPE_PT
								+ "' GROUP BY sampCode ORDER BY sampCode ASC";
						List<Sampling> samList = samplingDao.list(jpql);
						if (samList != null && samList.size() > 0) {
							int cols = samList.size();
							dttabVo.setCols(cols);
							dttabVo.setColWth(6400 / cols);
							String[] ccList = new String[cols];// 水位
							String[] addList = new String[cols];// 采样点
							String[] tzList = new String[cols];// 时间
							String[] dtList = new String[cols];// 样品状态
							String[] headList = new String[cols];
							int n = 0;
							for (Sampling sampling : samList) {
								addList[n] = sampling.getPointName();
								ccList[n] = sampling.getRecord().getDemo1();
								tzList[n] = sampling.getCyTime();
								dtList[n] = (sampling.getXz());
								headList[n] = sampling.getSampCode();
								n++;
							}
							List<RtabRowVo> dataList = new ArrayList<RtabRowVo>();
							// 检测项目
							String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' ";
							hql += "GROUP BY itemId ORDER BY sort asc";
							List<ReportDetail> itemList = reportDetailDao.list(hql);
							// 检测值
							String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' order by cyTime asc";
							List<ReportDetail> rdtList = reportDetailDao.list(subhql);
							if (null != itemList && itemList.size() > 0) {
								for (ReportDetail item : itemList) {
									RtabRowVo itemVo = new RtabRowVo();
									itemVo.setItemName(item.getItemName());
									itemVo.setUnit(item.getUnit());
									String[] valueArr = new String[headList.length];
									for (int i = 0; i < headList.length; i++) {
										if (StrUtils.isBlankOrNull(headList[i])) {
											continue;
										}
										for (ReportDetail rdt : rdtList) {
											if (rdt.getItemName().equals(item.getItemName()) && rdt.getSampCode().equals(headList[i])) {
												valueArr[i] = rdt.getValue();
											}
										}
									}
									itemVo.setValueList(valueArr);
									dataList.add(itemVo);
								}
							}
							dttabVo.setRowList(dataList);// 结果
							dttabVo.setHeadList(headList);// 标题
							dttabVo.setDtList(dtList);// 样品状态
							dttabVo.setDateList(tzList);// 时间
							dttabVo.setCeList(ccList);// 水位
							dttabVo.setAddList(addList);// 采样地点
							dateList.add(dttabVo);
							page++;
						}
					}
					tabVo.setTabList(dateList);
				}
				wtabList.add(tabVo);
			}
			vo.setWtabList(wtabList);
		}
	}

	// 环评 无组织气 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4QwHp(Report po, ReportVo vo) throws GlobalException {
		// 无组织气 tab循环按项目， 每个tab一个项目，每个tab内部按日期循环结果块
		SampType wst = sampTypeDao.findByName(Constants.SAMP_Q_WZZ);
		String pointName = "";
		if (wst != null) {
			String hql = "Select itemName FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
					+ po.getId() + "' AND sampTypeId='" + wst.getId() + "' AND cyHours >=12 ";
			hql += "GROUP BY itemId ORDER BY sort asc";
			List<String> ritemList = reportDetailDao.query(hql).getResultList();// 日均值
			String hql2 = "Select itemName FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
					+ po.getId() + "' AND sampTypeId='" + wst.getId() + "' AND cyHours <=9 AND cyHours >=7 ";
			hql2 += "GROUP BY itemId ORDER BY sort asc";
			List<String> titemList = reportDetailDao.query(hql2).getResultList();// 8小时均值
			String hql3 = "Select itemName FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
					+ po.getId() + "' AND sampTypeId='" + wst.getId() + "' AND cyHours <=2 ";
			hql3 += "GROUP BY itemId ORDER BY sort asc";
			List<String> xsitemList = reportDetailDao.query(hql3).getResultList();// 8小时均值
			if (null != ritemList && ritemList.size() > 0 || null != titemList && titemList.size() > 0 || null != xsitemList && xsitemList.size() > 0) {
				RtabVo wzqtab = new RtabVo();// 无组织气开始
				wzqtab.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				wzqtab.setSampName("环境空气");
				List<RtabVo> wzqtabList = new ArrayList<RtabVo>();
				int page = 1;
				// 日均值
				if (null != ritemList && ritemList.size() > 0) {
					RtabVo tabVo = new RtabVo();
					tabVo.setPage(page);
					tabVo.setType("24");
					int cols = ritemList.size();// 列
					tabVo.setCols(cols);
					tabVo.setColWth(6000 / cols);
					String[] headList = new String[cols];
					int n = 0;
					for (String item : ritemList) {// 表头
						headList[n] = item;
						n++;
					}
					tabVo.setHeadList(headList);
					// 获取日均值所有结果
					String jpql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='" + po.getId()
							+ "' AND sampTypeId='" + wst.getId() + "' AND cyHours >=12 ";
					List<ReportDetail> rtList = reportDetailDao.list(jpql);

					List<RtabRowVo> ptList = new ArrayList<>();
					String sql = "select rd.point_name,pt.point_code FROM v_bus_report_detail rd left join v_bus_task_point pt on rd.point_id=pt.id WHERE rd.is_del='"
							+ Po.N + "' AND rd.report_id ='" + po.getId() + "' AND rd.samp_type_id='" + wst.getId() + "' AND rd.cy_hours >=12 ";
					sql += "GROUP BY rd.point_id ORDER BY rd.sort asc";
					List<Object[]> objList = reportDetailDao.queryBySql(sql);
					for (Object[] obj : objList) {// 结果 点位循环
						RtabRowVo ptVo = new RtabRowVo();
						ptVo.setPointName(String.valueOf(obj[0]));
						ptVo.setSampCode(StrUtils.null2Str(obj[1]));// 代替点位编号
						if (!StrUtils.isBlankOrNull(ptVo.getPointName()) && !pointName.contains(ptVo.getPointName())) {
							pointName += "," + ptVo.getSampCode() + ptVo.getPointName();
						}
						// 日期循环
						List<RtabRowVo> dateList = new ArrayList<>();
						String sql1 = "select cy_date FROM v_bus_report_detail WHERE is_del='" + Po.N + "' AND report_id ='" + po.getId()
								+ "' AND samp_type_id='" + wst.getId() + "' AND point_name like '" + ptVo.getPointName() + "' AND cy_hours >=12 ";
						sql1 += "GROUP BY cy_date ORDER BY cy_date asc";
						List<String> dtList = reportDetailDao.queryBySql(sql1);
						if (null != dtList && dtList.size() > 0) {
							for (String date : dtList) {
								RtabRowVo dtVo = new RtabRowVo();
								dtVo.setDate(DateUtils.getChineseDate(date));
								String[] valueList = new String[cols];
								int i = 0;
								for (String hd : headList) {
									for (ReportDetail rt : rtList) {
										if (rt.getPointName().equals(ptVo.getPointName()) && rt.getCyDate().equals(date) && rt.getItemName().equals(hd)) {
											valueList[i] = rt.getValue();
											break;
										}
									}
									i++;
								}
								dtVo.setValueList(valueList);
								dateList.add(dtVo);
							}
							ptVo.setRowList(dateList);
							ptList.add(ptVo);
						}
					}
					tabVo.setRowList(ptList);
					wzqtabList.add(tabVo);
					page++;
				}
				// 8小时均值
				if (null != titemList && titemList.size() > 0) {
					RtabVo tabVo = new RtabVo();// 循环tab
					tabVo.setPage(page);
					tabVo.setType("8");
					int cols = titemList.size();// 列
					tabVo.setCols(cols);
					tabVo.setColWth(5000 / cols);
					String[] headList = new String[cols];
					int n = 0;
					for (String item : titemList) {
						headList[n] = item;
						n++;
					}
					tabVo.setHeadList(headList);
					// 获取8小时均值所有结果
					String jpql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='" + po.getId()
							+ "' AND sampTypeId='" + wst.getId() + "' AND cyHours <=9 AND cyHours >=7 ";
					List<ReportDetail> rtList = reportDetailDao.list(jpql);
					List<RtabRowVo> ptList = new ArrayList<>();
					String sql = "select rd.point_name,pt.point_code FROM v_bus_report_detail rd left join v_bus_task_point pt on rd.point_id=pt.id WHERE rd.is_del='"
							+ Po.N + "' AND rd.report_id ='" + po.getId() + "' AND rd.samp_type_id='" + wst.getId()
							+ "' AND rd.cy_hours <=9 AND rd.cy_hours >=7 ";
					sql += "GROUP BY rd.point_id ORDER BY rd.sort asc";
					List<Object[]> objList = reportDetailDao.queryBySql(sql);
					for (Object[] obj : objList) {// 结果 点位循环
						RtabRowVo ptVo = new RtabRowVo();
						ptVo.setPointName(String.valueOf(obj[0]));
						ptVo.setSampCode(StrUtils.null2Str(obj[1]));// 代替点位编号
						if (!StrUtils.isBlankOrNull(ptVo.getPointName()) && !pointName.contains(ptVo.getPointName())) {
							pointName += "," + ptVo.getSampCode() + ptVo.getPointName();
						}
						// 日期循环
						List<RtabRowVo> dateList = new ArrayList<>();
						String sql1 = "select cy_date FROM v_bus_report_detail WHERE is_del='" + Po.N + "' AND report_id ='" + po.getId()
								+ "' AND samp_type_id='" + wst.getId() + "' AND point_name like '" + ptVo.getPointName()
								+ "' AND cy_hours <=9 AND cy_hours >=7 ";
						sql1 += "GROUP BY cy_date ORDER BY cy_date asc";
						List<String> dtList = reportDetailDao.queryBySql(sql1);
						if (null != dtList && dtList.size() > 0) {
							for (String date : dtList) {
								RtabRowVo dtVo = new RtabRowVo();
								dtVo.setDate(DateUtils.getChineseDate(date));
								String[] valueList = new String[cols];
								int i = 0;
								for (String hd : headList) {
									for (ReportDetail rt : rtList) {
										if (rt.getPointName().equals(ptVo.getPointName()) && rt.getCyDate().equals(date) && rt.getItemName().equals(hd)) {
											valueList[i] = rt.getValue();
											break;
										}
									}
									i++;
								}
								dtVo.setValueList(valueList);
								dateList.add(dtVo);
							}
							ptVo.setRowList(dateList);
							ptList.add(ptVo);
						}
					}
					tabVo.setRowList(ptList);
					wzqtabList.add(tabVo);
					page++;
				}
				// 小时均值
				if (null != xsitemList && xsitemList.size() > 0) {
					RtabVo tabVo = new RtabVo();// 循环tab
					tabVo.setPage(page);
					tabVo.setType("1");
					int cols = xsitemList.size();// 列
					tabVo.setCols(cols);
					tabVo.setColWth(6700 / cols);
					String[] headList = new String[cols];
					int n = 0;
					for (String item : xsitemList) {
						headList[n] = item;
						n++;
					}
					tabVo.setHeadList(headList);
					// 获取小时均值所有结果
					String jpql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='" + po.getId()
							+ "' AND sampTypeId='" + wst.getId() + "' AND cyHours <=2 ";
					List<ReportDetail> rtList = reportDetailDao.list(jpql);
					List<RtabRowVo> ptList = new ArrayList<>();
					String sql = "select rd.point_name,pt.point_code FROM v_bus_report_detail rd left join v_bus_task_point pt on rd.point_id=pt.id WHERE rd.is_del='"
							+ Po.N + "' AND rd.report_id ='" + po.getId() + "' AND rd.samp_type_id='" + wst.getId() + "' AND rd.cy_hours <=2 ";
					sql += "GROUP BY rd.point_id ORDER BY rd.sort asc";
					List<Object[]> objList = reportDetailDao.queryBySql(sql);
					for (Object[] obj : objList) {// 结果 点位循环
						RtabRowVo ptVo = new RtabRowVo();
						ptVo.setPointName(String.valueOf(obj[0]));
						ptVo.setSampCode(StrUtils.null2Str(obj[1]));// 代替点位编号
						if (!StrUtils.isBlankOrNull(ptVo.getPointName()) && !pointName.contains(ptVo.getPointName())) {
							pointName += "," + ptVo.getSampCode() + ptVo.getPointName();
						}
						// 日期循环
						List<RtabRowVo> dateList = new ArrayList<>();
						String sql1 = "select cy_date FROM v_bus_report_detail WHERE is_del='" + Po.N + "' AND report_id ='" + po.getId()
								+ "' AND samp_type_id='" + wst.getId() + "' AND point_name like '" + ptVo.getPointName() + "' AND cy_hours <=2 ";
						sql1 += "GROUP BY cy_date ORDER BY cy_date asc";
						List<String> dtList = reportDetailDao.queryBySql(sql1);
						if (null != dtList && dtList.size() > 0) {
							for (String date : dtList) {
								RtabRowVo dtVo = new RtabRowVo();
								dtVo.setDate(DateUtils.getChineseDate(date));
								// 小时循环
								List<RtabRowVo> timeList = new ArrayList<>();
								String sql2 = "select cy_time FROM v_bus_report_detail WHERE is_del='" + Po.N + "' AND report_id ='" + po.getId()
										+ "' AND samp_type_id='" + wst.getId() + "' AND point_name like '" + ptVo.getPointName() + "' AND cy_date like '" + date
										+ "' AND cy_hours <=2 ";
								sql2 += "GROUP BY cy_time ORDER BY cy_time asc";
								List<String> tmList = reportDetailDao.queryBySql(sql2);
								if (null != tmList && tmList.size() > 0) {
									for (String time : tmList) {
										RtabRowVo timeVo = new RtabRowVo();
										timeVo.setDate(time);
										String[] valueList = new String[cols];
										int i = 0;
										for (String hd : headList) {
											for (ReportDetail rt : rtList) {
												if (rt.getPointName().equals(ptVo.getPointName()) && rt.getCyDate().equals(date) && rt.getCyTime().equals(time)
														&& rt.getItemName().equals(hd)) {
													valueList[i] = rt.getValue();
													break;
												}
											}
											i++;
										}
										timeVo.setValueList(valueList);
										timeList.add(timeVo);
									}
									dtVo.setRowList(timeList);
									dateList.add(dtVo);
								}
							}
							ptVo.setRowList(dateList);
							ptList.add(ptVo);
						}
					}
					tabVo.setRowList(ptList);
					wzqtabList.add(tabVo);
					page++;
				}
				wzqtab.setTabList(wzqtabList);
				vo.setQwtab(wzqtab);
				// 气象部分
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + wst.getId() + "' order by cy_date asc";
				List<String> rqList = samplingDao.queryBySql(sql);
				if (rqList != null) {
					RtabVo qxtabVo = new RtabVo();
					qxtabVo.setPage(page);

					List<RtabRowVo> rowList = new ArrayList<>();
					for (String date : rqList) {// 1 按日期循环
						RtabRowVo rowVo = new RtabRowVo();
						rowVo.setDate(DateUtils.getChineseDate(date));
						List<RtabDataVo> subList = new ArrayList<RtabDataVo>();
						String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
								+ "' AND type='" + Sampling.SAMP_TYPE_PT + "' AND sampTypeId='" + wst.getId() + "' AND cyDate like '" + date
								+ "' GROUP BY cyTime ORDER BY cyTime ASC";
						List<Sampling> samList = samplingDao.list(jpql);
						for (Sampling sampling : samList) {// 2 按采样时间循环
							RtabDataVo row = new RtabDataVo();
							String dateStr = sampling.getCyTime();
							if (!StrUtils.isBlankOrNull(sampling.getCyEndTime())) {
								dateStr += "~" + sampling.getCyEndTime();
							}
							row.setCol1(dateStr);
							row.setCol2(sampling.getRecord().getDemo7());
							row.setCol3(sampling.getRecord().getDemo8());
							row.setCol4(sampling.getRecord().getDemo10());
							row.setCol5(sampling.getRecord().getDemo9());
							row.setCol6(sampling.getRecord().getDemo11());
							subList.add(row);
						}
						rowVo.setDataList(subList);
						rowList.add(rowVo);
					}
					if (pointName.length() > 0) {
						pointName = pointName.substring(1);
					}
					qxtabVo.setPointName(pointName);
					qxtabVo.setRowList(rowList);
					vo.setQxtab(qxtabVo);
				}
			}
		}
	}

	// 环境 无组织气 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4QW(Report po, ReportVo vo) throws GlobalException {
		// 无组织气 tab循环按项目， 每个tab一个项目，每个tab内部按日期循环结果块
		SampType wst = sampTypeDao.findByName(Constants.SAMP_Q_WZZ);
		if (wst != null) {
			String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='" + po.getId()
					+ "' AND sampTypeId='" + wst.getId() + "'";
			hql += "GROUP BY itemId ORDER BY sort asc";
			List<ReportDetail> itemList = reportDetailDao.list(hql);
			if (null != itemList && itemList.size() > 0) {
				RtabVo wzqtab = new RtabVo();// 无组织气开始
				wzqtab.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				wzqtab.setSampName(wst.getName());
				List<RtabVo> wzqtabList = new ArrayList<RtabVo>();
				int page = 1;
				for (ReportDetail item : itemList) {// 循环项目
					RtabVo tabVo = new RtabVo();// 循环tab
					tabVo.setPage(page);
					// 获取检测点位采样日期集合
					String sql = "select distinct(cy_date) from v_bus_report_detail where is_del=" + Po.N + " AND report_id='" + po.getId()
							+ "' AND samp_type_id='" + wst.getId() + "' AND item_id like '" + item.getItemId() + "' order by cy_date asc";
					List<String> rqList = reportDetailDao.queryBySql(sql);
					List<RtabVo> tabList = new ArrayList<>();// 结果块
					String limit = "";
					String pj = "";
					for (String date : rqList) {// 二级 日期循环
						sql = "select distinct(point_name) from v_bus_report_detail where is_del=" + Po.N + " AND report_id='" + po.getId()
								+ "' AND samp_type_id='" + wst.getId() + "' AND item_id like '" + item.getItemId() + "' AND cy_date like '" + date
								+ "' order by point_name asc";
						List<String> ptList = reportDetailDao.queryBySql(sql);
						if (null != ptList && ptList.size() > 0) {
							RtabVo rtabVo = new RtabVo();
							rtabVo.setDate(DateUtils.getChineseDate(date));
							rtabVo.setItemName(item.getItemName());
							String xz = "";
							List<RtabRowVo> rowList = new ArrayList<>();
							for (String pt : ptList) {// 循环点位
								RtabRowVo rowVo = new RtabRowVo();
								rowVo.setPointName(pt);
								hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + po.getId()
										+ "' AND sampTypeId='" + wst.getId() + "' AND pointName ='" + pt + "' AND cyDate like '" + date + "' AND itemId='"
										+ item.getItemId() + "' order by sampCode asc";
								List<ReportDetail> rdtList = reportDetailDao.list(hql);
								List<RtabRowVo> itList = new ArrayList<>();
								if (null != rdtList) {
									for (ReportDetail rdt : rdtList) {// 循环样品
										if (StrUtils.isBlankOrNull(rowVo.getAppName())) {
											List<Appara> appList = apparaDao.listByIds(rdt.getAppId());
											String appCode = "";
											if (appList != null && appList.size() > 0) {
												for (Appara appara : appList) {
													if (!StrUtils.isBlankOrNull(appara.getNo()) && !appCode.contains(appara.getNo())) {
														appCode += "," + appara.getNo();
													}
												}
											}
											if (appCode.startsWith(",")) {
												appCode = appCode.substring(1);
											}
											rowVo.setAppName(appCode);
										}
										RtabRowVo row = new RtabRowVo();
										row.setSampCode(rdt.getSampCode());
										row.setV1(rdt.getValue());
										itList.add(row);
										// 获取限值
										String standCode = null;
										if (!StrUtils.isBlankOrNull(rdt.getStandId())) {
											List<Pstandard> pdlist = pstandardDao.listByIds(rdt.getStandId());
											if (null != pdlist && pdlist.size() > 0) {
												standCode = pdlist.get(0).getCode();
											}
										}
										if (!StrUtils.isBlankOrNull(standCode) && (StrUtils.isBlankOrNull(pj) || !pj.contains(standCode))) {
											pj += "《" + rdt.getStandName() + "》（" + standCode + "）";
										}
										if (StrUtils.isBlankOrNull(limit) && !StrUtils.isBlankOrNull(rdt.getLimited())) {
											limit = "限值：" + rdt.getLimited();
										}
										if (!StrUtils.isBlankOrNull(rdt.getXz()) && !xz.contains(rdt.getXz())) {
											xz += "," + rdt.getXz();
										}
									}
								}
								rowVo.setRowList(itList);
								rowList.add(rowVo);
							}
							if (xz.length() > 0) {
								xz = xz.substring(1);
							}
							rtabVo.setRowList(rowList);// 点位
							rtabVo.setRemark(xz);// 性状
							tabList.add(rtabVo);
						}
					}
					tabVo.setTabList(tabList);
					if (limit.length() > 0) {
						tabVo.setRemark(pj + "，" + limit);
					} else {
						tabVo.setRemark(limit);
					}
					wzqtabList.add(tabVo);
					page++;
				}
				wzqtab.setTabList(wzqtabList);
				vo.setQwtab(wzqtab);
				// 气象部分
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + wst.getId() + "' order by cy_date,cy_time asc";
				List<String> rqList = samplingDao.queryBySql(sql);
				if (rqList != null) {
					RtabVo qxtabVo = new RtabVo();
					qxtabVo.setPage(page);
					String pointName = "";
					List<RtabRowVo> rowList = new ArrayList<>();
					for (String date : rqList) {
						RtabRowVo rowVo = new RtabRowVo();
						rowVo.setDate(date);
						List<RtabDataVo> subList = new ArrayList<RtabDataVo>();
						String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
								+ "' AND type='" + Sampling.SAMP_TYPE_PT + "' AND sampTypeId='" + wst.getId() + "' AND cyDate like '" + date
								+ "' GROUP BY cyTime ORDER BY cyTime ASC";
						List<Sampling> samList = samplingDao.list(jpql);
						for (Sampling sampling : samList) {
							RtabDataVo row = new RtabDataVo();
							String dateStr = sampling.getCyTime();
							if (!StrUtils.isBlankOrNull(sampling.getCyEndTime())) {
								dateStr += "~" + sampling.getCyEndTime();
							}
							row.setCol1(dateStr);
							if (null != sampling.getRecord()) {
								row.setCol2(sampling.getRecord().getDemo7());
								row.setCol3(sampling.getRecord().getDemo8());
								row.setCol4(sampling.getRecord().getDemo10());
								row.setCol5(sampling.getRecord().getDemo9());
								row.setCol6(sampling.getRecord().getDemo11());
							}
							subList.add(row);
							if (!StrUtils.isBlankOrNull(sampling.getPointName()) && !pointName.contains(sampling.getPointName())) {
								pointName += "," + sampling.getPointName();
							}
						}
						rowVo.setDataList(subList);
						rowList.add(rowVo);
					}
					if (pointName.length() > 0) {
						pointName = pointName.substring(1);
					}
					qxtabVo.setPointName(pointName);
					qxtabVo.setRowList(rowList);

					vo.setQxtab(qxtabVo);
				}
			}
		}
	}

	// 环境 有组织气 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4Q(Report po, ReportVo vo) throws GlobalException {
		// 有组织气
		SampType st = sampTypeDao.findByName(Constants.SAMP_Q_YZZ);
		if (st != null) {
			// tab循环 一级 点位 二级 日期
			// 结果集循环 行 前11固定，然后按项目循环，列按样品循环
			// 检查是否存在有组织气 的 点位
			String jpql = "FROM " + taskPointDao.getEntityName(TaskPoint.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
					+ "' AND sampTypeId='" + st.getId() + "' ORDER BY sort ASC";
			List<TaskPoint> pointList = taskPointDao.list(jpql);
			if (null != pointList && pointList.size() > 0) {
				RtabVo qtab = new RtabVo();
				qtab.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				qtab.setSampName(st.getName());
				List<RtabVo> qtabList = new ArrayList<RtabVo>();
				int page = 1;
				for (TaskPoint tp : pointList) {// 一级 点位循环
					// 获取检测点位采样日期集合
					String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND point_id='" + tp.getId()
							+ "' order by cy_date,cy_time asc";
					List<String> rqList = taskPointDao.queryBySql(sql);
					for (String date : rqList) {// 二级 日期循环
						RtabVo tabVo = new RtabVo();
						tabVo.setPage(page);
						tabVo.setPointName(tp.getPointName());
						tabVo.setDate(DateUtils.getChineseDate(date));
						// 查询该日期下的样品
						jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id='" + tp.getId()
								+ "' AND cyDate like '" + date + "%' AND zkType='" + Sampling.SAMP_TYPE_PT + "' group by sampCode ORDER BY sampCode ASC";
						List<Sampling> samList = samplingDao.query(jpql).getResultList();
						// 组装 tab内检测结果信息
						List<RtabRowVo> rowList = new ArrayList<RtabRowVo>();
						// 大气压
						RtabRowVo row1 = new RtabRowVo();
						row1.setItemName("大气压");
						row1.setUnit("kPa");
						row1.setV1("");
						rowList.add(row1);
						// 排气筒高度
						RtabRowVo row2 = new RtabRowVo();
						row2.setItemName("排气筒高度");
						row2.setUnit("m");
						row2.setV1("");
						rowList.add(row2);
						// 排气筒直径
						RtabRowVo row3 = new RtabRowVo();
						row3.setItemName("排气筒直径");
						row3.setUnit("m");
						row3.setV1("");
						rowList.add(row3);
						// 管道截面积
						RtabRowVo row4 = new RtabRowVo();
						row4.setItemName("管道截面积");
						row4.setUnit("m2");
						row4.setV1("");
						rowList.add(row4);
						// 工况负荷
						RtabRowVo row5 = new RtabRowVo();
						row5.setItemName("运行负荷");
						row5.setUnit("%");
						row5.setV1("");
						rowList.add(row5);
						// 平均烟温
						RtabRowVo row6 = new RtabRowVo();
						row6.setItemName("烟气温度");
						row6.setUnit("℃");
						// 含湿量
						RtabRowVo row7 = new RtabRowVo();
						row7.setItemName("含湿量");
						row7.setUnit("%");
						// 平均静压
						RtabRowVo row8 = new RtabRowVo();
						row8.setItemName("平均静压");
						row8.setUnit("kPa");
						// 平均动压
						RtabRowVo row9 = new RtabRowVo();
						row9.setItemName("平均动压");
						row9.setUnit("Pa");
						// 平均流速
						RtabRowVo row10 = new RtabRowVo();
						row10.setItemName("烟气流速");
						row10.setUnit("m/s");
						// 标干流量
						RtabRowVo row11 = new RtabRowVo();
						row11.setItemName("标干流量");
						row11.setUnit("m3/h");
						int mlength = samList.size();// 最小列数 为3
						if (mlength < 3) {
							mlength = 3;
						}
						// 标题集合
						String[] headList = new String[mlength];
						String[] list6 = new String[mlength];
						String[] list7 = new String[mlength];
						String[] list8 = new String[mlength];
						String[] list9 = new String[mlength];
						String[] list10 = new String[mlength];
						String[] list11 = new String[mlength];
						int length = samList.size();// 数组长度
						float avg6 = 0;
						float avg7 = 0;
						float avg8 = 0;
						float avg9 = 0;
						float avg10 = 0;
						float avg11 = 0;
						int n = 0;
						for (Sampling samp : samList) {
							if (n == 0) {
								row1.setV1(samp.getRecord().getDemo9());
								row2.setV1(samp.getCyd().getDeme6());
								row3.setV1(samp.getCyd().getDeme5());
								row4.setV1(samp.getCyd().getDeme7());
								row5.setV1(samp.getCyd().getDeme9());
							}
							headList[n] = samp.getSampCode();
							list6[n] = samp.getRecord().getDemo12();
							list7[n] = samp.getRecord().getDemo13();
							list8[n] = samp.getRecord().getDemo15();
							list9[n] = samp.getRecord().getDemo18();
							list10[n] = samp.getRecord().getDemo19();
							list11[n] = samp.getRecord().getDemo1();
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo12())) {
									avg6 += Float.valueOf(samp.getRecord().getDemo12());
								}
							} catch (NumberFormatException e) {
								avg6 += 0;
							}
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo13())) {
									avg7 += Float.valueOf(samp.getRecord().getDemo13());
								}
							} catch (NumberFormatException e) {
								avg7 += 0;
							}
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo15())) {
									avg8 += Float.valueOf(samp.getRecord().getDemo15());
								}
							} catch (NumberFormatException e) {
								avg8 += 0;
							}
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo18())) {
									avg9 += Float.valueOf(samp.getRecord().getDemo18());
								}
							} catch (NumberFormatException e) {
								avg9 += 0;
							}
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo19())) {
									avg10 += Float.valueOf(samp.getRecord().getDemo19());
								}
							} catch (NumberFormatException e) {
								avg10 += 0;
							}
							try {
								if (!StrUtils.isBlankOrNull(samp.getRecord().getDemo1())) {
									avg11 += Float.valueOf(samp.getRecord().getDemo1());
								}
							} catch (NumberFormatException e) {
								avg11 += 0;
							}
							n++;
						}
						row6.setValueList(list6);
						row7.setValueList(list7);
						row8.setValueList(list8);
						row9.setValueList(list9);
						row10.setValueList(list10);
						row11.setValueList(list11);
						// 赋 平均值
						row6.setAvg(avg6 > 0 ? (avg6 / length) : 0);
						row7.setAvg(avg7 > 0 ? (avg7 / length) : 0);
						row8.setAvg(avg8 > 0 ? (avg8 / length) : 0);
						row9.setAvg(avg9 > 0 ? (avg9 / length) : 0);
						row10.setAvg(avg10 > 0 ? (avg10 / length) : 0);
						row11.setAvg(avg11 > 0 ? (avg11 / length) : 0);
						rowList.add(row6);
						rowList.add(row7);
						rowList.add(row8);
						rowList.add(row9);
						rowList.add(row10);
						rowList.add(row11);
						// 处理样品的检测项目
						String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND pointId ='" + tp.getId()
								+ "' AND cyDate like'" + date + "%' ";
						hql += "GROUP BY itemId ORDER BY sort asc";
						List<ReportDetail> itemList = reportDetailDao.list(hql);
						String pj = "";
						String limit = "";
						if (itemList != null) {
							String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND pointId ='"
									+ tp.getId() + "' AND cyDate like'" + date + "%' ";
							List<ReportDetail> dataList = reportDetailDao.list(subhql);
							for (ReportDetail item : itemList) {
								// 浓度
								RtabRowVo row = new RtabRowVo();
								row.setItemName(item.getItemName() + "标干浓度");
								row.setUnit(item.getUnit());
								String[] valueList = new String[mlength];
								float avg = 0;
								// 速率
								RtabRowVo nd = new RtabRowVo();
								nd.setItemName(item.getItemName() + "排放速率");
								nd.setUnit(item.getUnit());
								String[] valueList2 = new String[mlength];
								float avg2 = 0;
								for (int j = 0; j < headList.length; j++) {
									if (StrUtils.isBlankOrNull(headList[j])) {
										continue;
									}
									for (ReportDetail dt : dataList) {
										if (dt.getItemName().equals(item.getItemName()) && headList[j].equals(dt.getSampCode())) {
											valueList[j] = dt.getValue();
											if (!StrUtils.isBlankOrNull(dt.getValue())) {
												try {
													avg += Float.valueOf(dt.getValue());
												} catch (NumberFormatException e) {
													avg += 0;
												}
											}
											valueList2[j] = dt.getSl();
											if (!StrUtils.isBlankOrNull(dt.getSl())) {
												try {
													avg2 += Float.valueOf(dt.getSl());
												} catch (NumberFormatException e) {
													avg2 += 0;
												}
											}
										}
									}
								}
								String standCode = null;
								if (!StrUtils.isBlankOrNull(item.getStandId())) {
									List<Pstandard> pdlist = pstandardDao.listByIds(item.getStandId());
									if (null != pdlist && pdlist.size() > 0) {
										standCode = pdlist.get(0).getCode();
									}
								}
								if (StrUtils.isBlankOrNull(pj) && !StrUtils.isBlankOrNull(item.getStandName())) {
									pj += "《" + item.getStandName() + "》（" + standCode + "）";
								} else if (!StrUtils.isBlankOrNull(pj) && !StrUtils.isBlankOrNull(item.getStandName()) && !pj.contains(item.getStandName())) {
									pj += "，《" + item.getStandName() + "》（" + standCode + "）";
								}
								if (StrUtils.isBlankOrNull(limit) && !StrUtils.isBlankOrNull(item.getLimited())) {
									limit = "限值：" + item.getLimited();
								} else if (!StrUtils.isBlankOrNull(item.getLimited())) {
									limit += "，" + item.getLimited();
								}
								row.setValueList(valueList);
								row.setAvg(avg > 0 ? (avg / length) : 0);
								nd.setValueList(valueList2);
								nd.setAvg(avg2 > 0 ? (avg2 / length) : 0);
								rowList.add(row);
								rowList.add(nd);
							}
						}
						tabVo.setRowList(rowList);
						tabVo.setHeadList(headList);
						if (headList.length > 0) {
							tabVo.setColWth(4706 / headList.length);
						} else {
							tabVo.setColWth(4706);
						}
						tabVo.setCols(headList.length);
						if (limit.length() > 0) {
							tabVo.setRemark(pj + "，" + limit);
						} else {
							tabVo.setRemark(limit);
						}
						qtabList.add(tabVo);
						page++;
					}
				}
				qtab.setTabList(qtabList);
				vo.setQtab(qtab);
			}
		}
	}

	// 环境 声 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4S(Report po, ReportVo vo) throws GlobalException {
		List<SampType> ssampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_S);
		if (null != ssampList && ssampList.size() > 0) {
			List<RtabVo> stabList = new ArrayList<RtabVo>();
			for (SampType sampType : ssampList) {// 一级tab循环 按样品类型
				RtabVo tabVo = new RtabVo();
				tabVo.setSampName(sampType.getName());
				tabVo.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				// 开始tab内部 循环 日期 块循环点位循环
				// 获取检测点位采样日期集合
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + sampType.getId() + "' order by cy_date asc";
				List<String> rqList = taskPointDao.queryBySql(sql);
				List<RtabVo> dateList = new ArrayList<RtabVo>();
				List<RtabRowVo> qxList = new ArrayList<RtabRowVo>();
				String pj = "";
				String gnq = "";
				String limit = "";
				for (String date : rqList) {// 二级 日期块循环
					RtabVo dateTab = new RtabVo();
					dateTab.setDate(DateUtils.getChineseDate(date));
					// 查询该日期下的样品
					sql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
							+ "' AND sampTypeId='" + sampType.getId() + "' AND cyDate like '" + date + "' ORDER BY sampCode ASC";
					List<Sampling> samList = samplingDao.list(sql);
					RtabRowVo qxVo = new RtabRowVo();
					qxVo.setDate(DateUtils.getChineseDate(date));
					List<RtabDataVo> qxdataList = new ArrayList<>();
					RtabDataVo zj = new RtabDataVo();
					RtabDataVo yj = new RtabDataVo();
					if (null != samList && samList.size() > 0) {
						List<RtabRowVo> rowList = new ArrayList<>();
						for (Sampling sampling : samList) {
							RtabRowVo rowVo = new RtabRowVo();
							rowVo.setSampCode(sampling.getSampCode());
							rowVo.setPointName(sampling.getPointName());
							rowVo.setUnit(sampling.getCyd().getGnq());// 功能区
							if (StrUtils.isBlankOrNull(gnq) && !StrUtils.isBlankOrNull(rowVo.getUnit())) {
								gnq += "功能区：" + rowVo.getUnit();
							}
							String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND samplingId ='"
									+ sampling.getId() + "' order by cyDate,cyTime asc";
							List<ReportDetail> dataList = reportDetailDao.list(subhql);
							if (null != dataList && dataList.size() > 0) {
								List<RtabRowVo> valList = new ArrayList<>();
								for (ReportDetail rdt : dataList) {
									RtabRowVo data = new RtabRowVo();
									String cytime = sampling.getCyTime() + "~" + sampling.getCyEndTime();
									data.setDate(cytime);// 采样时间
									data.setItemName(sampling.getFcType());// 昼间 夜间
									data.setV1(rdt.getValue());
									valList.add(data);
									// 更新测试日期
									String standCode = null;
									if (!StrUtils.isBlankOrNull(rdt.getStandId())) {
										List<Pstandard> pdlist = pstandardDao.listByIds(rdt.getStandId());
										if (null != pdlist && pdlist.size() > 0) {
											standCode = pdlist.get(0).getCode();
										}
									}
									if (StrUtils.isBlankOrNull(pj) && !StrUtils.isBlankOrNull(rdt.getStandName())) {
										pj = "《" + rdt.getStandName() + "》（" + standCode + "）";
									}
									if (StrUtils.isBlankOrNull(limit) && !StrUtils.isBlankOrNull(rdt.getLimited())) {
										String lmt = rdt.getLimited();
										lmt = lmt.replace("≤", "");
										lmt = lmt.replace("<", "");
										limit = rdt.getSampName() + lmt + rdt.getUnit();
									} else if (!StrUtils.isBlankOrNull(rdt.getLimited()) && !limit.contains(rdt.getSampName())) {
										String lmt = rdt.getLimited();
										lmt = lmt.replace("≤", "");
										lmt = lmt.replace("<", "");
										limit += "，" + rdt.getSampName() + lmt + rdt.getUnit();
									}
								}
								rowVo.setRowList(valList);
								rowList.add(rowVo);
							}
							if (StrUtils.isBlankOrNull(zj.getCol1()) && null != sampling.getFcType() && sampling.getFcType().contains("昼间")) {
								zj.setCol1("昼间");
								zj.setCol2(sampling.getCyd().getTx());
								zj.setCol3(sampling.getCyd().getFx());
								zj.setCol4(sampling.getCyd().getFs());
							}
							if (StrUtils.isBlankOrNull(yj.getCol1()) && null != sampling.getFcType() && sampling.getFcType().contains("夜间")) {
								yj.setCol1("夜间");
								yj.setCol2(sampling.getCyd().getTx());
								yj.setCol3(sampling.getCyd().getFx());
								yj.setCol4(sampling.getCyd().getFs());
							}
						}
						dateTab.setRowList(rowList);
					}
					if (!StrUtils.isBlankOrNull(zj.getCol1())) {
						qxdataList.add(zj);
					}
					if (!StrUtils.isBlankOrNull(yj.getCol1())) {
						qxdataList.add(yj);
					}
					qxVo.setDataList(qxdataList);
					qxList.add(qxVo);
					dateList.add(dateTab);
				}
				tabVo.setTabList(dateList);// 检测结果集合
				tabVo.setRemark(pj + gnq + limit);
				tabVo.setRowList(qxList);// 气象集合
				stabList.add(tabVo);
			}
			vo.setStabList(stabList);
		}
	}

	// 环评 土 固体 沉积物 等 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4TPj(Report po, ReportVo vo) throws GlobalException {
		List<SampType> tsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_T);
		List<SampType> gsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_G);
		tsampList.addAll(gsampList);
		if (null != tsampList && tsampList.size() > 0) {
			List<RtabVo> ttabList = new ArrayList<RtabVo>();
			for (SampType st : tsampList) {// 一级tab循环 按样品类型
				RtabVo tabVo = new RtabVo();
				tabVo.setSampName(st.getName());
				tabVo.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + st.getId() + "' order by cy_date,cy_time asc";
				List<String> rqList = taskPointDao.queryBySql(sql);
				if (rqList != null) {
					List<RtabVo> dateList = new ArrayList<RtabVo>();
					int page = 1;
					for (String date : rqList) {// 二级 tab 循环 按 日期
						RtabVo dttabVo = new RtabVo();
						dttabVo.setPage(page);
						dttabVo.setDate(DateUtils.getChineseDate(date));
						String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
								+ "' AND sampTypeId='" + st.getId() + "' AND cyDate like '" + date + "%' GROUP BY sampCode ORDER BY sampCode ASC";
						List<Sampling> samList = samplingDao.list(jpql);
						String others = "";
						if (samList != null && samList.size() > 0) {
							int cols = samList.size();
							dttabVo.setCols(cols);
							dttabVo.setColWth(7200 / cols);
							String[] ccList = new String[cols];
							String[] addList = new String[cols];
							String[] tzList = new String[cols];
							String[] dtList = new String[cols];
							String[] headList = new String[cols];
							int n = 0;
							for (Sampling sampling : samList) {
								addList[n] = sampling.getPointName();
								ccList[n] = sampling.getRecord().getDemo4();
								tzList[n] = sampling.getRecord().getDemo3();
								dtList[n] = (sampling.getRecord().getDemo2());
								headList[n] = sampling.getSampCode();
								others = sampling.getCyd().getDeme10();// 土壤和废固 pH浸提剂
								n++;
							}
							List<RtabRowVo> dataList = new ArrayList<RtabRowVo>();
							// 检测项目
							String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' ";
							hql += "GROUP BY itemId ORDER BY sort asc";
							List<ReportDetail> itemList = reportDetailDao.list(hql);
							// 检测值
							String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' order by cyTime asc";
							List<ReportDetail> rdtList = reportDetailDao.list(subhql);
							if (null != itemList && itemList.size() > 0) {
								for (ReportDetail item : itemList) {
									RtabRowVo itemVo = new RtabRowVo();
									itemVo.setItemName(item.getItemName());
									itemVo.setUnit(item.getUnit());
									String[] valueArr = new String[headList.length];
									for (int i = 0; i < headList.length; i++) {
										if (StrUtils.isBlankOrNull(headList[i])) {
											continue;
										}
										for (ReportDetail rdt : rdtList) {
											if (rdt.getItemName().equals(item.getItemName()) && rdt.getSampCode().equals(headList[i])) {
												valueArr[i] = rdt.getValue();
											}
										}
									}
									itemVo.setValueList(valueArr);
									dataList.add(itemVo);
								}
							}
							dttabVo.setRowList(dataList);// 结果
							dttabVo.setHeadList(headList);// 标题
							dttabVo.setDtList(dtList);// 样品状态
							dttabVo.setAddList(addList);// 采样地点
							dttabVo.setOthers(others);// pH浸提剂
							dateList.add(dttabVo);
							page++;
						}
					}
					tabVo.setTabList(dateList);
				}
				ttabList.add(tabVo);
			}
			vo.setTtabList(ttabList);
		}
	}

	// 环境 土 固体 沉积物 等 检测结果
	@SuppressWarnings("unchecked")
	public void getTab4T(Report po, ReportVo vo) throws GlobalException {
		List<SampType> tsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_T);
		List<SampType> gsampList = sampTypeDao.listByType(po.getSampTypeId(), Constants.SAMP_G);
		tsampList.addAll(gsampList);
		if (null != tsampList && tsampList.size() > 0) {
			List<RtabVo> ttabList = new ArrayList<RtabVo>();
			for (SampType st : tsampList) {// 一级tab循环 按样品类型
				RtabVo tabVo = new RtabVo();
				tabVo.setSampName(st.getName());
				tabVo.setPage(vo.getPage());
				vo.setPage(vo.getPage() + 1);
				String sql = "select distinct(cy_date) from v_bus_sampling where is_del=" + Po.N + " AND task_id='" + po.getTask().getId()
						+ "' AND samp_type_id='" + st.getId() + "' order by cy_date,cy_time asc";
				List<String> rqList = taskPointDao.queryBySql(sql);
				if (rqList != null) {
					List<RtabVo> dateList = new ArrayList<RtabVo>();
					int page = 1;
					for (String date : rqList) {// 二级 tab 循环 按 日期
						RtabVo dttabVo = new RtabVo();
						dttabVo.setPage(page);
						dttabVo.setDate(DateUtils.getChineseDate(date));
						String pj = "";
						String limit = "";
						String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + po.getTask().getId()
								+ "' AND sampTypeId='" + st.getId() + "' AND cyDate like '" + date + "%' GROUP BY sampCode ORDER BY sampCode ASC";
						List<Sampling> samList = samplingDao.list(jpql);
						String others = "";
						if (samList != null && samList.size() > 0) {
							int cols = samList.size();
							dttabVo.setCols(cols);
							dttabVo.setColWth(6200 / cols);
							String[] ccList = new String[cols];
							String[] addList = new String[cols];
							String[] tzList = new String[cols];
							String[] dtList = new String[cols];
							String[] headList = new String[cols];
							int n = 0;
							for (Sampling sampling : samList) {
								addList[n] = sampling.getPointName();
								if (sampling.getRecord() != null) {
									ccList[n] = sampling.getRecord().getDemo4();
									tzList[n] = sampling.getRecord().getDemo3();
									dtList[n] = (sampling.getRecord().getDemo2());
								}

								headList[n] = sampling.getSampCode();
								if (null != sampling.getCyd()) {
									others = sampling.getCyd().getDeme10();// 土壤和废固 pH浸提剂
								}

								n++;
							}
							List<RtabRowVo> dataList = new ArrayList<RtabRowVo>();
							// 检测项目
							String hql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' ";
							hql += "GROUP BY itemId ORDER BY sort asc";
							List<ReportDetail> itemList = reportDetailDao.list(hql);
							// 检测值
							String subhql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId ='"
									+ po.getId() + "' AND sampTypeId='" + st.getId() + "' AND cyDate like'" + date + "%' order by cyTime asc";
							List<ReportDetail> rdtList = reportDetailDao.list(subhql);
							if (null != itemList && itemList.size() > 0) {
								for (ReportDetail item : itemList) {
									RtabRowVo itemVo = new RtabRowVo();
									itemVo.setItemName(item.getItemName());
									itemVo.setUnit(item.getUnit());
									String[] valueArr = new String[headList.length];
									for (int i = 0; i < headList.length; i++) {
										if (StrUtils.isBlankOrNull(headList[i])) {
											continue;
										}
										for (ReportDetail rdt : rdtList) {
											if (rdt.getItemName().equals(item.getItemName()) && rdt.getSampCode().equals(headList[i])) {
												valueArr[i] = rdt.getValue();
											}
										}
									}
									itemVo.setValueList(valueArr);
									dataList.add(itemVo);
									// 组装评价信息
									String standCode = null;
									if (!StrUtils.isBlankOrNull(item.getStandId())) {
										List<Pstandard> pdlist = pstandardDao.listByIds(item.getStandId());
										if (null != pdlist && pdlist.size() > 0) {
											standCode = pdlist.get(0).getCode();
										}
									}
									if (StrUtils.isBlankOrNull(pj) && !StrUtils.isBlankOrNull(item.getStandName())) {
										pj += "《" + item.getStandName() + "》（" + standCode + "）";
									} else if (!StrUtils.isBlankOrNull(pj) && !StrUtils.isBlankOrNull(item.getStandName())
											&& !pj.contains(item.getStandName())) {
										pj += "，《" + item.getStandName() + "》（" + standCode + "）";
									}
									// 组装限值信息
									if (StrUtils.isBlankOrNull(limit) && !StrUtils.isBlankOrNull(item.getLimited())) {
										limit = "限值：" + item.getLimited();
									} else if (!StrUtils.isBlankOrNull(item.getLimited()) && !limit.contains(item.getLimited())) {
										limit += "，" + item.getLimited();
									}
								}
							}
							dttabVo.setRowList(dataList);// 结果
							dttabVo.setHeadList(headList);// 标题
							dttabVo.setRemark(pj + "  " + limit);// 备注 标准
							dttabVo.setDtList(dtList);// 样品状态
							dttabVo.setDateList(tzList);// 断面特征
							dttabVo.setCeList(ccList);// 层次
							dttabVo.setAddList(addList);// 采样地点
							dttabVo.setOthers(others);// pH浸提剂
							dateList.add(dttabVo);
							page++;
						}
					}
					tabVo.setTabList(dateList);
				}
				ttabList.add(tabVo);
			}
			vo.setTtabList(ttabList);
		}
	}

	public static String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(d);
	}

	@Override
	public void update(ReportVo v) throws GlobalException {
		Report po = reportDao.findById(v.getId());
		po.setReportNo(v.getReportNo());
		po.setReportDate(v.getReportDate());
		po.setTestEndDate(v.getTestEndDate());
		po.setMakeDate(v.getMakeDate());
		po.setMakeUser(v.getMakeUser());
		po.setMakeUserId(v.getMakeUserId());
		po.setJhsm(v.getJhsm());
		po.setJcct(v.getJcct());
		po.setJssm(v.getJssm());
		po.setResult(v.getResult());
		po.setRemark(v.getRemark());
		po.setJchj(v.getJchj());
		if (!StrUtils.isBlankOrNull(v.getReptUserId())) {
			Account at = accountDao.findById(v.getReptUserId());
			v.setReptUser(at.getUser().getName());
			po.setReptUser(v.getReptUser());
			po.setReptUserId(v.getReptUserId());
		}
		if (v.getIsCommit().equals(EunmTask.PASS_Y)) {
			// 插入任务进度日志
			Progress pg = po.getProgress();
			progressLogDao.add(po.getTask().getId(), po.getTask().getId(), EunmTask.TASK_BZ.getStatus(), v.getIsCommit(), v.getRemark());
			if (!StrUtils.isBlankOrNull(po.getReptUserId())) {
				pg = progressDao.update(pg.getId(), EunmTask.TASK_FH.getStatus(), null, null, v.getReptUserId(), v.getReptUser());
			} else {
				pg = progressDao.update(pg.getId(), EunmTask.TASK_FH.getStatus(), null, null, null, null);
			}
			po.setIsBack(Constants.N);
			po.setStatus(pg.getStatus());
			sRecordDao.insert(po.getTask().getNo(), po.getTask().getSumDate(), Constants.KH_RW_BGBZ);
		}
		reportDao.update(po);
		Task task = po.getTask();
		task.setStatus(po.getStatus());

		task.setJcct(v.getJcct());
		taskDao.update(task);
		updDetail(v);
	}

	// 更新报告详情的序号
	public void updDetail(ReportVo v) {
		// 环境
		List<ReportDetailVo> dtList = v.getDetailList();
		if (null != dtList) {
			for (ReportDetailVo dtVo : dtList) {
				ReportDetail dt = reportDetailDao.findById(dtVo.getId());
				dt.setSort(dtVo.getSort());
				dt.setValue(dtVo.getValue());
				dt.setSl(dtVo.getSl());
				reportDetailDao.update(dt);
			}
		}
		// 职卫
		List<TaskItemVo> timList = v.getTimList();
		if (null != timList) {
			for (TaskItemVo timVo : timList) {
				TaskItem tim = taskItemDao.findById(timVo.getId());
				tim.setSort(timVo.getSort());
				taskItemDao.update(tim);
				if (tim.getItemName().contains("粉尘")) {// 粉尘报告编制更新数据
					List<TestItemVo> itList = timVo.getItemList();
					if (null != itList) {
						for (TestItemVo itVo : itList) {
							TestItem it = testItemDao.findById(itVo.getId());
							it.setValue(itVo.getValue());
							it.setResult(itVo.getResult());
							it.setMac(itVo.getMac());
							it.setStel(itVo.getStel());
							it.setTwa(itVo.getTwa());
							it.setLmt(itVo.getLmt());
							testItemDao.update(it);
							List<TestResultVo> trVoList = itVo.getTrList();
							if (null != trVoList) {
								for (TestResultVo trVo : trVoList) {
									TestResult tr = testResultDao.findById(trVo.getId());
									uptValue4Xc(tim, trVo.getSampVo(), tr.getSamp().getRecord());
								}
							}
						}
					}
				}
			}
		}
	}

	// 更新现场数据信息
	public void uptValue4Xc(TaskItem tim, SamplingVo sampVo, SampRecord record) {
		if (tim.getType().equals(TaskItem.ITEM_TYPE_XC) && null != sampVo && null != sampVo.getRecordVo()) {
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV1())) {
				record.setV1(sampVo.getRecordVo().getV1());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV2())) {
				record.setV2(sampVo.getRecordVo().getV2());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV3())) {
				record.setV3(sampVo.getRecordVo().getV3());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV4())) {
				record.setV4(sampVo.getRecordVo().getV4());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV5())) {
				record.setV5(sampVo.getRecordVo().getV5());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV6())) {
				record.setV6(sampVo.getRecordVo().getV6());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV7())) {
				record.setV7(sampVo.getRecordVo().getV7());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV8())) {
				record.setV8(sampVo.getRecordVo().getV8());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV9())) {
				record.setV9(sampVo.getRecordVo().getV9());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV10())) {
				record.setV10(sampVo.getRecordVo().getV10());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV11())) {
				record.setV11(sampVo.getRecordVo().getV11());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV12())) {
				record.setV12(sampVo.getRecordVo().getV12());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg1())) {
				record.setAvg1(sampVo.getRecordVo().getAvg1());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg2())) {
				record.setAvg2(sampVo.getRecordVo().getAvg2());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg3())) {
				record.setAvg3(sampVo.getRecordVo().getAvg3());
			}
			sampRecordDao.update(record);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_BZ.getStatus() + "' "));
//		pageResult.addCondition(new QueryCondition("makeUserId='" + getCurrent().getAccountId() + "' or makeUserId is null or makeUserId='' "));
		pageResult = reportDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Report>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ReportVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		// queryList.add(new QueryCondition("type='" + Po.N + "' "));
		return queryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + reportDao.getEntityName(Report.class) + " t,"
				+ reportDao.getEntityName(ProgressLog.class) + " log where log.busId=t.task.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmTask.TASK_BZ.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = reportDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Report> taskList = reportDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public ReportVo update4Report(ReportVo v) throws GlobalException {
		Report report = reportDao.findById(v.getId());
		if (null != v.getUuid() && v.getUuid().equals("dq")) {
			// 保存定期项目报告
			report.setFilePath2(v.getFilePath2());
			report.setFileName2(v.getFileName2());
			String filePath = report.getFilePath2();
			String fileName = report.getFileName2();
			if (!StrUtils.isBlankOrNull(filePath)) {
				filePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
				report.setPdfPath2(filePath);
				fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
				report.setPdfName2(fileName);
			}
		} else {
			report.setFilePath(v.getFilePath());
			report.setFileName(v.getFileName());
			String filePath = report.getFilePath();
			if (!StrUtils.isBlankOrNull(filePath)) {
				filePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
				report.setPdfPath(filePath);
				String fileName = report.getFileName();
				fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
				report.setPdfName(fileName);
			}
		}
		reportDao.update(report);
		v.setId(report.getId());
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Comp(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_END.getStatus() + "' "));
		pageResult = reportDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Report>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	// 放射性报告组装
	public void getDate4Fs(Report po, ReportVo vo) throws GlobalException {
		// 项目合格率表
		vo.setFfList(getSampResult4FS(vo));
		// 获取仪器信息集合
		List<Appara> appList = apparaDao.listByIds(vo.getAppIds());
		List<RappVo> appVoList = new ArrayList<RappVo>();
		if (null != appList) {
			for (Appara appara : appList) {
				RappVo appVo = new RappVo();
				appVo.setAppName(appara.getName());
				appVo.setAppRule(StrUtils.escapeWord(appara.getSpec()));
				appVo.setAppCode(appara.getNo());
				appVoList.add(appVo);
			}
		}
		vo.setAppList(appVoList);
		List<TaskItem> timList = taskItemDao.listByTaskId(vo.getTaskVo().getId());
		List<TaskItemVo> timVoList = new ArrayList<TaskItemVo>();
		for (TaskItem tim : timList) {
			TaskItemVo timVo = new TaskItemVo();
			timVo = timVo.toVo(tim);
			String limited = timVo.getLimited();
			if (!StrUtils.isBlankOrNull(limited)) {
				limited = limited.replace("<", "").replace(">", "").replace("≤", "").replace("≥", "");
			}
			timVo.setLimited(limited);
			int num = testItemDao.maxItemNum4Tim(tim.getId());
			if (num < 3) {
				num = 3;
			}
			String[] headAry = new String[num];
			for (int i = 0; i < num; i++) {
				headAry[i] = String.valueOf(i + 1);
			}
			timVo.setHeadAry(headAry);
			List<RtabRowVo> dateList = getDateRow4Fs(vo.getTaskVo(), timVo, num);
			if (null != dateList && dateList.size() > 0) {
				if (null != tim.getLimited() && tim.getLimited().equals("b")) {// 报告文件具体情况具体分析
					num = num + 2;
				} else {
					num = num + 1;
				}
				timVo.setCount(num);
				if (!StrUtils.isBlankOrNull(timVo.getMac()) && !timVo.getMac().contains(timVo.getUnit())) {
					timVo.setMac(timVo.getMac() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getTwa()) && !timVo.getTwa().contains(timVo.getUnit())) {
					timVo.setTwa(timVo.getTwa() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getStel()) && !timVo.getStel().contains(timVo.getUnit())) {
					timVo.setStel(timVo.getStel() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getLimited()) && !timVo.getLimited().equals("a") && !timVo.getLimited().equals("b")
						&& !timVo.getLimited().equals("c") && !timVo.getLimited().contains(timVo.getUnit())) {
					timVo.setLimited(timVo.getLimited() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getPitId())) {
					PstandItem pit = pstandItemDao.findById(timVo.getPitId());
					if (null != pit) {
						PstandItemVo pitVo = new PstandItemVo();
						pitVo = pitVo.toVo(pit);
						timVo.setPitVo(pitVo);
					}
				}
				timVo.setDateList(dateList);
				String hql = "FROM " + sampCydDao.getEntityName(SampCyd.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
						+ "' AND itemIds like '%" + tim.getItemId() + "%' ";
				List<SampCyd> cydList = sampCydDao.list(hql);
				timVo.setCydTemp(cydList.get(0).getType());
				timVoList.add(timVo);
			}
		}
		vo.setTimList(timVoList);
		if (!StrUtils.isBlankOrNull(vo.getReportDate())) {
			String rdata = vo.getReportDate();
			vo.setReportDate(DateUtils.getChineseDate(rdata));
			vo.setReportDateStr(DateUtils.formatToChineseDate(rdata));
		}
		if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyDate())) {
			String cyDate = vo.getTaskVo().getCyDate().substring(0, 10);
			String cyEndDate = null;
			if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyEndDate())) {
				cyEndDate = vo.getTaskVo().getCyEndDate().substring(0, 10);
				cyEndDate = DateUtils.getChineseDate(cyEndDate);
			}
			cyDate = DateUtils.getChineseDate(cyDate);
			if (null != cyEndDate && !cyEndDate.equals(cyDate)) {
				cyDate = cyDate + "~" + cyEndDate;
			}
			vo.setCyDate(cyDate);
		}
		String testDate = vo.getTestDate();
		String tesEndtDate = vo.getTestEndDate();
		if (!StrUtils.isBlankOrNull(testDate)) {
			testDate = DateUtils.getChineseDate(testDate);
		}
		if (!StrUtils.isBlankOrNull(tesEndtDate)) {
			tesEndtDate = DateUtils.getChineseDate(tesEndtDate);
		}
		if (!testDate.equals(tesEndtDate)) {
			testDate = testDate + "~" + tesEndtDate;
		}
		vo.setTestDate(testDate);
	}

	// 职卫报告组装
	public void getDate4Zw(Report po, ReportVo vo) throws GlobalException {
		// 项目合格率表
		vo.setFfList(getSampResult(vo));
		// 获取仪器信息集合
		List<Appara> appList = apparaDao.listByIds(vo.getAppIds());
		List<RappVo> appVoList = new ArrayList<RappVo>();
		if (null != appList) {
			for (Appara appara : appList) {
				RappVo appVo = new RappVo();
				appVo.setAppName(appara.getName());
				appVo.setAppRule(appara.getSpec());
				appVo.setAppCode(appara.getNo());
				appVoList.add(appVo);
			}
		}
		vo.setAppList(appVoList);
		List<TaskItem> timList = taskItemDao.listByTaskId(vo.getTaskVo().getId());
		List<TaskItemVo> timVoList = new ArrayList<TaskItemVo>();
		for (TaskItem tim : timList) {
			TaskItemVo timVo = new TaskItemVo();
			timVo = timVo.toVo(tim);
			String limited = timVo.getLimited();
			if (!StrUtils.isBlankOrNull(limited)) {
				limited = limited.replace("<", "").replace(">", "").replace("≤", "").replace("≥", "");
			}
			timVo.setLimited(limited);
			int num = testItemDao.maxItemNum4Tim(tim.getId());
			if (num < 3) {
				num = 3;
			}
			String[] headAry = new String[num];
			for (int i = 0; i < num; i++) {
				headAry[i] = String.valueOf(i + 1);
			}
			timVo.setHeadAry(headAry);
			List<RtabRowVo> dateList = getDateRow(vo.getTaskVo(), timVo, num);
			if (null != dateList && dateList.size() > 0) {
				if (null != tim.getLimited() && tim.getLimited().equals("b")) {// 报告文件具体情况具体分析
					num = num + 2;
				} else {
					num = num + 1;
				}
				timVo.setCount(num);
				if (!StrUtils.isBlankOrNull(timVo.getMac()) && !timVo.getMac().contains(timVo.getUnit())) {
					timVo.setMac(timVo.getMac() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getTwa()) && !timVo.getTwa().contains(timVo.getUnit())) {
					timVo.setTwa(timVo.getTwa() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getStel()) && !timVo.getStel().contains(timVo.getUnit())) {
					timVo.setStel(timVo.getStel() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getLimited()) && !timVo.getLimited().equals("a") && !timVo.getLimited().equals("b")
						&& !timVo.getLimited().equals("c") && !timVo.getLimited().contains(timVo.getUnit())) {
					timVo.setLimited(timVo.getLimited() + timVo.getUnit());
				}
				if (!StrUtils.isBlankOrNull(timVo.getPitId())) {
					PstandItem pit = pstandItemDao.findById(timVo.getPitId());
					if (null != pit) {
						PstandItemVo pitVo = new PstandItemVo();
						pitVo = pitVo.toVo(pit);
						timVo.setPitVo(pitVo);
					}
				}
				timVo.setDateList(dateList);
				String hql = "FROM " + sampCydDao.getEntityName(SampCyd.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
						+ "' AND itemIds like '%" + tim.getItemId() + "%' ";
				List<SampCyd> cydList = sampCydDao.list(hql);
				timVo.setCydTemp(cydList.get(0).getType());
				timVoList.add(timVo);
			}
		}
		vo.setTimList(timVoList);
		if (!StrUtils.isBlankOrNull(vo.getReportDate())) {
			String rdata = vo.getReportDate();
			vo.setReportDate(DateUtils.getChineseDate(rdata));
			vo.setReportDateStr(DateUtils.formatToChineseDate(rdata));
		}
		if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyDate())) {
			String cyDate = vo.getTaskVo().getCyDate().substring(0, 10);
			String cyEndDate = null;
			if (StrUtils.isNotBlankOrNull(vo.getTaskVo().getCyEndDate())) {
				cyEndDate = vo.getTaskVo().getCyEndDate().substring(0, 10);
				cyEndDate = DateUtils.getChineseDate(cyEndDate);
			}
			cyDate = DateUtils.getChineseDate(cyDate);
			if (null != cyEndDate && !cyEndDate.equals(cyDate)) {
				cyDate = cyDate + "~" + cyEndDate;
			}
			vo.setCyDate(cyDate);
		}
		String testDate = vo.getTestDate();
		String tesEndtDate = vo.getTestEndDate();
		if (!StrUtils.isBlankOrNull(testDate)) {
			testDate = DateUtils.getChineseDate(testDate);
		}
		if (!StrUtils.isBlankOrNull(tesEndtDate)) {
			tesEndtDate = DateUtils.getChineseDate(tesEndtDate);
		}
		if (!testDate.equals(tesEndtDate)) {
			testDate = testDate + "~" + tesEndtDate;
		}
		vo.setTestDate(testDate);
	}

	// 获取 职卫报告文件 检测结果 第一次循环 日期循环
	@SuppressWarnings("unchecked")
	public List<RtabRowVo> getDateRow(TaskVo taskVo, TaskItemVo timVo, int num) throws GlobalException {
		String sql = "select distinct cy_date from v_bus_test_item where is_del=" + Po.N + " and item_id like '" + timVo.getItemId() + "' and task_id='"
				+ taskVo.getId() + "' order by cy_date asc";
		List<String> dateList = taskPointDao.queryBysql(sql).getResultList();
		List<RtabRowVo> dateRowList = null;
		if (null != dateList && dateList.size() > 0) {
			dateRowList = new ArrayList<RtabRowVo>();
			for (String date : dateList) {
				RtabRowVo dateRowVo = new RtabRowVo();
				dateRowVo.setDate(date);
				List<RtabRoomVo> roomList = getRoomRow(taskVo, timVo, date, num);
				if (null != roomList && roomList.size() > 0) {
					dateRowVo.setRoomList(roomList);
					dateRowList.add(dateRowVo);
				}
			}
		}
		return dateRowList;
	}

	@SuppressWarnings("unchecked")
	public List<RtabRowVo> getDateRow4Fs(TaskVo taskVo, TaskItemVo timVo, int num) throws GlobalException {
		String sql = "select distinct cy_date from v_bus_test_item where is_del=" + Po.N + " and item_id like '" + timVo.getItemId() + "' and task_id='"
				+ taskVo.getId() + "' order by cy_date asc";
		List<String> dateList = taskPointDao.queryBysql(sql).getResultList();
		List<RtabRowVo> dateRowList = null;
		if (null != dateList && dateList.size() > 0) {
			dateRowList = new ArrayList<RtabRowVo>();
			for (String date : dateList) {
				RtabRowVo dateRowVo = new RtabRowVo();
				dateRowVo.setDate(date);
				List<RtabRoomVo> roomList = getRoomRow4Fs(taskVo, timVo, date, num);
				if (null != roomList && roomList.size() > 0) {
					dateRowVo.setRoomList(roomList);
					dateRowList.add(dateRowVo);
				}
			}
		}
		return dateRowList;
	}

	@SuppressWarnings("unchecked")
	public List<RtabRoomVo> getRoomRow4Fs(TaskVo taskVo, TaskItemVo timVo, String date, int num) throws GlobalException {
		String sql = "select distinct pt.room from v_bus_task_point pt join v_bus_test_item it on pt.id=it.point_id and it.item_id like '" + timVo.getItemId()
				+ "' and it.is_del=" + Po.N + " and it.task_id='" + taskVo.getId() + "' and cy_date like '" + date + "%' where pt.is_del=" + Po.N
				+ " and pt.task_id='" + taskVo.getId() + "' order by pt.sort asc";
		List<String> rmList = taskPointDao.queryBysql(sql).getResultList();
		List<RtabRoomVo> roomList = null;
		if (null != rmList && rmList.size() > 0) {
			int n = 1;
			roomList = new ArrayList<RtabRoomVo>();
			for (String room : rmList) {
				RtabRoomVo roomVo = new RtabRoomVo();
				roomVo.setRoom(room);
				String hql = "FROM " + testItemDao.getEntityName(TestItem.class) + " WHERE isDel='" + Po.N + "' AND tim.id ='" + timVo.getId()
						+ "' and point.room like '" + room + "' and cy_date like '" + date + "%' ORDER BY sort asc";
				List<TestItem> itList = testItemDao.list(hql);
				List<TestItemVo> itVoList = new ArrayList<>();
				if (null != itList && itList.size() > 0) {
					for (TestItem it : itList) {
						TestItemVo itVo = new TestItemVo();
						itVo = itVo.toVo(it);
						itVo.setValue(StrUtils.escapeWord(itVo.getValue()));
						itVo.setValue2(StrUtils.escapeWord(itVo.getValue2()));
						itVo.setMac(StrUtils.escapeWord(itVo.getMac()));
						itVo.setStel(StrUtils.escapeWord(itVo.getStel()));
						itVo.setTwa(StrUtils.escapeWord(itVo.getTwa()));
						if (it.getType().equals(TaskItem.ITEM_TYPE_XC) && (it.getItemName().contains("空气比释动能率") || it.getItemName().contains("X射线"))) {
							itVo.setTrVo(findResult4fs(it.getId(), itVo, n));
						}
						itVo.setSort(n);
						itVoList.add(itVo);
						n++;
					}
					roomVo.setItList(itVoList);
					roomList.add(roomVo);
				}
			}
		}
		return roomList;
	}

	// 获取 职卫报告文件 检测结果 第一次循环 日期循环
	@SuppressWarnings("unchecked")
	public List<RtabRoomVo> getRoomRow(TaskVo taskVo, TaskItemVo timVo, String date, int num) throws GlobalException {
		String sql = "select distinct pt.room from v_bus_task_point pt join v_bus_test_item it on pt.id=it.point_id and it.item_id like '" + timVo.getItemId()
				+ "' and it.is_del=" + Po.N + " and it.task_id='" + taskVo.getId() + "' and cy_date like '" + date + "%' where pt.is_del=" + Po.N
				+ " and pt.task_id='" + taskVo.getId() + "' order by pt.sort asc";
		List<String> rmList = taskPointDao.queryBysql(sql).getResultList();
		List<RtabRoomVo> roomList = null;
		if (null != rmList && rmList.size() > 0) {
			int n = 1;
			roomList = new ArrayList<RtabRoomVo>();
			for (String room : rmList) {
				RtabRoomVo roomVo = new RtabRoomVo();
				roomVo.setRoom(room);
				String hql = "FROM " + testItemDao.getEntityName(TestItem.class) + " WHERE isDel='" + Po.N + "' AND tim.id ='" + timVo.getId()
						+ "' and point.room like '" + room + "' and cy_date like '" + date + "%' ORDER BY sort asc";
				List<TestItem> itList = testItemDao.list(hql);
				List<TestItemVo> itVoList = new ArrayList<>();
				if (null != itList && itList.size() > 0) {
					for (TestItem it : itList) {
						TestItemVo itVo = new TestItemVo();
						itVo = itVo.toVo(it);
						itVo.setValue(StrUtils.escapeWord(itVo.getValue()));
						itVo.setValue2(StrUtils.escapeWord(itVo.getValue2()));
						itVo.setMac(StrUtils.escapeWord(itVo.getMac()));
						itVo.setStel(StrUtils.escapeWord(itVo.getStel()));
						itVo.setTwa(StrUtils.escapeWord(itVo.getTwa()));
						if (it.getType().equals(TaskItem.ITEM_TYPE_XC) && !it.getItemName().contains("粉尘")) {
							itVo.setTrVo(findResult(it.getId(), itVo));
						} else {
							itVo.setTrArry(arry4Result(it.getId(), new String[num], itVo));
						}
						itVo.setSort(n);
						itVoList.add(itVo);
						n++;
					}
					roomVo.setItList(itVoList);
					roomList.add(roomVo);
				}
			}
		}
		return roomList;
	}

	// 获取放射线样品项目合格率结果
	@SuppressWarnings("unchecked")
	public List<RsampVo> getSampResult4FS(ReportVo vo) {
		List<RsampVo> sampList = new ArrayList<RsampVo>();
		// 判定 放射线
		String hql = "select id,itemId,itemName,appId,wd,sd FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N
				+ "' AND (itemName like '%空气比释动能率%' or itemName like '%X射线%') and task.id='" + vo.getTaskVo().getId() + "'";
		List<Object[]> timList = taskItemDao.query(hql).getResultList();
		String itemIds = "";
		Set<String> appIdSet = new HashSet<String>();
		Set<String> wdSet = new HashSet<String>();
		Set<String> sdSet = new HashSet<String>();
		if (timList != null && timList.size() > 0) {// 项目任务集合
			RsampVo sampVo = new RsampVo();
			// sampVo.setSampName("粉尘");
			List<RItemVo> itemList = new ArrayList<RItemVo>();
			int zs = 0;// 总点位数
			int zhgs = 0;// 合格点位数
			for (Object[] tim : timList) {
				if (String.valueOf(tim[2]).contains("空气比释动能率")) {
					sampVo.setSampName("空气比释动能率(X射线)");
				} else if (String.valueOf(tim[2]).contains("X射线")) {
					sampVo.setSampName("剂量当量率(X射线)");
				}

				RItemVo item = new RItemVo();
				item.setItemName(String.valueOf(tim[2]));
				if (null != tim[3]) {
					String appId = String.valueOf(tim[3]);
					appIdSet.addAll(Arrays.asList(appId.split(",")));
				}
				if (null != tim[4]) {
					String wd = String.valueOf(tim[4]) + "℃";
					if (!wdSet.contains(wd)) {
						wdSet.add(wd);
					}
				}
				if (null != tim[5]) {
					String sd = String.valueOf(tim[5]) + "%";
					if (!sdSet.contains(sd)) {
						sdSet.add(sd);
					}
				}
				String sql = "select count(id) from v_bus_task_point where is_del=" + Po.N + " and item_ids like '%" + String.valueOf(tim[1])
						+ "%' and task_id='" + vo.getTaskVo().getId() + "'";
				Object ptObj = taskPointDao.queryBysql(sql).getSingleResult();
				int dws = Integer.valueOf(ptObj.toString());
				if (dws > 0) {
					// 普通样 总数
					String ypSql = "select count(id) from v_bus_sampling where is_del='" + Po.N + "' and type='" + Sampling.SAMP_TYPE_PT + "' and zk_type='"
							+ Sampling.SAMP_TYPE_PT + "' and item_ids like '%" + String.valueOf(tim[1]) + "%' and task_id='" + vo.getTaskVo().getId() + "'";
					Object ypObj = samplingDao.queryBysql(ypSql).getSingleResult();
					int yps = Integer.valueOf(ypObj.toString());// 样品数
					String bhgSql = "select count(distinct point_id) from v_bus_test_item  where is_del='" + Po.N + "' and tim_id='" + String.valueOf(tim[0])
							+ "' and result='" + TaskItem.RESULT_NO + "' ";
					Object bhgObj = samplingDao.queryBysql(bhgSql).getSingleResult();
					int bhg = Integer.valueOf(bhgObj.toString());// 不合格点位数
					int hgs = dws - bhg;
					item.setYps(yps == 0 ? "/" : String.valueOf(yps));
					item.setDws(String.valueOf(dws));
					item.setHgs(String.valueOf(hgs));
					itemList.add(item);
					zs += dws;
					zhgs += hgs;
				}
				itemIds += "," + String.valueOf(tim[0]);
			}
			sampVo.setHgl(String.valueOf(zhgs * 100 / zs));// 计算合格率
			sampVo.setItemList(itemList);
			if (itemList.size() > 0) {
				sampList.add(sampVo);
			}
		}
		vo.setAppIds(String.join(",", appIdSet));
		vo.setWd(String.join(",", wdSet));
		vo.setSd(String.join(",", sdSet));
		return sampList;
	}

	// 获取样品项目合格率结果
	@SuppressWarnings("unchecked")
	public List<RsampVo> getSampResult(ReportVo vo) {
		List<RsampVo> sampList = new ArrayList<RsampVo>();
		// 判定 粉尘
		String hql = "select id,itemId,itemName,appId,wd,sd FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N
				+ "' AND (itemName like '总粉尘' or itemName like '呼吸性粉尘' or itemName like '粉尘分散度' or itemName like '游离二氧化硅含量') and task.id='"
				+ vo.getTaskVo().getId() + "'";
		List<Object[]> timList = taskItemDao.query(hql).getResultList();
		String itemIds = "";
		Set<String> appIdSet = new HashSet<String>();
		Set<String> wdSet = new HashSet<String>();
		Set<String> sdSet = new HashSet<String>();
		if (timList != null && timList.size() > 0) {// 项目任务集合
			RsampVo sampVo = new RsampVo();
			sampVo.setSampName("粉尘");
			List<RItemVo> itemList = new ArrayList<RItemVo>();
			int zs = 0;// 总点位数
			int zhgs = 0;// 合格点位数
			for (Object[] tim : timList) {
				RItemVo item = new RItemVo();
				item.setItemName(String.valueOf(tim[2]));
				if (null != tim[3]) {
					String appId = String.valueOf(tim[3]);
					appIdSet.addAll(Arrays.asList(appId.split(",")));
				}
				if (null != tim[4]) {
					String wd = String.valueOf(tim[4]) + "℃";
					if (!wdSet.contains(wd)) {
						wdSet.add(wd);
					}
				}
				if (null != tim[5]) {
					String sd = String.valueOf(tim[5]) + "%";
					if (!sdSet.contains(sd)) {
						sdSet.add(sd);
					}
				}
				String sql = "select count(id) from v_bus_task_point where is_del=" + Po.N + " and item_ids like '%" + String.valueOf(tim[1])
						+ "%' and task_id='" + vo.getTaskVo().getId() + "'";
				Object ptObj = taskPointDao.queryBysql(sql).getSingleResult();
				int dws = Integer.valueOf(ptObj.toString());
				if (dws > 0) {
					// 普通样 总数
					String ypSql = "select count(id) from v_bus_sampling where is_del='" + Po.N + "' and type='" + Sampling.SAMP_TYPE_PT + "' and zk_type='"
							+ Sampling.SAMP_TYPE_PT + "' and item_ids like '%" + String.valueOf(tim[1]) + "%' and task_id='" + vo.getTaskVo().getId() + "'";
					Object ypObj = samplingDao.queryBysql(ypSql).getSingleResult();
					int yps = Integer.valueOf(ypObj.toString());// 样品数
					String bhgSql = "select count(distinct point_id) from v_bus_test_item  where is_del='" + Po.N + "' and tim_id='" + String.valueOf(tim[0])
							+ "' and result='" + TaskItem.RESULT_NO + "' ";
					Object bhgObj = samplingDao.queryBysql(bhgSql).getSingleResult();
					int bhg = Integer.valueOf(bhgObj.toString());// 不合格点位数
					int hgs = dws - bhg;
					item.setYps(yps == 0 ? "/" : String.valueOf(yps));
					item.setDws(String.valueOf(dws));
					item.setHgs(String.valueOf(hgs));
					itemList.add(item);
					zs += dws;
					zhgs += hgs;
				}
				itemIds += "," + String.valueOf(tim[0]);
			}
			sampVo.setHgl(String.valueOf(zhgs * 100 / zs));// 计算合格率
			sampVo.setItemList(itemList);
			if (itemList.size() > 0) {
				sampList.add(sampVo);
			}
		}
		// 化学有害因素
		hql = "select id,itemId,itemName,appId,wd,sd FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N
				+ "' and sampTypeName like '化学有害因素' and id not in('" + itemIds.replace(",", "','") + "')  and task.id='" + vo.getTaskVo().getId() + "'";
		timList = taskItemDao.query(hql).getResultList();
		if (timList != null && timList.size() > 0) {// 项目任务集合
			RsampVo sampVo = new RsampVo();
			sampVo.setSampName("化学有害因素");
			List<RItemVo> itemList = new ArrayList<RItemVo>();
			int zs = 0;// 总点位数
			int zhgs = 0;// 合格点位数
			for (Object[] tim : timList) {
				RItemVo item = new RItemVo();
				item.setItemName(String.valueOf(tim[2]));
				if (null != tim[3]) {
					String appId = String.valueOf(tim[3]);
					appIdSet.addAll(Arrays.asList(appId.split(",")));
				}
				if (null != tim[4]) {
					String wd = String.valueOf(tim[4]) + "℃";
					if (!wdSet.contains(wd)) {
						wdSet.add(wd);
					}
				}
				if (null != tim[5]) {
					String sd = String.valueOf(tim[5]) + "%";
					if (!sdSet.contains(sd)) {
						sdSet.add(sd);
					}
				}
				String sql = "select count(id) from v_bus_task_point where is_del=" + Po.N + " and item_ids like '%" + String.valueOf(tim[1])
						+ "%' and task_id='" + vo.getTaskVo().getId() + "'";
				Object ptObj = taskPointDao.queryBysql(sql).getSingleResult();
				int dws = Integer.valueOf(ptObj.toString());
				if (dws > 0) {
					// 普通样 总数
					String ypSql = "select count(id) from v_bus_sampling where is_del='" + Po.N + "' and type='" + Sampling.SAMP_TYPE_PT + "' and zk_type='"
							+ Sampling.SAMP_TYPE_PT + "' and item_ids like '%" + String.valueOf(tim[1]) + "%' and task_id='" + vo.getTaskVo().getId() + "'";
					Object ypObj = samplingDao.queryBysql(ypSql).getSingleResult();
					int yps = Integer.valueOf(ypObj.toString());// 样品数
					String bhgSql = "select count(distinct point_id) from v_bus_test_item  where is_del='" + Po.N + "' and tim_id='" + String.valueOf(tim[0])
							+ "' and result='" + TaskItem.RESULT_NO + "' ";
					Object bhgObj = samplingDao.queryBysql(bhgSql).getSingleResult();
					int bhg = Integer.valueOf(bhgObj.toString());// 不合格点位数
					int hgs = dws - bhg;
					item.setYps(yps == 0 ? "/" : String.valueOf(yps));
					item.setDws(String.valueOf(dws));
					item.setHgs(String.valueOf(hgs));
					itemList.add(item);
					zs += dws;
					zhgs += hgs;
				}
				itemIds += "," + String.valueOf(tim[1]);
			}
			sampVo.setHgl(String.valueOf(zhgs * 100 / zs));// 计算合格率
			sampVo.setItemList(itemList);
			if (itemList.size() > 0) {
				sampList.add(sampVo);
			}
		}
		// 物理因素
		hql = "select id,itemId,itemName,appId,wd,sd FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N
				+ "' and sampTypeName like '物理因素' and task.id='" + vo.getTaskVo().getId() + "'";
		timList = taskItemDao.query(hql).getResultList();
		if (timList != null && timList.size() > 0) {// 项目任务集合
			RsampVo sampVo = new RsampVo();
			sampVo.setSampName("物理因素");
			List<RItemVo> itemList = new ArrayList<RItemVo>();
			int zs = 0;// 总点位数
			int zhgs = 0;// 合格点位数
			for (Object[] tim : timList) {
				RItemVo item = new RItemVo();
				item.setItemName(String.valueOf(tim[2]));
				if (null != tim[3]) {
					String appId = String.valueOf(tim[3]);
					appIdSet.addAll(Arrays.asList(appId.split(",")));
				}
				if (null != tim[4]) {
					String wd = String.valueOf(tim[4]) + "℃";
					if (!wdSet.contains(wd)) {
						wdSet.add(wd);
					}
				}
				if (null != tim[5]) {
					String sd = String.valueOf(tim[5]) + "%";
					if (!sdSet.contains(sd)) {
						sdSet.add(sd);
					}
				}
				String sql = "select count(id) from v_bus_task_point where is_del=" + Po.N + " and item_ids like '%" + String.valueOf(tim[1])
						+ "%' and task_id='" + vo.getTaskVo().getId() + "'";
				Object ptObj = taskPointDao.queryBysql(sql).getSingleResult();
				int dws = Integer.valueOf(ptObj.toString());
				if (dws > 0) {
					// 普通样 总数
					String ypSql = "select count(id) from v_bus_sampling where is_del='" + Po.N + "' and type='" + Sampling.SAMP_TYPE_PT + "' and zk_type='"
							+ Sampling.SAMP_TYPE_PT + "' and item_ids like '%" + String.valueOf(tim[1]) + "%' and task_id='" + vo.getTaskVo().getId() + "'";
					Object ypObj = samplingDao.queryBysql(ypSql).getSingleResult();
					int yps = Integer.valueOf(ypObj.toString());// 样品数
					String bhgSql = "select count(distinct point_id) from v_bus_test_item  where is_del='" + Po.N + "' and tim_id='" + String.valueOf(tim[0])
							+ "' and result='" + TaskItem.RESULT_NO + "' ";
					Object bhgObj = samplingDao.queryBysql(bhgSql).getSingleResult();
					int bhg = Integer.valueOf(bhgObj.toString());// 不合格点位数
					int hgs = dws - bhg;
					item.setYps(yps == 0 ? "/" : String.valueOf(yps));
					item.setDws(String.valueOf(dws));
					item.setHgs(String.valueOf(hgs));
					itemList.add(item);
					zs += dws;
					zhgs += hgs;
				}
				itemIds += "," + String.valueOf(tim[1]);
			}
			sampVo.setHgl(String.valueOf(zhgs * 100 / zs));// 计算合格率
			sampVo.setItemList(itemList);
			if (itemList.size() > 0) {
				sampList.add(sampVo);
			}
		}
		vo.setAppIds(String.join(",", appIdSet));
		vo.setWd(String.join(",", wdSet));
		vo.setSd(String.join(",", sdSet));
		return sampList;
	}

	// 定期文件 汇总文件数据源
	public void getDate4Dq(Report po, ReportVo vo) throws GlobalException {
		// 立项里的 主铺原料信息
		vo.setMtList(getMtList(vo.getTaskVo().getPid()));
		// 检测与评价依据
		vo.setDataList(getStandList(vo.getTaskVo().getId()));
		// 现场 点位 检测情况
		getRoomList(vo);
		// 检测结果信息组装
		getTimList4Dq(vo);
		// 报告日期
		if (!StrUtils.isBlankOrNull(vo.getReportDate())) {
			String rdata = vo.getReportDate();
			vo.setReportDate(DateUtils.getChineseDate(rdata));
			vo.setReportDateStr(DateUtils.formatToChineseDate(rdata));
		}
		// 采样日期
		String cyDate = vo.getCyDate();
		String cyEndDate = vo.getCyEndDate();
		cyDate = cyDate.replace("-", ".");
		if (!StrUtils.isBlankOrNull(cyEndDate)) {
			cyEndDate = cyEndDate.replace("-", ".");
			if (!cyDate.equals(cyEndDate)) {
				String ym = cyEndDate.substring(0, 7);
				String y = cyEndDate.substring(0, 4);
				if (cyDate.contains(ym)) {
					cyEndDate = cyEndDate.substring(8);
				} else if (cyDate.contains(y)) {
					cyEndDate = cyEndDate.substring(5);
				}
				cyDate = cyDate + "-" + cyEndDate;
			}
		}
		vo.setCyDate(cyDate);
		// 测试日期
		String testDate = vo.getTestDate();
		String testEndDate = vo.getTestEndDate();
		testDate = testDate.replace("-", ".");
		if (!StrUtils.isBlankOrNull(testEndDate)) {
			testEndDate = testEndDate.replace("-", ".");
			if (!testDate.equals(testEndDate)) {
				String ym = testEndDate.substring(0, 7);
				String y = testEndDate.substring(0, 4);
				if (testDate.contains(ym)) {
					testEndDate = testEndDate.substring(8);
				} else if (testDate.contains(y)) {
					testEndDate = testEndDate.substring(5);
				}
				testDate = testDate + "-" + testEndDate;
			}
		}
		vo.setTestDate(testDate);
		// 环境条件
		vo.setJchj(getJchj(vo));
	}

	// 立项里的 主铺原料信息
	public List<CustMaterialVo> getMtList(String projectId) throws GlobalException {
		List<CustMaterialVo> mtList = new ArrayList<CustMaterialVo>();
		List<CustMaterial> list = custMaterialDao.listByProjectId(projectId);
		if (null != list) {
			for (CustMaterial cm : list) {
				CustMaterialVo cmVo = new CustMaterialVo();
				cmVo = cmVo.toVo(cm);
				mtList.add(cmVo);
			}
		}
		return mtList;
	}

	// 检测与评价依据
	public List<RtabDataVo> getStandList(String taskId) throws GlobalException {
		List<RtabDataVo> standList = new ArrayList<RtabDataVo>();
		List<TaskItem> list = taskItemDao.listByTaskId(taskId);
		if (null != list) {
			Set<String> standSet = new HashSet<String>();
			Set<String> methodSet = new HashSet<String>();
			for (TaskItem tim : list) {
				standSet.add(tim.getStandId());
				methodSet.add(tim.getMethodId());
			}
			List<Pstandard> plist = pstandardDao.listByIds(String.join(",", standSet));
			if (null != plist) {
				for (Pstandard p : plist) {
					RtabDataVo timVo = new RtabDataVo();
					timVo.setCol1(p.getCode());// 编号
					timVo.setCol2(p.getName());// 名称
					standList.add(timVo);
				}
			}
			List<Method> mlist = methodDao.listByIds(String.join(",", methodSet));
			if (null != mlist) {
				for (Method m : mlist) {
					RtabDataVo timVo = new RtabDataVo();
					timVo.setCol1(m.getCode());// 编号
					timVo.setCol2(m.getName());// 名称
					standList.add(timVo);
				}
			}
		}
		return standList;
	}

	@SuppressWarnings("unchecked")
	public void getRoomList(ReportVo vo) {
		String sql = "select distinct room from v_bus_task_point where is_del=" + Po.N + " and task_id='" + vo.getTaskVo().getId() + "' order by sort asc";
		List<String> rmList = taskPointDao.queryBysql(sql).getResultList();
		List<RtabRoomVo> roomList = new ArrayList<RtabRoomVo>();
		if (null != rmList && rmList.size() > 0) {
			for (String room : rmList) {
				RtabRoomVo roomVo = new RtabRoomVo();
				roomVo.setRoom(room);
				String subsql = "select point_name,work_num,work_hours,all_item_name,item_names,cy_type,pc,fh,others from v_bus_task_point where is_del=" + Po.N
						+ " and task_id='" + vo.getTaskVo().getId() + "' and room like '" + room + "' order by sort asc";
				List<Object[]> ptList = taskPointDao.queryBysql(subsql).getResultList();
				if (null != ptList && ptList.size() > 0) {
					List<RtabDataVo> dataList = new ArrayList<RtabDataVo>();
					for (Object[] obj : ptList) {
						RtabDataVo dataVo = new RtabDataVo();
						dataVo.setCol1(obj[0] == null ? "" : obj[0].toString());
						dataVo.setCol2(obj[1] == null ? "" : obj[1].toString());
						dataVo.setCol3(obj[2] == null ? "" : obj[2].toString());
						dataVo.setCol4(obj[3] == null ? "" : obj[3].toString().replace(",", "、"));
						dataVo.setCol5(obj[4] == null ? "" : obj[4].toString().replace(",", "、"));
						if (StrUtils.isBlankOrNull(roomVo.getCyType())) {
							roomVo.setCyType(obj[5] == null ? "" : obj[5].toString());
						}
						if (StrUtils.isBlankOrNull(roomVo.getPc())) {
							roomVo.setPc(obj[6] == null ? "" : obj[6].toString());
						}
						if (StrUtils.isBlankOrNull(roomVo.getZyfh())) {
							roomVo.setZyfh(obj[7] == null ? "" : obj[7].toString());
						}
						if (StrUtils.isBlankOrNull(roomVo.getGrfh())) {
							roomVo.setGrfh(obj[8] == null ? "" : obj[8].toString());
						}
						dataList.add(dataVo);
					}
					roomVo.setDataList(dataList);
					roomList.add(roomVo);
				}
			}
			vo.setTestRange(String.join("、", rmList));
		}
		vo.setRoomList(roomList);
	};

	// 职业卫生 定期报告 数据结果组装
	public void getTimList4Dq(ReportVo vo) throws GlobalException {
		List<RtabVo> tabList = new ArrayList<RtabVo>(); // tab 集合 按 限值类型 a b c 其他等
		String hql = "FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
				+ "' and limited='a' ORDER BY sort asc";
		List<TaskItem> aList = taskItemDao.list(hql);
		if (null != aList && aList.size() > 0) {
			List<TaskItemVo> timList = new ArrayList<>();
			RtabVo tabVo = new RtabVo();
			tabVo.setType("a");
			for (TaskItem tim : aList) {// 限值为 a 的项目循环
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				timVo.setLimited(StrUtils.escapeWord(timVo.getLimited()));
				timVo.setMac(StrUtils.escapeWord(timVo.getMac()));
				timVo.setStel(StrUtils.escapeWord(timVo.getStel()));
				timVo.setTwa(StrUtils.escapeWord(timVo.getTwa()));
				List<RtabRoomVo> roomList = getRoomList(timVo);// 车间集合
				if (null != roomList && roomList.size() > 0) {
					timVo.setRoomList(roomList);// 车间集合
					timList.add(timVo);
				}
			}
			if (timList.size() > 0) {
				tabVo.setTimList(timList);
				tabList.add(tabVo);
			}
		}
		hql = "FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
				+ "' and limited='b' ORDER BY sort asc";
		List<TaskItem> bList = taskItemDao.list(hql);
		if (null != bList && bList.size() > 0) {
			List<TaskItemVo> timList = new ArrayList<>();
			RtabVo tabVo = new RtabVo();
			tabVo.setType("b");
			for (TaskItem tim : bList) {// 限值为 a 的项目循环
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				timVo.setLimited(StrUtils.escapeWord(timVo.getLimited()));
				timVo.setMac(StrUtils.escapeWord(timVo.getMac()));
				timVo.setStel(StrUtils.escapeWord(timVo.getStel()));
				timVo.setTwa(StrUtils.escapeWord(timVo.getTwa()));
				List<RtabRoomVo> roomList = getRoomList(timVo);// 车间集合
				if (null != roomList && roomList.size() > 0) {
					timVo.setRoomList(roomList);// 车间集合
					timList.add(timVo);
				}
			}
			if (timList.size() > 0) {
				tabVo.setTimList(timList);
				tabList.add(tabVo);
			}
		}
		hql = "FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
				+ "' and limited='c' ORDER BY sort asc";
		List<TaskItem> cList = taskItemDao.list(hql);
		if (null != cList && cList.size() > 0) {
			List<TaskItemVo> timList = new ArrayList<>();
			RtabVo tabVo = new RtabVo();
			tabVo.setType("c");
			for (TaskItem tim : cList) {// 限值为 a 的项目循环
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				timVo.setLimited(StrUtils.escapeWord(timVo.getLimited()));
				timVo.setMac(StrUtils.escapeWord(timVo.getMac()));
				timVo.setStel(StrUtils.escapeWord(timVo.getStel()));
				timVo.setTwa(StrUtils.escapeWord(timVo.getTwa()));
				List<RtabRoomVo> roomList = getRoomList(timVo);// 车间集合
				if (null != roomList && roomList.size() > 0) {
					timVo.setRoomList(roomList);// 车间集合
					timList.add(timVo);
				}
			}
			if (timList.size() > 0) {
				tabVo.setTimList(timList);
				tabList.add(tabVo);
			}
		}
		// 标准值：照度
		hql = "FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
				+ "' and itemName like '照度' ORDER BY sort asc";
		List<TaskItem> bzList = taskItemDao.list(hql);
		if (null != bzList && bzList.size() > 0) {
			List<TaskItemVo> timList = new ArrayList<>();
			for (TaskItem tim : bzList) {// 限值为 a 的项目循环
				RtabVo tabVo = new RtabVo();
				tabVo.setType("标准值");
				tabVo.setUnit(tim.getUnit());
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				String limited = timVo.getLimited();
				if (!StrUtils.isBlankOrNull(limited)) {
					limited = limited.replace("<", "").replace(">", "").replace("≤", "").replace("≥", "");
				}
				timVo.setLimited(StrUtils.escapeWord(limited));
				timVo.setMac(StrUtils.escapeWord(timVo.getMac()));
				timVo.setStel(StrUtils.escapeWord(timVo.getStel()));
				timVo.setTwa(StrUtils.escapeWord(timVo.getTwa()));
				List<RtabRoomVo> roomList = getRoomList(timVo);// 车间集合
				if (null != roomList && roomList.size() > 0) {
					timVo.setRoomList(roomList);// 车间集合
					timList.add(timVo);
				}
				if (timList.size() > 0) {
					tabVo.setTimList(timList);
					tabList.add(tabVo);
				}
			}
		}
		// 其他
		hql = "FROM " + taskItemDao.getEntityName(TaskItem.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + vo.getTaskVo().getId()
				+ "' and limited<>'a' and limited<>'b'  and limited<>'c' and itemName not like '照度' ORDER BY sort asc";
		List<TaskItem> pList = taskItemDao.list(hql);
		if (null != pList && pList.size() > 0) {
			for (TaskItem tim : pList) {// 限值为 a 的项目循环
				RtabVo tabVo = new RtabVo();
				tabVo.setType(tim.getItemName());
				tabVo.setUnit(tim.getUnit());
				List<TaskItemVo> timList = new ArrayList<>();
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				String limited = timVo.getLimited();
				if (!StrUtils.isBlankOrNull(limited)) {
					limited = limited.replace("<", "").replace(">", "").replace("≤", "").replace("≥", "");
				}
				timVo.setLimited(StrUtils.escapeWord(limited));
				timVo.setMac(StrUtils.escapeWord(timVo.getMac()));
				timVo.setStel(StrUtils.escapeWord(timVo.getStel()));
				timVo.setTwa(StrUtils.escapeWord(timVo.getTwa()));
				// timVo.setLimited(limited);
				List<RtabRoomVo> roomList = getRoomList(timVo);// 车间集合
				if (null != roomList && roomList.size() > 0) {
					timVo.setRoomList(roomList);// 车间集合
					timList.add(timVo);
				}
				if (timList.size() > 0) {
					tabVo.setTimList(timList);
					tabList.add(tabVo);
				}
			}
		}
		vo.setWtabList(tabList);
	}

	// 获取车间集合
	@SuppressWarnings("unchecked")
	public List<RtabRoomVo> getRoomList(TaskItemVo timVo) throws GlobalException {
		String subsql = "select distinct room from v_bus_task_point where is_del=" + Po.N + " and task_id='" + timVo.getTaskVo().getId()
				+ "' and item_ids like '%" + timVo.getItemId() + "%' order by sort asc";
		List<String> ptList = taskPointDao.queryBysql(subsql).getResultList();
		List<RtabRoomVo> roomList = null;
		if (null != ptList && ptList.size() > 0) {
			roomList = new ArrayList<>();
			for (String room : ptList) {
				RtabRoomVo roomVo = new RtabRoomVo();
				roomVo.setRoom(room);
				String hql = "FROM " + testItemDao.getEntityName(TestItem.class) + " WHERE isDel='" + Po.N + "' AND tim.id ='" + timVo.getId()
						+ "' and point.room like '" + room + "' ORDER BY point.sort,sort asc";
				List<TestItem> itList = testItemDao.list(hql);
				if (null != itList && itList.size() > 0) {
					List<TestItemVo> itVoList = new ArrayList<>();
					for (TestItem it : itList) {
						TestItemVo itVo = new TestItemVo();
						itVo = itVo.toVo(it);
						itVo.setValue(StrUtils.escapeWord(itVo.getValue()));
						itVo.setMac(StrUtils.escapeWord(itVo.getMac()));
						itVo.setStel(StrUtils.escapeWord(itVo.getStel()));
						itVo.setTwa(StrUtils.escapeWord(itVo.getTwa()));
						itVoList.add(itVo);
					}
					roomVo.setItList(itVoList);
					roomList.add(roomVo);
				}
			}
		}
		return roomList;
	}

	@Override
	public void updateBack(ReportVo v) throws GlobalException {
		Report rpo = reportDao.findById(v.getId());
		Task task = rpo.getTask();
		List<TaskItem> timList = taskItemDao.listByTaskId(task.getId());
		Progress pg = null;
		Org org = orgDao.findByName("质量管控中心");
		for (TaskItem tim : timList) {
			// 新增进度日志
			pg = progressDao.update(tim.getProgress().getId(), EunmTask.ITEM_HZ.getStatus(), null, null, null, null);
			tim.setStatus(pg.getStatus());
			taskItemDao.update(tim);
		}

		Task po = taskDao.findById(task.getId());
		// Org org = null;
		// if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
		// org = orgDao.findByName("环境");
		// } else {
		// org = orgDao.findByName("职业卫生");
		// }
		// task.setOrgId(org.getId());
		// task.setOrgName(org.getName());

		pg = progressDao.update(po.getProgress().getId(), EunmTask.TASK_HZ.getStatus(), null, null, null, null);
		po.setStatus(pg.getStatus());// 数据录入状态
		taskDao.update(po);

		Report report = reportDao.findByTaskId(po.getId());
		pg = report.getProgress();
		progressLogDao.add(report.getTask().getId(), report.getTask().getId(), EunmTask.TASK_BZ.getStatus(), EunmTask.PASS_N, v.getRemark());
		if (!StrUtils.isBlankOrNull(report.getReptUserId())) {
			pg = progressDao.update(pg.getId(), EunmTask.TASK_HZ.getStatus(), null, null, report.getReptUserId(), report.getReptUser());
		}
		report.setIsBack(Constants.Y);
		report.setStatus(pg.getStatus());
		reportDao.update(report);
	}
}
