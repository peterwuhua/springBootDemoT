package cn.demi.app.offfice.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.offfice.service.AppCarService;
import cn.demi.app.offfice.vo.AppCarAuditEdit;
import cn.demi.app.offfice.vo.AppCarAuditPage;
import cn.demi.app.offfice.vo.AppCarEdit;
import cn.demi.app.offfice.vo.AppCarPage;
import cn.demi.app.offfice.vo.AppCarUserEdit;
import cn.demi.app.offfice.vo.AppCarUserPage;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.init.car.dao.ICarDao;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.Car;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.service.ICarAuditService;
import cn.demi.init.car.service.ICarService;
import cn.demi.init.car.service.ICarUseService;
import cn.demi.init.car.vo.CarUseVo;
import cn.demi.init.car.vo.CarVo;
import javafx.geometry.VPos;

@Service("app.carService")
public class AppCarServiceImpl implements AppCarService {

	@Autowired
	private ICarDao carDao;
	@Autowired
	private ICarUseDao carUseDao;
	@Autowired
	private ICarService carService;
	@Autowired
	private ICarUseService carUseService;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ICarAuditService carAuditService;

	@Override
	public List<AppCarPage> appcarList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + carDao.getEntityName(Car.class) + " WHERE isDel=" + Po.N);
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = carDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Car> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppCarPage> outList = new ArrayList<>();
		for (Car po : list) {
			AppCarPage vo = new AppCarPage();
			vo.setId(po.getId());
			vo.setCode(po.getCode());
			vo.setName(po.getName());
			vo.setRule(po.getRule());
			vo.setUserName(po.getUserName());
			vo.setStatus(po.getStatus());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public AppCarEdit appCarEdit(String id) throws GlobalException {
		CarVo po = carService.findById(id);
		AppCarEdit vo = new AppCarEdit();
		vo.setId(po.getId());
		vo.setCode(po.getCode());
		vo.setName(po.getName());
		vo.setRule(po.getRule());
		vo.setUserId(po.getUserId());
		vo.setUserName(po.getUserName());
		vo.setStatus(po.getStatus());
		vo.setRemark(po.getRemark());
		vo.setNum(po.getNum());
		vo.setFileName(po.getFileName());
		vo.setFilePath(po.getFilePath());
		vo.setBuyDate(po.getBuyDate());
		vo.setCareDate(po.getCareDate());
		vo.setCareCycle(po.getCareCycle());
		vo.setOil(po.getOil());
		vo.setVersion(String.valueOf(po.getVersion()));
		return vo;
	}

	@Override
	public boolean saveCar(AppCarEdit appCarEdit) throws GlobalException {
		// 判断是否有id
		if (StrUtils.isNotBlankOrNull(appCarEdit.getId())) {
			// 有更新
			try {
				carUpdate(appCarEdit);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			// 无保存
			try {
				carAdd(appCarEdit);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	private void carAdd(AppCarEdit v) {
		Car po = new Car();
		po.setCode(v.getCode());
		po.setName(v.getName());
		po.setRule(v.getRule());
		po.setUserId(v.getUserId());
		po.setUserName(v.getUserName());
		po.setStatus(v.getStatus());
		po.setRemark(v.getRemark());
		po.setFileName(v.getFileName());
		po.setFilePath(v.getFilePath());
		po.setBuyDate(v.getBuyDate());
		po.setCareDate(v.getCareDate());
		po.setCareCycle(v.getCareCycle());
		po.setOil(v.getOil());
		carDao.add(po);

	}

	private void carUpdate(AppCarEdit v) throws GlobalException {
		Car po = carDao.findById(v.getId());
		po.setId(v.getId());
		po.setCode(v.getCode());
		po.setName(v.getName());
		po.setRule(v.getRule());
		po.setUserId(v.getUserId());
		po.setUserName(v.getUserName());
		po.setStatus(v.getStatus());
		po.setRemark(v.getRemark());
		po.setFileName(v.getFileName());
		po.setFilePath(v.getFilePath());
		po.setBuyDate(v.getBuyDate());
		po.setCareDate(v.getCareDate());
		po.setCareCycle(v.getCareCycle());
		po.setOil(v.getOil());
		carDao.update(po);
	}

	@Override
	public List<AppCarUserPage> appCarUserList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + carUseDao.getEntityName(CarUse.class) + " WHERE isDel=" + Po.N);
		hql.append(" and userId like '%" + v.getUserId() + "%'");
		hql.append(" ORDER BY date desc");
		Query query = carUseDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<CarUse> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppCarUserPage> outList = new ArrayList<>();
		for (CarUse po : list) {
			AppCarUserPage vo = new AppCarUserPage();
			vo.setId(po.getId());
			vo.setNo(po.getNo());
			vo.setDestRynum(String.valueOf(po.getDestRynum()));
			vo.setUserName(po.getUserName());
			vo.setDate(po.getDate());
			vo.setStartTime(po.getStartTime());
			vo.setEndTime(po.getEndTime());
			vo.setStatus(po.getStatus());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public AppCarUserEdit appCarUserEdit(String id) throws GlobalException {
		CarUseVo po = carUseService.findById(id);
		AppCarUserEdit vo = new AppCarUserEdit();
		vo.setId(po.getId());
		vo.setBusNo(po.getBusNo());
		vo.setDestination(po.getDestination());
		vo.setDestRynum(String.valueOf(po.getDestRynum()));
		vo.setUserName(po.getUserName());
		vo.setUserId(po.getUserId());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setContent(po.getContent());
		vo.setRemark(po.getRemark());
		vo.setCode(po.getCode());
		vo.setVersion(String.valueOf(po.getVersion()));
		vo.setIsCommit(po.getIsCommit());
		return vo;
	}

	@Override
	public boolean saveCarUser(AppCarUserEdit appCarUserEdit, ObjVo objVo) throws GlobalException {
		// 判断是否有id
		if (StrUtils.isNotBlankOrNull(appCarUserEdit.getId())) {
			// 有更新
			try {
				carUserUpdate(appCarUserEdit, objVo);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			// 无保存
			try {
				carUserAdd(appCarUserEdit, objVo);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	private void carUserAdd(AppCarUserEdit v, ObjVo objVo) {
		CarUse p = new CarUse();
		p.setBusNo(v.getBusNo());
		p.setDestination(v.getDestination());
		p.setDestRynum(Integer.parseInt(v.getDestRynum()));
		p.setUserId(v.getUserId());
		p.setUserName(v.getUserName());
		p.setDate(v.getDate());
		p.setStartTime(v.getStartTime());
		p.setEndTime(v.getEndTime());
		p.setContent(v.getContent());
		p.setRemark(v.getRemark());
		p.setCode(v.getCode());
		p.setOrgId(objVo.getOrgId());
		p.setOrgName(objVo.getOrgName());
		p.setStatus(CarUse.ST_BC);
		p.setNo(carUseDao.createCode());
		try {
			p.setDuration((int) DateUtils.getIntevalHours(
					DateUtils.parse(p.getStartTime(), DateUtils.formatStr_yyyyMMddHHmmss),
					DateUtils.parse(p.getEndTime(), DateUtils.formatStr_yyyyMMddHHmmss)));
		} catch (ParseException e) {
			p.setDuration(0);
		}
		carUseDao.add(p);
		v.setId(p.getId());
		commint(v, p);
	}

	private void carUserUpdate(AppCarUserEdit v, ObjVo objVo) {
		CarUse p = carUseDao.findById(v.getId());
		p.setId(v.getId());
		p.setBusNo(v.getBusNo());
		p.setDestination(v.getDestination());
		p.setDestRynum(Integer.parseInt(v.getDestRynum()));
		p.setUserId(v.getUserId());
		p.setUserName(v.getUserName());
		p.setDate(v.getDate());
		p.setStartTime(v.getStartTime());
		p.setEndTime(v.getEndTime());
		p.setContent(v.getContent());
		p.setRemark(v.getRemark());
		p.setCode(v.getCode());
		p.setOrgId(objVo.getOrgId());
		p.setOrgName(objVo.getOrgName());
		p.setStatus(CarUse.ST_BC);
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(carUseDao.createCode());
		}
		try {
			p.setDuration((int) DateUtils.getIntevalHours(
					DateUtils.parse(p.getStartTime(), DateUtils.formatStr_yyyyMMddHHmmss),
					DateUtils.parse(p.getEndTime(), DateUtils.formatStr_yyyyMMddHHmmss)));
		} catch (ParseException e) {
			p.setDuration(0);
		}
		carUseDao.update(p);
		commint(v, p);

	}

	public void commint(AppCarUserEdit v, CarUse p) {
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			progressDao.add(p.getId(), EunmTask.CL_SH.getStatus(), null, null, null, null);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.CL_SQ.getStatus(), v.getIsCommit(), p.getRemark());
			p.setStatus(CarUse.ST_SH);
			carUseDao.update(p);
		}
	}

	@Override
	public List<AppCarAuditPage> appCarAuditList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + carUseDao.getEntityName(CarUse.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status='" + CarUse.ST_SH + "'");
		hql.append(" and auditId='" + v.getUserId() + "' or auditId is null");
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = carUseDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<CarUse> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppCarAuditPage> outList = new ArrayList<>();
		for (CarUse po : list) {
			AppCarAuditPage vo = new AppCarAuditPage();
			vo.setId(po.getId());
			vo.setNo(po.getNo());
			vo.setDestRynum(String.valueOf(po.getDestRynum()));
			vo.setUserName(po.getUserName());
			vo.setDate(po.getDate());
			vo.setStartTime(po.getStartTime());
			vo.setEndTime(po.getEndTime());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public List<AppCarAuditPage> appCarAuditListEd(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + carUseDao.getEntityName(CarUse.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status='" + CarUse.ST_TG + "' or status='" + CarUse.ST_JJ + "'");
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = carUseDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<CarUse> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppCarAuditPage> outList = new ArrayList<>();
		for (CarUse po : list) {
			AppCarAuditPage vo = new AppCarAuditPage();
			vo.setId(po.getId());
			vo.setNo(po.getNo());
			vo.setDestRynum(String.valueOf(po.getDestRynum()));
			vo.setUserName(po.getUserName());
			vo.setDate(po.getDate());
			vo.setStartTime(po.getStartTime());
			vo.setEndTime(po.getEndTime());
			vo.setAuditDate(po.getAuditDate());
			vo.setAuditName(po.getAuditName());
			vo.setAuditResult(po.getAuditResult());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public AppCarAuditEdit appCarAuditEdit(String id, ObjVo objVo) throws GlobalException {
		CarUseVo po = carAuditService.findById(id);
		AppCarAuditEdit vo = new AppCarAuditEdit();
		vo.setId(po.getId());
		vo.setCarId(po.getCarId());
		vo.setCode(po.getCode());
		vo.setBusNo(po.getBusNo());
		vo.setDestination(po.getDestination());
		vo.setDestRynum(String.valueOf(po.getDestRynum()));
		vo.setUserName(po.getUserName());
		vo.setDate(po.getDate());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setContent(po.getContent());
		vo.setRemark(po.getRemark());
		vo.setAuditMsg(po.getAuditMsg());
		vo.setAuditDate(String.valueOf(DateUtils.getCurrDateTimeStr()));
		vo.setAuditId(objVo.getUserId());
		vo.setAuditName(objVo.getName());
		return vo;
	}

	@Override
	public boolean saveCarAudit(AppCarAuditEdit v, ObjVo objVo) throws GlobalException {
		try {
			CarUse p = carUseDao.findById(v.getId());
			p.setAuditDate(v.getAuditDate());
			p.setAuditMsg(v.getAuditMsg());
			p.setAuditId(v.getAuditId());
			p.setAuditName(v.getAuditName());
			p.setCarId(v.getCarId());// 车辆id
			p.setCode(v.getCode());// 车辆编号
			Progress pg = progressDao.findByBusId(p.getId());
			if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
				p.setAuditResult("通过");
				p.setStatus(CarUse.ST_TG);
				Car car = carDao.findById(p.getCarId());
				car.setStatus(Car.ST_SY);// 使用中
				carDao.update(car);
			} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
				p.setAuditResult("不通过");
				p.setStatus(CarUse.ST_JJ);
			}
			progressDao.delete(pg);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.CL_SH.getStatus(), v.getIsCommit(), p.getRemark());
			carUseDao.update(p);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
