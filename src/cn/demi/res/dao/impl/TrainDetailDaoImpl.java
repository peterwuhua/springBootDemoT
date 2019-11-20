package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.ITrainDetailDao;
import cn.demi.res.po.TrainDetail;

@Repository("res.trainDetailDao")
public class TrainDetailDaoImpl extends BaseDaoImpl<TrainDetail> implements ITrainDetailDao {
}
