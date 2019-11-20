package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.ITrainDao;
import cn.demi.res.po.Train;

@Repository("res.trainDao")
public class TrainDaoImpl extends BaseDaoImpl<Train> implements ITrainDao {
}
