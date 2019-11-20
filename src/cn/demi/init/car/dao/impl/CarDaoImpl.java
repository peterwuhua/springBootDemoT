package cn.demi.init.car.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.car.dao.ICarDao;
import cn.demi.init.car.po.Car;

@Repository("init.carDao")
public class CarDaoImpl extends BaseDaoImpl<Car> implements ICarDao {

}
