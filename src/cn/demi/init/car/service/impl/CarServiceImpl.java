package cn.demi.init.car.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.car.dao.ICarDao;
import cn.demi.init.car.po.Car;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.service.ICarService;
import cn.demi.init.car.vo.CarVo;

@Service("init.carService")
public class CarServiceImpl extends BaseServiceImpl<CarVo,Car> implements
		ICarService {
	@Autowired
	private ICarDao carDao;
 
	@Override
	public CarVo findById(String id) throws GlobalException {
		Car po;
		try {
			po = carDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	@Override
	public void add(CarVo v) throws GlobalException {
		Car p = vo2Po(v);
		carDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(CarVo v) throws GlobalException {
		Car p = carDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		carDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CarVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = carDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Car>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Select(GridVo gridVo, CarVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		//pageResult.addCondition(new QueryCondition("status='"+Car.ST_ZC+"'"));
		pageResult = carDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Car>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<CarVo> list4Select(GridVo gridVo,CarVo v) throws GlobalException {
		StringBuffer hql=new StringBuffer("SELECT car.id,car.name,car.code,car.rule,car.user_name,car.status,count(u.id) FROM "+tablePrefix+carDao.getEntityName(Car.class)+" car");
		hql.append(" LEFT JOIN "+tablePrefix+carDao.getEntityName(CarUse.class)+" u ON car.id=u.car_id AND u.is_del='"+Po.N+"' AND (u.start_time>='"+DateUtils.getCurrDateTimeStr()+"' or u.start_time<'"+DateUtils.getCurrDateTimeStr()+"' and u.end_time>='"+DateUtils.getCurrDateTimeStr()+"')");
		hql.append(" WHERE car.is_del='"+Po.N+"' ");
		//hql.append(" AND car.status='"+Car.ST_ZC+"'");
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		if(QueryConditionList!=null) {
			for (QueryCondition condition : QueryConditionList) {
				if(!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					hql.append(" AND (car."+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"%')");
				}
			}
		}
		hql.append(" group by car.id");
		@SuppressWarnings("unchecked")
		List<Object[]> appList=carDao.queryBySql(hql.toString());
		List<CarVo> carList=new ArrayList<CarVo>();
		if(null!=carList) {
			for (Object[] obj : appList) {
				CarVo car=new CarVo();
				car.setId(String.valueOf(obj[0]));
				car.setName(String.valueOf(obj[1]));
				car.setCode(String.valueOf(obj[2]));
				car.setRule(String.valueOf(obj[3]));
				car.setUserName(String.valueOf(obj[4]));
				car.setStatus(String.valueOf(obj[5]));
				car.setNum(Integer.valueOf(String.valueOf(obj[6])));
				carList.add(car);
			}
		}
		return carList;
	}
	
}
