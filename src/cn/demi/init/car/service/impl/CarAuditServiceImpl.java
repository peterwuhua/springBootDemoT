package cn.demi.init.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.init.car.dao.ICarDao;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.Car;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.service.ICarAuditService;
import cn.demi.init.car.vo.CarUseVo;

@Service("init.carAuditService")
public class CarAuditServiceImpl extends BaseServiceImpl<CarUseVo,CarUse> implements
		ICarAuditService {
	@Autowired
	private ICarDao carDao;
	@Autowired
	private ICarUseDao carUseDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public CarUseVo findById(String id) throws GlobalException {
		CarUse po;
		try {
			po = carUseDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	
	@Override
	public void update(CarUseVo v) throws GlobalException {
		CarUse p = carUseDao.findById(v.getId());
		p.setAuditDate(v.getAuditDate());
		p.setAuditMsg(v.getAuditMsg());
		p.setAuditId(v.getAuditId());
		p.setAuditName(v.getAuditName());
		p.setCarId(v.getCarId());//车辆id
		p.setCode(v.getCode());//车辆编号
		Progress pg=progressDao.findByBusId(p.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setAuditResult("通过");
			p.setStatus(CarUse.ST_TG);
			Car car = carDao.findById(p.getCarId());
			car.setStatus(Car.ST_SY);//使用中
			carDao.update(car);
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			p.setAuditResult("不通过");
			p.setStatus(CarUse.ST_JJ);
		}
		progressDao.delete(pg);
		progressLogDao.add(p.getId(), p.getId(), EunmTask.CL_SH.getStatus(), v.getIsCommit(), p.getRemark());
		carUseDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CarUseVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='"+CarUse.ST_SH+"'"));
		pageResult.addCondition(new QueryCondition("auditId='"+getCurrent().getAccountId()+"' or auditId is null"));
		pageResult = carUseDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<CarUse>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, CarUseVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		//pageResult.addCondition(new QueryCondition("auditId='"+getCurrent().getAccountId()+"' "));
		pageResult.addCondition(new QueryCondition("status='"+CarUse.ST_TG+"' or status='"+CarUse.ST_JJ+"'"));
		pageResult = carUseDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<CarUse>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
