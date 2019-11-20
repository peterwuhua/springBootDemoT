package cn.demi.bus.sample.service.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EnumCydfile;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISampCydService;
import cn.demi.bus.sample.vo.SampCydVo;
import cn.demi.bus.sample.vo.SampRecordVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskPointService;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.service.ITestResultService;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.ISampSourceDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.SampSource;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;

@Service("bus.sampCydService")
public class SampCydServiceImpl extends BaseServiceImpl<SampCydVo, SampCyd> implements ISampCydService {

	@Autowired
	private ISampRecordDao sampRecordDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private ISampSourceDao sampSourceDao;
	@Autowired
	private ITestResultService testResultService;
	@Autowired
	private ITaskPointService taskPointService;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private IApparaDao apparaDao;

	@Override
	public SampCydVo find4Cyd(String id) throws GlobalException {
		SampCyd cyd = sampCydDao.findById(id);
		SampCydVo vo = po2Vo(cyd);
		if (StrUtils.isBlankOrNull(vo.getJhId())) {
			vo.setJhId(vo.getTaskVo().getFzId());
			vo.setJhName(vo.getTaskVo().getFzName());
		}
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND cyd.id ='" + cyd.getId()
				+ "'   ORDER BY zkType,itemIds,sort asc";
		List<Sampling> sampList = samplingDao.list(hql);
		List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
		if (null != sampList) {
			for (Sampling samp : sampList) {
				SamplingVo sampVo = new SamplingVo();
				sampVo = sampVo.toVo(samp);
				if (StrUtils.isBlankOrNull(sampVo.getWorkHours())) { // 采样单接触时间为空或null默认父页面现场情况里的接触时间
					sampVo.setWorkHours(samp.getPoint().getWorkHours());
				}
				if (StrUtils.isBlankOrNull(sampVo.getWorkPc())) { // 采样单接触频次为空或null默认父页面现场情况里的接触频次
					sampVo.setWorkPc(samp.getPoint().getWorkPc());
				}
				if (cyd.getType().equals(EnumCyd.HJ_G.getCode())) {// 固体底泥采样单数据
					SampRecordVo recordVo = sampVo.getRecordVo();
					if (recordVo == null) {
						recordVo = new SampRecordVo();
					}
					if (StrUtils.isBlankOrNull(recordVo.getDemo1())) {
						recordVo.setDemo1(samp.getPointCode());// 站点编码 默认采样点编号
					}
					sampVo.setRecordVo(recordVo);
				}
				if (StrUtils.isBlankOrNull(sampVo.getSampCode()) && !sampVo.getZkType().equals(Sampling.SAMP_TYPE_PT)) {
					int ptSort = taskPointDao.getSort4Code(cyd.getTask().getId(), samp.getPoint().getId(), samp.getSampTypeId());
					sampVo.setSampCode(samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP(),
							samp.getZkType()));
				} else if (StrUtils.isBlankOrNull(sampVo.getSampCode()) && sampVo.getZkType().equals(Sampling.SAMP_TYPE_PT)) {
					int ptSort = taskPointDao.getSort4Code(cyd.getTask().getId(), samp.getPoint().getId(), samp.getSampTypeId());
					if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
						sampVo.setSampCode(
								samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					} else {
						sampVo.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
								samp.getPoint().getSort(), samp.getP()));
					}
				}
				sampVoList.add(sampVo);
			}
		}
		vo.setSampList(sampVoList);

		// hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='"
		// + Po.N + "' AND cyd.id ='" + cyd.getId() + "' AND zkType<>'"
		// + Sampling.SAMP_TYPE_PT + "' ORDER BY sort asc";
		// List<Sampling> zkList = samplingDao.list(hql);
		// List<SamplingVo> zkVoList = new ArrayList<SamplingVo>();
		// if (null != sampList) {
		// for (Sampling samp : zkList) {
		// SamplingVo sampVo = new SamplingVo();
		// sampVo = sampVo.toVo(samp);
		// if(cyd.getType().equals(EnumCyd.HJ_G.getCode())) {//固体底泥采样单数据
		// SampRecordVo recordVo=sampVo.getRecordVo();
		// if(recordVo==null) {
		// recordVo=new SampRecordVo();
		// }
		// if(StrUtils.isBlankOrNull(recordVo.getDemo1())) {
		// recordVo.setDemo1(samp.getPointCode());//站点编码 默认采样点编号
		// }
		// sampVo.setRecordVo(recordVo);
		// }
		//
		// zkVoList.add(sampVo);
		// }
		// }
		// vo.setZkList(zkVoList);
		return vo;
	}

	@Override
	public void updateCyd(SampCydVo v) throws GlobalException {
		SampCyd cyd = sampCydDao.findById(v.getId());
		cyd.setCyId(v.getCyId());
		cyd.setCyName(v.getCyName());
		cyd.setFxId(v.getFxId());
		cyd.setFxName(v.getFxName());
		cyd.setJhId(v.getJhId());
		cyd.setJhName(v.getJhName());
		cyd.setPtUser(v.getPtUser());
		cyd.setSampNum(v.getSampNum());
		if (!StrUtils.isBlankOrNull(v.getCyAppId())) {
			List<Appara> applist = apparaDao.listByIds(v.getCyAppId());
			List<String> idList = new ArrayList<>();
			List<String> nameList = new ArrayList<>();
			for (Appara appara : applist) {
				idList.add(appara.getId());
				nameList.add(appara.getName() + appara.getSpec() + "(" + appara.getNo() + ")");
			}
			cyd.setCyAppId(String.join(",", idList));
			cyd.setCyAppName(String.join(",", nameList));
		}
		if (!StrUtils.isBlankOrNull(v.getCyStandId())) {
			List<SampSource> ssList = sampSourceDao.listByIds(v.getCyStandId());
			List<String> idList = new ArrayList<>();
			List<String> nameList = new ArrayList<>();
			for (SampSource ss : ssList) {
				idList.add(ss.getId());
				nameList.add(ss.getCode() + ss.getName());
			}
			cyd.setCyStandId(String.join(",", idList));
			cyd.setCyStandName(String.join(",", nameList));
		}
		cyd.setGnq(v.getGnq());
		cyd.setRunType(v.getRunType());
		cyd.setItemType(v.getItemType());
		cyd.setQw(v.getQw());
		cyd.setQy(v.getQy());
		cyd.setFx(v.getFx());
		cyd.setFs(v.getFs());
		cyd.setSd(v.getSd());
		cyd.setTx(v.getTx());
		cyd.setXcDesc(v.getXcDesc());
		cyd.setDeme1(v.getDeme1());
		cyd.setDeme2(v.getDeme2());
		cyd.setDeme3(v.getDeme3());
		cyd.setDeme4(v.getDeme4());
		cyd.setDeme5(v.getDeme5());
		cyd.setDeme6(v.getDeme6());
		cyd.setDeme7(v.getDeme7());
		cyd.setDeme8(v.getDeme8());
		cyd.setDeme9(v.getDeme9());
		cyd.setDeme10(v.getDeme10());
		cyd.setDeme11(v.getDeme11());
		cyd.setDeme12(v.getDeme12());
		cyd.setDeme13(v.getDeme13());
		cyd.setDeme14(v.getDeme14());
		cyd.setDeme15(v.getDeme15());
		cyd.setDeme16(v.getDeme16());
		cyd.setDeme17(v.getDeme17());
		cyd.setDeme18(v.getDeme18());
		cyd.setDeme19(v.getDeme19());
		cyd.setDeme20(v.getDeme20());
		cyd.setDeme21(v.getDeme21());
		cyd.setDeme22(v.getDeme22());
		cyd.setDeme23(v.getDeme23());
		cyd.setDeme24(v.getDeme24());
		cyd.setDeme25(v.getDeme25());
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_S)) {
			cyd.setCySt(TaskPoint.ST_1);
		} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			cyd.setCySt(TaskPoint.ST_2);
		}
		// 更新普通样
		List<SamplingVo> sampList = v.getSampList();
		int num = 0;
		if (sampList != null) {
			for (SamplingVo sampVo : sampList) {
				Sampling samp = samplingDao.findById(sampVo.getId());
				samp.setCyTime(sampVo.getCyTime());
				samp.setCyEndTime(sampVo.getCyEndTime());
				if (!StrUtils.isBlankOrNull(sampVo.getSampCode())) {
					samp.setSampCode(sampVo.getSampCode());
				}
				samp.setXz(sampVo.getXz());
				samp.setTjj(sampVo.getTjj());
				samp.setNum(sampVo.getNum());
				samp.setWorkHours(sampVo.getWorkHours());
				samp.setWorkPc(sampVo.getWorkPc());
				samp.setTj(sampVo.getTj());
				samp.setFcType(sampVo.getFcType());
				samp.setV1(sampVo.getV1());
				samp.setV2(sampVo.getV2());
				samp.setRemark(sampVo.getRemark());
				SampRecord record = samp.getRecord();
				if (record == null) {
					record = new SampRecord();
				}
				if (sampVo.getRecordVo() != null) {
					SampRecordVo recordVo = sampVo.getRecordVo();
					record = record.toPo(recordVo, record);
				}
				sampRecordDao.saveOrUpdate(record);
				samp.setRecord(record);
				if (samp.getPoint().getType() != null
						&& (samp.getPoint().getType().equals(Constants.SAMP_S) || samp.getPoint().getType().equals(Constants.SAMP_Q))) {
					if (!StrUtils.isBlankOrNull(samp.getCyEndTime()) && !StrUtils.isBlankOrNull(samp.getCyTime())) {
						long saveHour = DateUtils.getIntevalHours(samp.getCyDate() + " " + samp.getCyTime(), samp.getCyDate() + " " + samp.getCyEndTime());
						samp.setCyHours(Long.valueOf(saveHour).intValue());// 获取环境的气 声 采样时间长 用于计算小时 8 小时 日均值等数据
					}
				}
				samplingDao.update(samp);
				uptTaskItem(samp);
			}
			num += sampList.size();
		}
		// 更新质控样
		// List<SamplingVo> zkList = v.getZkList();
		// if (null != zkList) {
		// for (SamplingVo sampVo : zkList) {
		// Sampling samp = samplingDao.findById(sampVo.getId());
		// samp.setCyTime(sampVo.getCyTime());
		// samp.setCyEndTime(sampVo.getCyEndTime());
		// samp.setSampCode(sampVo.getSampCode());
		// samp.setXz(sampVo.getXz());
		// samp.setNum(sampVo.getNum());
		// SampRecord record = samp.getRecord();
		// if(record==null) {
		// record=new SampRecord();
		// }
		// if (sampVo.getRecordVo() != null) {
		// SampRecordVo recordVo = sampVo.getRecordVo();
		// record = record.toPo(recordVo, record);
		// }
		// sampRecordDao.saveOrUpdate(record);
		// samp.setRecord(record);
		// samplingDao.update(samp);
		// uptTaskItem(samp);
		// }
		// num += zkList.size();
		// }
		cyd.setSampNum(num);
		sampCydDao.update(cyd);

	}

	// 已生成的现场项目任务的，更新样品信息 同步到项目任务表中
	public void uptTaskItem(Sampling samp) {
		List<Item> itemList = itemDao.listByIds(samp.getItemIds(), Constants.S);
		if (null != itemList) {
			for (Item item : itemList) {
				TestResult tr = testResultDao.findBySampAndItem(samp.getId(), item.getId());
				if (null != tr) {
					TestItem it = tr.getIt();
					if (null != samp.getRecord()) {
						if ((item.getName().contains("一氧化碳") || item.getName().contains("二氧化碳"))) {
							it.setStel(samp.getRecord().getAvg1());
							it.setTwa(samp.getRecord().getAvg2());
						} else if ((item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场")
								|| item.getName().contains("激光辐射") || item.getName().contains("微波辐射"))) {
							checkAvg(samp, item);
							it.setValue(samp.getRecord().getAvg1());
						} else if (item.getName().contains("高频电磁场")) {
							checkAvg(samp, item);
							it.setValue(samp.getRecord().getAvg1());// 电场
							it.setValue2(samp.getRecord().getAvg2());// 磁场
						} else if (item.getName().contains("紫外辐射")) {
							it.setValue(samp.getRecord().getAvg1());
						} else if ((item.getName().contains("手传振动") || item.getName().contains("噪声")) && null != samp.getRecord()) {
							it.setValue(samp.getRecord().getAvg1());
							it.setValue2(samp.getRecord().getAvg2());
						} else if (item.getName().contains("照度")) {
							if (null != samp.getCyd().getItemType() && samp.getCyd().getItemType().equals("照度平均值")) {
								it.setValue(samp.getRecord().getAvg1());
							} else {
								it.setValue(samp.getRecord().getAvg2());
							}
						} else if (item.getName().contains("高温")) {
							double d1 = 0;
							try {
								d1 = Double.valueOf(samp.getRecord().getAvg1());
							} catch (Exception e) {
								d1 = 0;
							}
							double d2 = 0;
							try {
								d2 = Double.valueOf(samp.getRecord().getAvg2());
							} catch (Exception e) {
								d2 = 0;
							}
							double d3 = 0;
							try {
								d3 = Double.valueOf(samp.getRecord().getAvg3());
							} catch (Exception e) {
								d3 = 0;
							}
							try {
								NumberFormat ddf1 = NumberFormat.getNumberInstance();
								ddf1.setMaximumFractionDigits(1);
								it.setValue(ddf1.format((d1 + d2 + d3) / 3));
							} catch (Exception e) {
								it.setValue("");
							}
						} else if (item.getName().contains("空气比释动能率")) {
							double d1 = 0;
							try {
								d1 = Double.valueOf(samp.getRecord().getV1());
							} catch (Exception e) {
								d1 = 0;
							}
							double d2 = 0;
							try {
								d2 = Double.valueOf(samp.getRecord().getV2());
							} catch (Exception e) {
								d2 = 0;
							}
							double d3 = 0;
							try {
								d3 = Double.valueOf(samp.getRecord().getV3());
							} catch (Exception e) {
								d3 = 0;
							}
							try {
								NumberFormat ddf1 = NumberFormat.getNumberInstance();
								ddf1.setMaximumFractionDigits(1);
								it.setValue(ddf1.format((d1 + d2 + d3) / 3));
							} catch (Exception e) {
								it.setValue("");
							}
						}
					}
					it.setResult(TaskItem.RESULT_YES);
					testItemDao.update(it);
				}
			}
		}
	}

	// 检查均值，为空 择自动计算
	public void checkAvg(Sampling samp, Item item) {
		SampRecord record = samp.getRecord();
		if (StrUtils.isBlankOrNull(record.getAvg1())) {
			double v1 = 0;
			if (item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场") || item.getName().contains("激光辐射")
					|| item.getName().contains("高频电磁场") || item.getName().contains("微波辐射")) {
				try {
					v1 += Double.valueOf(record.getV4());
				} catch (NumberFormatException e) {
					v1 += 0;
				}
				try {
					v1 += Double.valueOf(record.getV5());
				} catch (NumberFormatException e) {
					v1 += 0;
				}
				try {
					v1 += Double.valueOf(record.getV6());
				} catch (NumberFormatException e) {
					v1 += 0;
				}
				String v = String.valueOf(v1 / 3);
				if (v.indexOf(".") > 0) {
					v = v.substring(0, v.indexOf("."));
				}
				record.setAvg1(v);
			}
		}
		if (StrUtils.isBlankOrNull(record.getAvg2())) {
			double v2 = 0;
			if (item.getName().contains("高频电磁场")) {
				try {
					v2 += Double.valueOf(record.getV10());
				} catch (NumberFormatException e) {
					v2 += 0;
				}
				try {
					v2 += Double.valueOf(record.getV11());
				} catch (NumberFormatException e) {
					v2 += 0;
				}
				try {
					v2 += Double.valueOf(record.getV12());
				} catch (NumberFormatException e) {
					v2 += 0;
				}
				String v = String.valueOf(v2 / 3);
				if (v.indexOf(".") > 0) {
					v = v.substring(0, v.indexOf("."));
				}
				record.setAvg2(v);
			}
		}
		sampRecordDao.update(record);
	}

	@Override
	public SampCydVo find4File(String id) throws GlobalException {
		SampCyd cyd = sampCydDao.findById(id);
		SampCydVo vo = po2Vo(cyd);
		if (!StrUtils.isBlankOrNull(vo.getCyDate())) {
			String date = vo.getCyDate();
			if (date.indexOf("-") > -1) {
				String[] dates = date.split("-");
				String year = dates[0];
				String month = dates[1];
				if (month.length() < 2) {
					month = "0" + month;
				}
				String day = dates[2];
				if (day.length() < 2) {
					day = "0" + day;
				}
				String jcdate = year + " 年 " + month + " 月 " + day + " 日";
				vo.setJcchDay(jcdate);
			}
		}

		if (vo.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			find4FileHj(cyd, vo);
		} else {
			find4FileZw(cyd, vo);
		}
		return vo;
	}

	// 职卫采样单文件
	public SampCydVo find4FileZw(SampCyd cyd, SampCydVo vo) throws GlobalException {
		// 查询所有类型的采样记录单
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND cyd.id ='" + cyd.getId() + "'  "
				+ " ORDER BY sort asc";
		List<Sampling> sampList = samplingDao.list(hql);
		List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
		if (null != sampList) {
			for (Sampling samp : sampList) {
				SamplingVo sampVo = new SamplingVo();
				sampVo = sampVo.toVo(samp);
				sampVoList.add(sampVo);
			}
		}
		vo.setSampList(sampVoList);
		return vo;
	}

	// 环境采样单文件
	public SampCydVo find4FileHj(SampCyd cyd, SampCydVo vo) throws GlobalException {
		// 报告编号
		if (vo.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			String reportNo = vo.getTaskVo().getReportNo();
			String sampTypeId = vo.getTaskVo().getSampTypeId();
			String type = "综";
			if (!sampTypeId.contains(",")) {
				SampType sampType = sampTypeDao.findById(sampTypeId);
				type = sampType.getType();
			}
			reportNo = "环（" + type + "）" + reportNo;
			vo.getTaskVo().setReportNo(reportNo);
		}
		// 查询所有类型的采样记录单
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND cyd.id ='" + cyd.getId() + "'  " + " AND type='"
				+ Sampling.SAMP_TYPE_PT + "' ORDER BY sort asc";
		List<Sampling> sampList = samplingDao.list(hql);
		List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
		if (null != sampList) {
			for (Sampling samp : sampList) {
				SamplingVo sampVo = new SamplingVo();
				sampVo = sampVo.toVo(samp);
				sampVoList.add(sampVo);
			}
		}
		vo.setSampList(sampVoList);
		if (cyd.getTemp() != null) {
			// 用于生成的采样单文件的数据
			if (cyd.getTemp().equals(EnumCydfile.HJ_S_SH.getCode())) {
				vo = fetchZY(vo); // 获取昼夜
			} else if (cyd.getTemp().equals(EnumCydfile.HJ_S_QT.getCode())) {
				// 测量时间格式为（xx月xx日xx时）
				String sdate = "";
				String cyDatestr = "";
				if (vo.getCyDate() != null && vo.getCyDate().contains("-")) {
					if (vo.getCyDate().contains(" ")) {
						cyDatestr = vo.getCyDate().split(" ")[0];
					}
					List<SamplingVo> samps = vo.getSampList();
					for (int i = 0; i < samps.size(); i++) {
						if (samps.get(i).getCyTime() != null && samps.get(i).getCyTime().contains(":")) {
							sdate = ""; // 每次循环需要清空之前的值
							String[] cyDates = cyDatestr.split("-");
							sdate = cyDates[1] + " 月 " + cyDates[2] + " 日 ";
							String[] cytimes = samps.get(i).getCyTime().split(":");
							sdate = sdate + cytimes[0] + " 时";
							samps.get(i).setCyTimeStr(sdate);
						}
					}
					vo.setSampList(samps);
				}
			} else if (cyd.getTemp().equals(EnumCydfile.HJ_W.getCode())) {// 水和废水采样单
				// 查询现场数据是否有PH,流量,电导率,水温的值，如果没有采样单里给 “/”
				List<SamplingVo> samps = new ArrayList<>();
				for (int i = 0; i < vo.getSampList().size(); i++) {
					SamplingVo svo = vo.getSampList().get(i);
					String sampCode = svo.getSampCode();
					if (vo.getItemNames().contains("pH值")) {
						// 取值
						String phv = testResultService.findVal4Cyd(sampCode, "pH值");
						svo.getRecordVo().setDemo1(phv);
					} else {
						svo.getRecordVo().setDemo1("/");
					}
					if (vo.getItemNames().contains("流量")) {
						// 取值
						String lv = testResultService.findVal4Cyd(sampCode, "流量");
						svo.getRecordVo().setDemo2(lv);
					} else {
						svo.getRecordVo().setDemo2("/");
					}
					if (vo.getItemNames().contains("水温")) {
						// 取值
						String sw = testResultService.findVal4Cyd(sampCode, "水温");
						svo.getRecordVo().setDemo3(sw);
					} else {
						svo.getRecordVo().setDemo3("/");
					}
					if (vo.getItemNames().contains("电导率")) {
						// 取值
						String ddl = testResultService.findVal4Cyd(sampCode, "电导率");
						svo.getRecordVo().setDemo4(ddl);
					} else {
						svo.getRecordVo().setDemo4("/");
					}
					if (vo.getItemNames().contains("溶解氧")) {
						// 取值
						String ddl = testResultService.findVal4Cyd(sampCode, "溶解氧");
						svo.getRecordVo().setDemo5(ddl);
					} else {
						svo.getRecordVo().setDemo5("/");
					}
					samps.add(svo);
				}
				vo.setSampList(samps);
			} else if (cyd.getTemp().equals(EnumCydfile.HJ_YQ_GDY.getCode()))// 有组织废气（固定源）
			{
				for (int i = 0; i < vo.getSampList().size(); i++) {
					SamplingVo s = vo.getSampList().get(0);
					vo.setPqwd(s.getRecordVo().getDemo12());// 排气温度
					vo.setTgxs(s.getRecordVo().getDemo17());// 托管系数
					vo.setGdjy(s.getRecordVo().getDemo15());// 管道静压
					vo.setGddy(s.getRecordVo().getDemo19());// 管道动压
					vo.setHsl(s.getRecordVo().getDemo13());// 含湿量
					vo.setPqls(s.getRecordVo().getDemo20());// 排气流速
					vo.setBgll(s.getRecordVo().getDemo22());// 标干流量
					vo.setHjwd(s.getRecordVo().getDemo10());// 环境温度
					vo.setDqy(s.getRecordVo().getDemo9());// 大气压
				}
			} else if (cyd.getTemp().equals(EnumCydfile.HJ_WQ.getCode()))// 无组织废气
			{
				TaskPointVo pvo = taskPointService.findById(vo.getPointId());
				vo.setPointCode(pvo.getPointCode());// 监测点编号
			} else if (cyd.getTemp().equals(EnumCydfile.HJ_G_GF.getCode()))// 固废
			{
				for (int i = 0; i < vo.getSampList().size(); i++) {
					SampRecordVo srvo = vo.getSampList().get(i).getRecordVo();
					String demo11 = "";
					if (!StrUtils.isBlankOrNull(srvo.getDemo4())) {
						demo11 += "嗅味:";
						demo11 += srvo.getDemo4();
						demo11 += ",";
					}
					if (!StrUtils.isBlankOrNull(srvo.getDemo5())) {
						demo11 += "颜色:";
						demo11 += srvo.getDemo5();
						demo11 += ",";
					}
					if (!StrUtils.isBlankOrNull(srvo.getDemo6())) {
						demo11 += "表观:";
						demo11 += srvo.getDemo6();
						demo11 += ",";
					}
					if (!StrUtils.isBlankOrNull(srvo.getDemo7())) {
						demo11 += "其它:";
						demo11 += srvo.getDemo7();
						demo11 += ",";
					}

					demo11 = demo11.substring(0, demo11.length() - 1);
					vo.getSampList().get(i).getRecordVo().setDemo11(demo11);// 样品性状文字组合（嗅味：无味，颜色：无色）
				}
			}
		}
		return vo;
	}

	@Override
	public void updateFile(SampCydVo v) throws GlobalException {
		SampCyd cyd = sampCydDao.findById(v.getId());
		cyd.setTemp(v.getTemp());
		cyd.setFileName(v.getFileName());
		cyd.setFilePath(v.getFilePath());
		sampCydDao.update(cyd);
	}

	private SampCydVo fetchZY(SampCydVo vo) throws GlobalException {
		List<SamplingVo> sampList = vo.getSampList();
		for (int i = 0; i < sampList.size(); i++) {
			String cyTime = sampList.get(i).getCyTime(); // 00:02
			if (!StrUtils.isBlankOrNull(cyTime)) {
				try {
					Date cyDTime = DateUtils.parse(cyTime, "HH:mm");
					Calendar c = Calendar.getInstance();
					c.setTime(cyDTime);
					c.add(Calendar.HOUR_OF_DAY, 24);

					long cyDTimes = c.getTimeInMillis();
					Date startTime = DateUtils.parse("07:00", "HH:mm");
					long startTimes = startTime.getTime();
					Date endTime = DateUtils.parse("20:00", "HH:mm");
					long endTimes = endTime.getTime();
					if (cyDTimes >= startTimes && cyDTimes <= endTimes) {
						sampList.get(i).setZy("昼");
					}

					Date stTime = DateUtils.parse("20:00", "HH:mm");
					long stTimes = stTime.getTime();
					Date edTime = DateUtils.parse("31:00", "HH:mm");
					long edTimes = edTime.getTime();

					if (cyDTimes > stTimes && cyDTimes <= edTimes) {
						sampList.get(i).setZy("夜");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		vo.setSampList(sampList);
		return vo;
	}
}
