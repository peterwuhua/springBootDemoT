package cn.demi.init.car.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.service.ICarUseService;
import cn.demi.init.car.vo.CarUseVo;

@Service("init.carUseService")
public class CarUseServiceImpl extends BaseServiceImpl<CarUseVo,CarUse> implements
		ICarUseService {

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
	public void add(CarUseVo v) throws GlobalException {
		CarUse p = vo2Po(v);
//		Car car=carDao.findById(v.getCarId());
//		p.setCode(car.getCode());
//		p.setName(car.getName());
//		p.setRule(car.getRule());
//		p.setAuditId(car.getUserId());
//		p.setAuditName(car.getUserName());
		p.setOrgId(getCurrent().getDepId());
		p.setOrgName(getCurrent().getDepName());
		p.setStatus(CarUse.ST_BC);
		p.setNo(carUseDao.createCode());
		try {
			p.setDuration((int) DateUtils.getIntevalHours(DateUtils.parse(p.getStartTime(), DateUtils.formatStr_yyyyMMddHHmmss), DateUtils.parse(p.getEndTime(), DateUtils.formatStr_yyyyMMddHHmmss)));
		} catch (ParseException e) {
			p.setDuration(0);
		}
		carUseDao.add(p);
		v.setId(p.getId());	
		commint(v, p);
	}
	@Override
	public void update(CarUseVo v) throws GlobalException {
		CarUse p = carUseDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
//		Car car=carDao.findById(v.getCarId());
//		p.setCode(car.getCode());
//		p.setName(car.getName());
//		p.setRule(car.getRule());
//		p.setAuditId(car.getUserId());
//		p.setAuditName(car.getUserName());
		p.setOrgId(getCurrent().getDepId());
		p.setOrgName(getCurrent().getDepName());
		p.setStatus(CarUse.ST_BC);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(carUseDao.createCode());
		}
		try {
			p.setDuration((int) DateUtils.getIntevalHours(DateUtils.parse(p.getStartTime(), DateUtils.formatStr_yyyyMMddHHmmss), DateUtils.parse(p.getEndTime(), DateUtils.formatStr_yyyyMMddHHmmss)));
		} catch (ParseException e) {
			p.setDuration(0);
		}
		carUseDao.update(p);
		commint(v, p);
	}
	public void commint(CarUseVo v,CarUse p) {
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			progressDao.add(p.getId(), EunmTask.CL_SH.getStatus(),null,null, null, null);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.CL_SQ.getStatus(), v.getIsCommit(), p.getRemark());
			p.setStatus(CarUse.ST_SH);
			carUseDao.update(p);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CarUseVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addOrder("date", OrderCondition.ORDER_DESC);
		pageResult.addCondition(new QueryCondition("userId like '%"+getCurrent().getAccountId()+"%'"));
		pageResult = carUseDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<CarUse>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<CarUseVo> listData(CarUseVo v) throws GlobalException {
		String hql="FROM "+carUseDao.getEntityName(CarUse.class)+" WHERE isDel='"+Po.N+"' AND carId='"+v.getCarId()+"'";
		if(StrUtils.isNotBlankOrNull(v.getStartTime())) {
			hql+=" AND (startTime>='"+v.getStartTime()+" 00:00:00' or startTime<'"+v.getStartTime()+" 00:00:00' and endTime>='"+v.getStartTime()+" 00:00:00')";
		}else {
			hql+=" AND (startTime>='"+DateUtils.getCurrDateTimeStr()+"' or startTime<'"+DateUtils.getCurrDateTimeStr()+"' and endTime>='"+DateUtils.getCurrDateTimeStr()+"')";
		}
		if(StrUtils.isNotBlankOrNull(v.getEndTime())) {
			hql+=" AND (endTime<'"+v.getEndTime()+" 23:59:59' or endTime>'"+v.getEndTime()+" 23:59:59' and startTime<'"+v.getEndTime()+" 23:59:59')";
		}
		if(StrUtils.isNotBlankOrNull(v.getBusId())) {
			hql+=" AND busId='"+v.getBusId()+"'";
		}
		List<CarUse> list=carUseDao.list(hql);
		return toVoList(list);
	}
	
}
